import React from 'react'
import {Button,Col, Form, Input, Row,Modal} from 'antd';

const createForm = Form.create;
const FormItem = Form.Item;
const objectAssign = require('object-assign');

var List = React.createClass({

    getInitialState() {
        return {
            selectedRowKeys: [], // 这里配置默认勾选列
            loading: false,
            data: [],
            formData: {},
            pagination: {},
            canEdit: true,
            visible: false,
            visibleLook: false,
            assignVisible: false,
        };
    },
    componentWillMount () {
        console.log("willMount");

    },
    componentDidMount () {
        console.log("didMount");
        this.fetch();
    },
    componentWillReceiveProps (nextProps) {
        console.log("WillReceiveProps");
    },
   /* shouldComponentUpdate (nextProps,nextState) {
        console.log("shouldComponentUpdate");
    },*/
    componentWillUpdate (nextProps,nextState) {
        console.log("WillUpdate");
    },
    componentDidUpdate (prevProps,prevState) {
        console.log("DidUpdate");
    },
    componentWillUnmount () {
        console.log("WillUnmount");
    },

    fetch(params = {
        pageSize: 10,
        current: 1
    }) {
        this.setState({
            loading: true
        });
        Utils.ajaxData({
            url: '/act/get/querycondition.htm',
            data: params,
            callback: (result) => {
                this.setState({
                    loading: false,
                    data: result.data
                });
                this.props.form.setFieldsValue(result.data);
            }
        });
    },
    handleOk(e){
        e.preventDefault();
        var props = this.props;
        this.props.form.validateFields((errors, values) => {
            if (!!errors) {
                return;
            }
            var data = objectAssign({}, {
                form: JSON.stringify(objectAssign({}, values, {}))
            });
            var confirm = Modal.confirm;
            confirm({
                title: '确定提交吗？',
                content: '请确认您的查询条件填写无误后点击确定！',
                onOk() {
                    Utils.ajaxData({
                        url: "/act/saveorupdate/querycondition.htm",
                        data: data,
                        callback: (result) => {
                            if (result.code == 200) {
                                /*const modal = Modal.success({
                                    title: "查询数据，逻辑待写",
                                });*/
                            }else {
                                Modal.error({
                                    title: result.msg,
                                });
                            }
                        }
                    });
                },
                onCancel() {},
            });
        })
    },
    render() {
        console.log("render渲染");
        const {getFieldProps} = this.props.form;

        var props = this.props;
        const formItemLayout = {
            labelCol: {
                span: 8
            },
            wrapperCol: {
                span: 16
            },
        };
        const itemLayout = {
            labelCol: {
                span: 6
            },
            wrapperCol: {
                span: 9
            },
        };
        const formItemLay = {
            type:"flex",
            justify:"center",
            align:"middle"
        };
        return (
            <div >
                <Form horizontal form={this.props.form}>
                    <Input  {...getFieldProps('id', {initialValue: ''})} type="hidden"/>

                    <div className="navLine-wrap-left">
                        <h2>设置用户类型</h2>
                    </div>
                        <Row {...formItemLay}>
                            <div>
                                <font color="green">提示：设置条件后将根据条件导入用户数据并执行扣费，请谨慎填写</font>
                            </div>
                        </Row>
                        <Row {...formItemLay}>
                            <Col span="6">
                                <FormItem  {...formItemLayout} label="芝麻分：">
                                    <Input disabled={!props.canEdit} {...getFieldProps('zmScoreMin')}  type="text"/>
                                </FormItem>
                            </Col>
                            <Col span="6" pull={1}>
                                <FormItem  {...formItemLayout} label="至 ">
                                    <Input disabled={!props.canEdit}  {...getFieldProps('zmScoreMax')} type="text"/>
                                </FormItem>
                            </Col>
                        </Row>

                        <Row {...formItemLay}>
                            <Col span="6">
                                <FormItem  {...formItemLayout} label="年 龄：">
                                    <Input disabled={!props.canEdit}  {...getFieldProps('ageMin')} type="text"/>
                                </FormItem>
                            </Col>
                            <Col span="6" pull={1}>
                                <FormItem  {...formItemLayout} label="至 ">
                                    <Input disabled={!props.canEdit}  {...getFieldProps('ageMax')} type="text"/>
                                </FormItem>
                            </Col>
                        </Row>

                        <Row {...formItemLay}>
                            <Col span="12" >
                            </Col>
                            <Col span="12" pull={7}>
                                <FormItem {...itemLayout} label="需求数量：">
                                    <Input disabled={!props.canEdit}  {...getFieldProps('dataAmount')} placeholder={"请填写你需要多少条数据"} type="text"/>
                                </FormItem>
                            </Col>
                        </Row>
                        <Row {...formItemLay}>
                            <Button type="primary" htmlType="submit" onClick={this.handleOk}>&nbsp;提&nbsp;&nbsp;交&nbsp;</Button>
                        </Row>

                </Form>
            </div>
        );
    }
})
List = createForm()(List);
export default List;