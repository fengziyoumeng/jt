import React from 'react';
import {Button, Form, Input, Select,Message ,DatePicker} from 'antd';
const createForm = Form.create;
const FormItem = Form.Item;
const Option = Select.Option;
const { RangePicker } = DatePicker;
var beginTime;
var endTime;

let SeachForm = React.createClass({
    getInitialState() {
        return {
            roleList: []
        }
    },
    handleQuery() {
        var params = this.props.form.getFieldsValue();
        var json = { orderCreatStartTime: '', orderCreatEndTime: '',pHandPerson:params.pHandPerson,realName: params.realName, phone: params.phone, orderNo: params.orderNo, state: params.state};
        if (params.orderCreatTime) {
            json.orderCreatStartTime = (DateFormat.formatDate(params.orderCreatTime[0])).substring(0, 10);
            json.orderCreatEndTime = (DateFormat.formatDate(params.orderCreatTime[1])).substring(0, 10);
        }
        this.props.passParams({
            searchParams: JSON.stringify(json),
            pageSize: 10,
            current: 1,
        });
    },
    handleReset() {
        this.props.form.resetFields();
        this.props.passParams({
            pageSize: 10,
            current: 1,
        });
    },
    handleExcelReady(date,dateString){
        beginTime = dateString[0];
        endTime = dateString[1];
    },
    handleOut() {
        window.open("/act/count/excel/flowUvInfo.htm?beginTime="+beginTime+"&endTime="+endTime);
    },
    render() {
        const {getFieldProps} = this.props.form;
        return (
            <Form inline>
                <FormItem label="按日期查询:">
                    <RangePicker   onChange={this.handleQuery}/>
                </FormItem>
            </Form>
        );
    }
});

SeachForm = createForm()(SeachForm);
export default SeachForm;