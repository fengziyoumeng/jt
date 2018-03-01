import React from 'react';
import {Button, Form, Input, Select,Message ,DatePicker} from 'antd';

const createForm = Form.create;
const FormItem = Form.Item;
const Option = Select.Option;
const RangePicker = DatePicker.RangePicker;

let SeachForm = React.createClass({
    getInitialState() {
        return {
            roleList: [],
            selectValue:""
        }
    },
    handleQuery() {
        var params = this.props.form.getFieldsValue();
        var json = { mobile:params.mobile,audit: this.state.audit,start:this.state.start,end:this.state.end};
        this.props.passParams({
            searchParams: JSON.stringify(json),
            pageSize: 10,
            current: 1,
        });
    },
    handleReset() {
        // this.props.form.resetFields();
        this.props.passParams({
            pageSize: 10,
            current: 1,
        });
    },
    handleOut() {
        var params = this.props.form.getFieldsValue();
        var json = JSON.stringify(params);
        window.open("/modules/manage/borrow/export.htm?searchParams="+json);

    },
    onChangeDate(date, dateString) {
        this.setState({
            start:dateString[0],
            end:dateString[1]
        });
    },
    handleChange(value){
        this.setState({
            audit:value
        });
    },
    render() {
        var me = this;
        const {getFieldProps} = this.props.form;
        return (
            <Form inline>
                 <FormItem label="日期:">
                     <RangePicker allowClear={true} onChange={this.onChangeDate}/>
                 </FormItem>
                 <FormItem label="手机号码:">
                      <Input  {...getFieldProps('mobile')} />
                 </FormItem>
                 <FormItem label="状态:">
                     <Select defaultValue={me.state.selectValue}  style={{ width: 120 }} onChange={this.handleChange}>
                         <Option value="0">未审核</Option>
                         <Option value="1">已通过</Option>
                         <Option value="2" >未通过</Option>
                         <Option value="">全部</Option>
                     </Select>
                 </FormItem>
                 <FormItem><Button type="primary" onClick={this.handleQuery}>查询</Button></FormItem>
                 <FormItem><Button type="reset" onClick={this.handleReset}>重置</Button></FormItem>
                 {/*<FormItem><Button type="export" onClick={this.handleExport}>导出</Button></FormItem>*/}
            </Form>
        );
    }
});

SeachForm = createForm()(SeachForm);
export default SeachForm;