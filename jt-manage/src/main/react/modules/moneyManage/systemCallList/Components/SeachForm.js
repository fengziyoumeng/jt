import React from 'react';
import {Button, Form, Input, Select,Message,DatePicker } from 'antd';
const createForm = Form.create;
const RangePicker = DatePicker.RangePicker;
const FormItem = Form.Item;
const Option = Select.Option;

let SeachForm = React.createClass({
    getInitialState() {
        return {
            roleList: []
        }
    },
    handleQuery() {
        var params = this.props.form.getFieldsValue();
         var json = {startTime:'',endTime:'',type:'20',state:params.state,realName:params.realName,phone:params.phone};
     //console.log(params);
     if(params.time){
        var d = new Date(params.time[0]);  
        json.startTime=d.getFullYear() + '-' + (d.getMonth() + 1) + '-' + d.getDate() + ' 00:00:00';
        var d1 = new Date(params.time[1]);  
        json.endTime=d1.getFullYear() + '-' + (d1.getMonth() + 1) + '-' + d1.getDate() + ' 23:59:59';
        //console.log(d);
      }
        this.props.passParams({
            search: JSON.stringify(json),
            pageSize: 10,
            current: 1,
        });
    },
    handleReset() {
        this.props.form.resetFields();
        this.props.passParams({
            search: JSON.stringify({type: "20"}),
            pageSize: 10,
            current: 1,
        });
    },
    render() {

        const {getFieldProps} = this.props.form;

        return (
            <Form inline>
            <input type="hidden" {...getFieldProps('type',{initialValue: '20'})}/>
            <FormItem label="打款时间:">
                  <RangePicker {...getFieldProps('time', { initialValue: '' }) } />
             </FormItem>
             <FormItem label="收款人姓名:">
                  <Input  {...getFieldProps('realName',{initialValue: ''})} />
             </FormItem>
             <FormItem label="手机号码:">
                  <Input  {...getFieldProps('phone',{initialValue: ''})} />
             </FormItem>
             <FormItem label="状态:">
             <Select style={{ width: 100 }} {...getFieldProps('state',{initialValue: ''})} placeholder='请选择...'>
                 <Option value="">全部</Option>
                 <Option value="10">待支付</Option>
                 <Option value="40">支付成功</Option>
                 <Option value="50">支付失败</Option>
                 <Option value="20">审核通过</Option>
                 <Option value="30">审核不通过</Option>
                 <Option value="15">待审核</Option>
             </Select>
             </FormItem>
             <FormItem><Button type="primary" onClick={this.handleQuery}>查询</Button></FormItem>
             <FormItem><Button type="reset" onClick={this.handleReset}>重置</Button></FormItem>
            </Form>
        );
    }
});

SeachForm = createForm()(SeachForm);
export default SeachForm;