import React from 'react'
import {Button,Col, Select,Form, Input, Row,Modal} from 'antd';

const createForm = Form.create;
const FormItem = Form.Item;
const Option = Select.Option;
const objectAssign = require('object-assign');
var tagList = [];
var sortList = [];


var List = React.createClass({

    getInitialState() {
        return {
            selectedRowKeys: [], // 这里配置默认勾选列
            loading: false,
            data: [],
            tagList:[],
            sortList:[],
            formData: {},
            pagination: {},
            canEdit: true,
            visible: false,
            visibleLook: false,
            assignVisible: false,
        };
    },
    componentWillMount () {
        Utils.ajaxData({
            url: '/modules/manage/system/usr/list.htm',
            method: 'get',
            callback: (result) => {
                this.setState({
                    tagList : result.data
                });
            }
        });

        Utils.ajaxData({
            url: '/act/flowControl/getMutilCheckBox.htm',
            method: 'get',
            type: 'json',
            data: {
                "typeCode": "SJ_CHARGE_TYPE"
            },
            callback: (result) => {
                this.setState({
                    sortList : result.data
                });

            }
        });


        console.log("willMount");

    },
    componentDidMount () {
        console.log("didMount");
        this.fetch();
    },

    fetch(params = {
        pageSize: 10,
        current: 1
    }) {
        this.setState({
            loading: true
        });
    },

    getTagList() {
        return this.state.tagList.map((item, index) => {
            return <Option key={item.id}>{item.name}</Option>
        })
    },
    getSortList() {
        return this.state.sortList.map((item, index) => {
            return <Option key={item.itemCode}>{item.itemValue}</Option>
        })
    },
    handleCancel() {
        this.props.form.resetFields();
        this.props.hideModal();
        this.setState({
            imageUrl: ''
        })
    },
    handleOk(e) {
        e.preventDefault();
        var me = this;
        var props = me.props;
        var record = props.record;
        var title = props.title;
        var path = me.path;
        var name = me.name;
        var url = "/acc/account/charge.htm";
        this.props.form.validateFields((errors, values) => {
            var data = objectAssign({}, {
                form: JSON.stringify(objectAssign({'path': path, 'name': name}, values, {}
                ))
            });
            Utils.ajaxData({
                url: url,
                data: data,
                callback: (result) => {
                    if (result.code == 200) {
                        Modal.success({
                            title: result.msg,
                        });
                        this.setState({
                            imageUrl: ''
                        })
                    }else {
                        Modal.error({
                            title: result.msg,
                        });
                    }
                }
            });
        })
    },
    render() {
        console.log("render渲染");
        const {
            getFieldProps
        } = this.props.form;

        var props = this.props;

        const formItemLayout = {
            labelCol: {
                span: 8
            },
            wrapperCol: {
                span: 15
            },
        };

        const formItemLay = {
            type:"flex",
            justify:"center",
            align:"middle"
        };
        return (

                <Form horizontal form={this.props.form}>
                    <Input  {...getFieldProps('id', {initialValue: ''})} type="hidden"/>
                    <div className="navLine-wrap-left">
                        <h2>账户充值</h2>
                    </div>
                    <div className="navLine-wrap-left">
                        <Row>
                            <Col span="12">
                                <FormItem  {...formItemLayout} label="商户id：">
                                    <Select disabled={props.canEdit}  {...getFieldProps('user_id', {rules: [{ required: true, message: '必填'}]})}>
                                        {this.getTagList()}
                                    </Select>
                                </FormItem>
                            </Col>
                            <Col span="12">
                                <FormItem  {...formItemLayout} label="充值方式：">
                                    <Select disabled={props.canEdit}  {...getFieldProps('pay_type', {rules: [{ required: true, message: '必填'}]})}>
                                        {this.getSortList()}
                                    </Select>
                                </FormItem>
                            </Col>
                        </Row>
                        <Row>
                            <Col span="12">
                                <FormItem  {...formItemLayout} label="充值金额：">
                                    <Input disabled={props.canEdit}  {...getFieldProps('amt', {
                                        rules: [{
                                            required: true,
                                            message: '必填'
                                        }]
                                    })} type="text"/>
                                </FormItem>
                            </Col>
                            <Col span="12">
                                <FormItem  {...formItemLayout} label="充值账号：">
                                    <Input disabled={props.canEdit}  {...getFieldProps('pay_account', {rules: [{required: true,message: '必填'}]})} type="text"/>
                                </FormItem>
                            </Col>
                        </Row>
                        <Row>
                            <Col span="12">
                                <FormItem  {...formItemLayout} label="充值录入人：">
                                    <Input disabled={props.canEdit}  {...getFieldProps('hand_person', {
                                        rules: [{
                                            required: true,
                                            message: '必填'
                                        }]
                                    })} type="text"/>
                                </FormItem>
                            </Col>
                        </Row>
                        <Row {...formItemLay}>
                            <Button  type="primary" htmlType="submit"  onClick={this.handleOk}>
                                提 交
                            </Button>
                        </Row>
                    </div>
                </Form>
        );
    }
})
List = createForm()(List);
export default List;