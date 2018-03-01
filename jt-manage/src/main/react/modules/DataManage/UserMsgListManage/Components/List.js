import React from 'react'
import {Dropdown, Button,Icon, Menu, Modal, Table} from 'antd';
import AddFlowInfo from './AddFlowInfo'

var confirm = Modal.confirm;
var userData;
export default React.createClass({
    getInitialState() {
        return {
            selectedRowKeys: [], // 这里配置默认勾选列
            loading: false,
            data: [],
            pagination: {},
            canEdit: true,
            visible: false,
            visibleLook: false,
            assignVisible: false,
        };
    },
    componentWillReceiveProps(nextProps, nextState) {
        this.setState({
            params: nextProps.params,
        });
        console.log(nextProps.params);
        this.fetch(nextProps.params);
    },
    componentDidMount() {
        this.fetch();
    },
    hideModal() {
        this.setState({
            visible: false,
            visibleLook: false,
            assignVisible: false
        });
        var pagination = this.state.pagination;
        this.fetch();
    },
    //新增跟修改弹窗
    showModal(title, record, canEdit) {
        var record = record;
        if (title == '修改') {
            var record = record;
            record.ptag = record.ptag.split(',');
            record.pprocess = record.pprocess.split(',');
            this.refs.AddFlowInfo.setFieldsValue(record);
            //console.log(record);
        } else if (title == '新增') {
            record = null
        }
        this.setState({
            canEdit: canEdit,
            visible: true,
            title: title,
            record: record
        });
    },
    //打开分配弹窗
    showAssignModal(title, record) {
        this.setState({
            assignVisible: true,
            title: title,
            record: record
        });
    },
    rowKey(record) {
        return record.id;
    },

    //分页
    handleTableChange(pagination, filters, sorter) {
        const pager = this.state.pagination;
        pager.current = pagination.current;
        this.setState({
            pagination: pager,
        });
        this.fetch({
            pageSize: pagination.pageSize,
            current: pagination.current,
            params:this.state.params
        });
    },
    fetch(params = {
        pageSize: 10,
        current: 1
    }) {
        this.setState({
            loading: true
        });
        Utils.ajaxData({
            url: '/act/get/userdata/list.htm',
            data: params,
            callback: (result) => {
                const pagination = this.state.pagination;
                pagination.total = result.page.total;
                this.setState({
                    loading: false,
                    data: result.data,
                    pagination,
                });
                this.clearList();
            }
        });
    },
    clearList() {
        this.setState({
            selectedRowKeys: [],
        });
    },
    refreshList() {
        this.fetch();
    },
    setAuditStatus(record) {
        var me = this;
        confirm({
            title: '删除后不可恢复，确定要删除吗？',
            onOk: function () {
                Utils.ajaxData({
                    url: "/act/set/userdata/status.htm",
                    data: {
                        id: record.id,
                    },
                    method: 'post',
                    callback: (result) => {
                        Modal.success({
                            title: result.msg,
                        });
                        me.refreshList();
                    }
                });
            },
            onCancel: function () { }
        });
    },
    handleMenuClick(record){

    },
    setUserId(record){
    },
    render() {
        var me = this;
        const {
            loading,
            selectedRowKeys
        } = this.state;
        const rowSelection = {
            selectedRowKeys,
            onChange: this.onSelectChange,
        };
        const hasSelected = selectedRowKeys.length > 0;

        /*const menu = (
            <Menu onClick={this.handleMenuClick}>
                <Menu.Item key="1">审核通过</Menu.Item>
                <Menu.Item key="2">审核拒绝</Menu.Item>
            </Menu>
        );*/

        var columns = [{
            title: '手机号',
            dataIndex: 'authMobile'
        }, {
            title: '运营商',
            dataIndex: "operatorStatus",
        }, {
            title: '姓名',
            dataIndex: "realName",
        },  {
            title: '年龄',
            dataIndex: "age"
        }, {
            title: '性别',
            dataIndex: "sex",
        },{
            title: '地区',
            dataIndex: "provinceAddress",
        },{
            title: 'QQ号',
            dataIndex: "qq"
        },{
            title: '芝麻分',
            dataIndex: "zhimaScore"
        }, {
            title: '日期',
            dataIndex: "addTime"
        }, {
            title: '扣款',
            dataIndex: "price"  ,
        }, {
            title: '状态',
            dataIndex: "audit",
            render(value,record){
                if(value==0){
                    return "未审核"
                }else if(value==1){
                    return "已通过"
                }else if(value==2){
                    return "未通过"
                }
            }
        },{
            title: '操作',
            dataIndex: "",
            render(text, record) {
                return <div style={{ textAlign: "left" }}>
                    <Dropdown overlay={
                        <Menu onClick={me.handleMenuClick.bind(record)}>
                            <Menu.Item key="1">审核通过</Menu.Item>
                            <Menu.Item key="2">审核拒绝</Menu.Item>
                        </Menu>}>
                    <Button >
                        管理 <Icon type="down" />
                    </Button>
                    </Dropdown>
                </div>
            }
        }];

        var state = this.state;
        return (
            <div className="block-panel">
                <Table columns={columns} rowKey={this.rowKey}  size="middle"
                       params ={this.props.params}
                       dataSource={this.state.data}
                       pagination={this.state.pagination}
                       loading={this.state.loading}
                       onChange={this.handleTableChange}  />
                <AddFlowInfo ref="AddFlowInfo"
                             visible={state.visible}
                             title={state.title}
                             hideModal={me.hideModal}
                             record={state.record}
                             canEdit={state.canEdit}/>
            </div>
        );
    }
})