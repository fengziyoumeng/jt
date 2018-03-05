import React from 'react';
import {
    Button,
    Form,
    Input,
    Modal,
    Select,
    Tree,
    TreeSelect,
    Row,
    Col,
    Upload,
    Icon,
    message
} from 'antd';

const createForm = Form.create;
const FormItem = Form.Item;
const Option = Select.Option;
const objectAssign = require('object-assign');
let treeData = [];
var tagList = [];
var processList = [];
var sortList = [];
var imagesList = [];
var vlu = '';


Utils.ajaxData({
    url: '/act/flowControl/getMutilCheckBox.htm',
    method: 'get',
    type: 'json',
    data: {
        "typeCode": "SJ_CHARGE_TYPE"
    },
    callback: (result) => {
        sortList = result.data;
    }
});

Utils.ajaxData({
    url: '/modules/manage/system/usr/list.htm',
    method: 'get',
    callback: (result) => {
        tagList = result.data;
    }
});

function getBase64(img, callback) {
    const reader = new FileReader();
    reader.addEventListener('load', () => callback(reader.result));
    reader.readAsDataURL(img);
}

var AddFlowInfo = React.createClass({
    getInitialState() {
        return {
            status: {},
            formData: {},
            path: '',
            name: '',
            upFlag: '',
            info: 15
        };
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
            if (!!errors) {
                return;
            }
            if (title == "充值") {
                var data = objectAssign({}, {
                    form: JSON.stringify(objectAssign({'path': path, 'name': name}, values, {}
                    ))
                });
            }
            Utils.ajaxData({
                url: url,
                data: data,
                callback: (result) => {
                    if (result.code == 200) {
                        Modal.success({
                            title: result.msg,
                            onOk: () => {
                                props.hideModal()
                            }
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
    handleChange(info) {
        console.log(info)
        if (info.file.status === 'done') {
            // Get this url from response in real world.
            getBase64(info.file.originFileObj, imageUrl => this.setState({imageUrl}));
            this.path = info.file.response.imgPath
            this.name = info.file.name
        }
    },
    changeVal: function (e) {
        var val = e.target.value;
        var length = val.length;
        /*vlu = val.substring(0,15);*/
        this.setState({"i_val": val.substring(0, 15)});
        length < 16 ? this.setState({"info": (15 - length)}) : "";
    },
    getTagList() {
        return tagList.map((item, index) => {
            return <Option key={item.id}>{item.name}</Option>
        })
    },
    getProcessList() {
        return processList.map((item, index) => {
            return <Option key={item.itemCode}>{item.itemValue}</Option>
        })
    },
    getSortList() {
        return sortList.map((item, index) => {
            return <Option key={item.itemCode}>{item.itemValue}</Option>
        })
    },
    getBackgroundImageList() {
        return imagesList.map((item, index) => {
            return <Option key={item.itemCode}>{item.itemValue}</Option>
        })
    },
    setVlue(vlu) {
        this.setState({"i_val": vlu});
    },

    render() {
        const {
            getFieldProps
        } = this.props.form;
        var imageUrl = this.state.imageUrl;
        var imageOss = {...getFieldProps('picUrl')}.value;
        vlu = {...getFieldProps('pmessage')}.value;

        var showImg
        if (imageOss == undefined) {
            showImg = imageUrl
        } else {
            showImg = imageOss
        }
        var props = this.props;
        var state = this.state;
        var modalBtns = [
            <button key="back" className="ant-btn" onClick={this.handleCancel}>返 回</button>,
            <button key="button" className="ant-btn ant-btn-primary" onClick={this.handleOk}>
                提 交
            </button>
        ];
        const formItemLayout = {
            labelCol: {
                span: 8
            },
            wrapperCol: {
                span: 15
            },
        };
        return (
            <Modal title={props.title} visible={props.visible} onCancel={this.handleCancel} width="900"
                   footer={modalBtns}>
                <Form horizontal form={this.props.form}>
                    <Input  {...getFieldProps('id', {initialValue: ''})} type="hidden"/>

                    <div className="navLine-wrap-left">
                        <Row>
                            <Col span="12">
                                <FormItem  {...formItemLayout} label="商户id：">
                                    <Select disabled={!props.canEdit}  {...getFieldProps('user_id', {rules: [{ required: true, message: '必填'}]})}>
                                        {this.getTagList()}
                                    </Select>
                                </FormItem>
                            </Col>
                            <Col span="12">
                                <FormItem  {...formItemLayout} label="充值方式：">
                                    <Select disabled={!props.canEdit}  {...getFieldProps('pay_type', {rules: [{ required: true, message: '必填'}]})}>
                                        {this.getSortList()}
                                    </Select>
                                </FormItem>
                            </Col>
                        </Row>
                        <Row>
                            <Col span="12">
                                <FormItem  {...formItemLayout} label="充值金额：">
                                    <Input disabled={!props.canEdit}  {...getFieldProps('amt', {
                                        rules: [{
                                            required: true,
                                            message: '必填'
                                        }]
                                    })} type="text"/>
                                </FormItem>
                            </Col>
                            <Col span="12">
                                <FormItem  {...formItemLayout} label="充值账号：">
                                    <Input disabled={!props.canEdit}  {...getFieldProps('pay_account', {rules: [{required: true,message: '必填'}]})} type="text"/>
                                </FormItem>
                            </Col>
                        </Row>
                        <Row>
                            <Col span="12">
                                <FormItem  {...formItemLayout} label="充值录入人：">
                                    <Input disabled={!props.canEdit}  {...getFieldProps('hand_person', {
                                        rules: [{
                                            required: true,
                                            message: '必填'
                                        }]
                                    })} type="text"/>
                                </FormItem>
                            </Col>
                        </Row>
                    </div>
                </Form>
            </Modal>
        );
    }
});
AddFlowInfo = createForm()(AddFlowInfo);
export default AddFlowInfo;
