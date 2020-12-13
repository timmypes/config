
import React from "react";
import {Modal, Space, Table, message} from "antd";
import PageInfo from "../model/PageInfo";
import axios from "axios";
import Constant from "../constant";
import RecordRowData from "../model/RecordRowData";
import Qs from "qs";

interface IProp{
    propId: string,
    isShow: boolean,
    onCancel: () => void,
    onSetVersion: () => void,
}

interface IState {
    isShow: boolean,
    pagination: PageInfo,
    recordList: RecordRowData[],
    propId: string,
}

export default class PropertyRecordModal extends React.Component<IProp, IState> {
    private tableColumns = [
        {
            title: '应用名',
            dataIndex: 'appName',
            key: 'appName',
        },
        {
            title: '属性名',
            dataIndex: 'propName',
            key: 'propName',
        },
        {
            title: '属性值',
            dataIndex: 'propValue',
            key: 'propValue',
        },
        {
            title: '参数描述',
            dataIndex: 'instruction',
            key: 'instruction',
        },
        {
            title: '变更说明',
            dataIndex: 'remark',
            key: 'remark',
        },
        {
            title: '操作',
            key: 'propId',
            dataIndex: 'recordId',
            render: (recordId:any) => (
                <Space size="middle">
                    <a onClick={(record)=>this.rollBackToVersion(recordId)}>选中</a>
                </Space>
            ),
        },
    ];


    constructor(props: IProp) {
        super(props);
        this.state = {
            isShow: false,
            pagination: new PageInfo(),
            recordList: [],
            propId: '',
        }
    }

    public componentWillReceiveProps(nextProps: Readonly<IProp>): void {
        if(nextProps.isShow !== this.state.isShow){
            this.setState({
                isShow: nextProps.isShow
            });
            if(nextProps.isShow && nextProps.propId && nextProps.propId !== ''){
                this.requestForRecordList(nextProps.propId, this.state.pagination.current, this.state.pagination.pageSize);
                this.setState({
                    propId: nextProps.propId
                });
            }
            if(!nextProps.isShow){
                this.setState({
                    pagination: new PageInfo()
                })
            }
        }
    }

    public render():JSX.Element{
        return <Modal visible={this.state.isShow} onCancel={this.cancel.bind(this)} width={800}>
            <Table columns={this.tableColumns} dataSource={this.state.recordList} pagination={this.state.pagination} onChange={this.handlerTableChange.bind(this)}/>
        </Modal>
    }

    public cancel(){
        this.props.onCancel();
    }

    private requestForRecordList(propId: string, page: number, pageSize: number){
        const _this = this;
        axios({
            url: Constant.FIND_RECORD_LIST_BY_PROP_ID,
            method: 'post',
            headers: { 'content-type': 'application/x-www-form-urlencoded' },
            data: Qs.stringify({
                propId: propId,
                page: page,
                pageSize: pageSize,
            })
        }).then(function (response) {
                console.log(response.data.data);
                const data: any = response.data.data;
                const recordList: RecordRowData[] = [];
                if(data && data.list){
                    const list: any[] = data.list;
                    list.map((item, index) => {
                        const recordData = new RecordRowData();
                        recordData.index = index;
                        recordData.key = item.recordId;
                        recordData.recordId = item.recordId;
                        recordData.appName = item.appName;
                        recordData.propName = item.propName;
                        recordData.propValue = item.propValue;
                        recordData.instruction = item.instruction;
                        recordData.remark = item.remark;
                        recordList.push(recordData);
                    });
                }
                if(data && data.total){
                    const pageInfo = new PageInfo();
                    pageInfo.current = page;
                    pageInfo.total = data.total;
                    _this.setState({
                        recordList,
                        pagination: pageInfo,
                    })
                }
            })
    }

    public handlerTableChange(pagination: any){
        this.requestForRecordList(this.state.propId, pagination.current, pagination.pageSize);
    }

    public rollBackToVersion(recordId: string){
        const _this = this;
        axios({
            url: Constant.SET_PREVIOUS_VERSION,
            method: 'post',
            headers: { 'content-type': 'application/x-www-form-urlencoded' },
            data: Qs.stringify({
                recordId: recordId,
            })
        }).then(function (response) {
            message.success('回退版本成功');
            _this.props.onCancel();
        })
    }
}