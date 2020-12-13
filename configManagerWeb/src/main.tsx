import React from 'react';
import './main.css';
import axios from '../node_modules/axios'
import Constant from "./constant";
import PropRowData from "./model/PropRowData";
import {Button, Space, Table, message} from 'antd';
import PageInfo from "./model/PageInfo";
import PropertyRecordModal from "./page/PropertyRecordModal";
import Qs from 'qs';
import AddPropertyModal from "./page/AddPropertyModal";
import EditPropertyModal from "./page/EditPropertyModal";

interface IProp{

}

interface IState {
  propDataList: PropRowData[];
  pagination: PageInfo;
  showRecordModal: boolean;
  showRecordPropId: string;
  showAddPropertyModal: boolean;
  editPropId: string;
  showEditPropertyModal: boolean;
  propData: PropRowData;
}

export default class Main extends React.Component<IProp, IState> {
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
      title: '描述',
      dataIndex: 'instruction',
      key: 'instruction',
    },
    {
      title: '操作',
      key: 'propId',
      dataIndex: 'propId',
      render: (propId:string, propData: PropRowData) => (
          <Space size="middle">
            <a onClick={() => this.showRecordModal(propId)}>操作历史</a>
            <a onClick={() => this.editProp(propId, propData)}>编辑</a>
            <a onClick={() => this.deleteProp(propId)}>删除</a>
          </Space>
      ),
    },
  ];

  constructor(props: IProp){
    super(props);
    this.state={
      propDataList: [],
      pagination: new PageInfo(),
      showRecordModal: false,
      showRecordPropId: '',
      showAddPropertyModal: false,
      editPropId: '',
      showEditPropertyModal: false,
      propData: new PropRowData(),
    }
    document.title = '动态配置中心管理站点'
  }

  public componentDidMount(): void {
    this.requestForPropertyList(this.state.pagination.current, this.state.pagination.pageSize);
  }

  public render(): JSX.Element{
    return <div className="PropPage">
      <PropertyRecordModal propId={this.state.showRecordPropId} isShow={this.state.showRecordModal} onCancel={()=>{this.setState({showRecordModal: false})}} onSetVersion={()=>this.setVersionSuccess()} />
      <AddPropertyModal isShow={this.state.showAddPropertyModal} onCancel={()=>this.setState({showAddPropertyModal: false})} onSuccess={()=>this.addSuccess()}/>
      <EditPropertyModal isShow={this.state.showEditPropertyModal} onCancel={()=>this.setState({showEditPropertyModal: false})} propData={this.state.propData} onSuccess={()=>this.editSuccess()}/>
      <Button onClick={()=>this.setState({showAddPropertyModal: true})} style={{marginBottom: '10px'}}>添加属性</Button>
      <Table dataSource={this.state.propDataList} columns={this.tableColumns} pagination={this.state.pagination} onChange={this.handlerTableChange.bind(this)}/>
    </div>
  }

  public editSuccess(){
    this.setState({
      showEditPropertyModal: false,
      pagination: new PageInfo(),
    }, ()=>{
      this.requestForPropertyList(this.state.pagination.current, this.state.pagination.pageSize);
    })
  }

  public addSuccess(){
    this.setState({
      showAddPropertyModal: false,
      pagination: new PageInfo(),
    }, ()=>{
      this.requestForPropertyList(this.state.pagination.current, this.state.pagination.pageSize);
    })
  }

  public setVersionSuccess(){
    this.setState({
      showRecordModal: false,
      pagination: new PageInfo(),
    }, () => {
      this.requestForPropertyList(this.state.pagination.current, this.state.pagination.pageSize);
    })
  }

  public handlerTableChange(pagination: any){
    this.requestForPropertyList(pagination.current, pagination.pageSize);
  }

  public showRecordModal(propId: string){
    this.setState({
      showRecordModal: true,
      showRecordPropId: propId,
    });
  }

  public editProp(propId: string, propData: PropRowData){
    this.setState({
      showEditPropertyModal: true,
      propData: propData
    })
  }

  public deleteProp(propId: string){
    const _this = this;
    axios({
      url: Constant.DELETE_PROPERTY,
      method: 'post',
      headers: { 'content-type': 'application/x-www-form-urlencoded' },
      data: Qs.stringify({
        propId: propId,
      })
    }).then(function (response) {
      message.success("删除成功");
      _this.setState({
        pagination: new PageInfo()
      }, ()=>{
        _this.requestForPropertyList(_this.state.pagination.current, _this.state.pagination.pageSize);
      })
    })
  }

  private requestForPropertyList(page: number, pageSize: number){
    const _this = this;
    axios({
      url: Constant.FIND_PROPERTY_LIST,
      method: 'post',
      headers: { 'content-type': 'application/x-www-form-urlencoded' },
      data: Qs.stringify({
        page: page,
        pageSize: pageSize,
      })
    }).then(function (response) {
          console.log(response.data.data);
          const data: any = response.data.data;
          const propDataList: any[] = [];
          if(data && data.list){
            const list: any[] = data.list;
            list.map((item, index) => {
              const propData = new PropRowData();
              propData.index = index;
              propData.key = item.key;
              propData.propId = item.propId;
              propData.appName = item.appName;
              propData.propName = item.propName;
              propData.propValue = item.propValue;
              propData.instruction = item.instruction;
              propDataList.push(propData);
            });
          }
          if(data && data.total){
            const pageInfo = new PageInfo();
            pageInfo.current = page;
            pageInfo.total = data.total;
            _this.setState({
              propDataList,
              pagination: pageInfo,
            })
          }

        })
  }
}
