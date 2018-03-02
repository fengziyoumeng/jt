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
    setAuditStatus(value,record) {
        var me = this;
        Utils.ajaxData({
            url: "/act/set/userdata/status.htm",
            data: {
                borrowerId:value.userId,
                audit: record.key,
            },
            method: 'post',
            callback: (result) => {
                me.refreshList();
            }
        });
    },
    handleMenuClick(value,record){
        this.setAuditStatus(value,record);
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
                if(value==0||value==null){
                    return "未审核"
                }else if(value==1){
                    return <font color={"green"}>已通过</font>
                }else if(value==2){
                    return <font color={"red"}>未通过</font>
                }
            }
        },{
            title: '操作',
            dataIndex: "",
            render(value, record) {
                return <div style={{ textAlign: "left" }}>
                    <Dropdown  overlay={
                        <Menu onClick={me.handleMenuClick.bind(value,record)}>
                            <Menu.Item key="1" disabled={record.audit!=0&&record.audit!=null?true:false}>审核通过</Menu.Item>
                            <Menu.Item key="2" disabled={record.audit!=0&&record.audit!=null?true:false}>审核拒绝</Menu.Item>
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