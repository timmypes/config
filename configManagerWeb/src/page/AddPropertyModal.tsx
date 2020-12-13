import React from "react";
import {Modal, Input, Form, Button, message} from 'antd';
import axios from "axios";
import Constant from "../constant";
import Qs from "qs";
const {TextArea} = Input;

interface IProp{
    isShow: boolean,
    onCancel: () => void,
    onSuccess: () => void,
}

interface IState {
    isShow: boolean,
}


export default class AddPropertyModal extends React.Component<IProp, IState> {
    constructor(props: IProp) {
        super(props);
        this.state={
            isShow: false
        }
    }

    public componentWillReceiveProps(nextProps: Readonly<IProp>): void {
        if(nextProps.isShow !== this.state.isShow){
            this.setState({
                isShow: nextProps.isShow
            });
        }
    }


    public render():JSX.Element{
        return <Modal width={800}
                      visible={this.state.isShow}
                      onCancel={()=>this.props.onCancel()}
                      title='添加属性'
                      footer={null}>
            <Form onFinish={(values) => this.submit(values)}>
                <Form.Item
                    name="appName"
                    label='应用名'
                    rules={[{ required: true, message: '请输入应用名!' }]}
                >
                    <Input placeholder={"应用名"}/>
                </Form.Item>
                <Form.Item
                    name="propName"
                    label='属性名'
                    rules={[{ required: true, message: '请输入属性名!' }]}
                >
                    <Input placeholder={"属性名"}/>
                </Form.Item>
                <Form.Item
                    name="propValue"
                    label='属性值'
                    rules={[{ required: true, message: '请输入属性值!' }]}
                >
                    <TextArea rows={4} placeholder={"属性值"}/>
                </Form.Item>
                <Form.Item
                    name="instruction"
                    label='参数说明'
                >
                    <Input placeholder={"参数说明"}/>
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
            url: Constant.ADD_PROPERTY,
            method: 'post',
            headers: { 'content-type': 'application/x-www-form-urlencoded' },
            data: Qs.stringify({
                appName: values.appName,
                propName: values.propName,
                propValue: values.propValue,
                instruction: values.instruction,
            })
        }).then(function (response) {
            message.success("保存成功");
            _this.props.onSuccess();
        })
    }
}