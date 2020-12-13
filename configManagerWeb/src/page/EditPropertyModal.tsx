import React from "react";
import {Modal, Input, Form, Button, message} from 'antd';
import axios from "axios";
import Constant from "../constant";
import Qs from "qs";
import PropRowData from "../model/PropRowData";
const {TextArea} = Input;

interface IProp{
    isShow: boolean,
    onCancel: () => void,
    propData: PropRowData
    onSuccess: () => void,
}

interface IState {
    isShow: boolean,
    propData: PropRowData,
}


export default class EditPropertyModal extends React.Component<IProp, IState> {
    private formRef: any;

    constructor(props: IProp) {
        super(props);
        this.state={
            isShow: false,
            propData: new PropRowData(),
        }
    }

    public componentWillReceiveProps(nextProps: Readonly<IProp>): void {
        if(nextProps.isShow !== this.state.isShow){
            console.log(nextProps);
            this.setState({
                isShow: nextProps.isShow,
                propData: nextProps.propData
            }, ()=>{
                if(nextProps.isShow && this.formRef){
                    this.formRef.setFieldsValue({
                        appName: nextProps.propData.appName,
                        propName: nextProps.propData.propName,
                        propValue: nextProps.propData.propValue,
                        instruction: nextProps.propData.instruction,
                    });
                }
            });
        }
    }

    public changeValue(propName: string, propValue: number){
        const propData: any = this.state.propData;
        propData[propName] = propValue;
        this.setState({
            propData
        });
    }

    public render():JSX.Element{
        return <Modal width={800}
                      visible={this.state.isShow}
                      onCancel={()=>this.props.onCancel()}
                      title='编辑属性'
                      footer={null}>
            <Form onFinish={(values) => this.submit(values)} ref={(formRef) => this.formRef = formRef}>
                <Form.Item
                    name="appName"
                    label='应用名'
                    rules={[{ required: true, message: '请输入应用名!' }]}
                    initialValue={this.state.propData.appName}
                >
                    <Input disabled={true} placeholder={"应用名"} />
                </Form.Item>
                <Form.Item
                    name="propName"
                    label='属性名'
                    rules={[{ required: true, message: '请输入属性名!' }]}
                    initialValue={this.state.propData.propName}
                >
                    <Input placeholder={"属性名"} />
                </Form.Item>
                <Form.Item
                    name="propValue"
                    label='属性值'
                    rules={[{ required: true, message: '请输入属性值!' }]}
                    initialValue={this.state.propData.propValue}
                >
                    <TextArea rows={4} placeholder={"属性值"}/>
                </Form.Item>
                <Form.Item
                    name="instruction"
                    label='参数说明'
                    initialValue={this.state.propData.instruction}
                >
                    <Input placeholder={"参数说明"} />
                </Form.Item>
                <Form.Item
                    name="remark"
                    label='修改说明'
                >
                    <Input placeholder={"修改说明"}/>
                </Form.Item>
                <Form.Item>
                    <Button type="primary" htmlType="submit">
                        提交
                    </Button>
                </Form.Item>
            </Form>
        </Modal>
    }

    public submit(values: any){
        const _this = this;
        axios({
            url: Constant.UPDATE_PROPERTY,
            method: 'post',
            headers: { 'content-type': 'application/x-www-form-urlencoded' },
            data: Qs.stringify({
                propId: this.props.propData.propId,
                appName: values.appName,
                propName: values.propName,
                propValue: values.propValue,
                instruction: values.instruction,
                remark: values.remark,
            })
        }).then(function (response) {
            message.success("操作成功");
            _this.props.onSuccess();
        })
    }
}