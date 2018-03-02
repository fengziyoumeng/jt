import React from 'react'
import {
    Table,
    Modal,
    Popover
} from 'antd';
var confirm = Modal.confirm;
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
    hideModal() {
        this.setState({
            visible: false,
            visibleLook: false,
            assignVisible: false
        });
        var pagination = this.state.pagination;
        this.fetch();
    },
    //点击量
    showModal1(title, record, canEdit) {
        // var record = this.state.selectedRow;
        this.setState({
            visibleLook: true,
            canEdit: canEdit,
            record: record,
            title: title
        },() => {
            Utils.ajaxData({
                url: '/act/flowControl/getPlatFormClick.htm',
                method: "post",
                data: {code:record.pcode},
                callback: (result) => {
                    if(result.code == '200'){
                        this.refs.Lookdetails.setFieldsValue(result);
                        this.setState({
                            detail: result.data,
                        });
                    }else{
                        Modal.error({
                            title: result.msg
                        })
                    }

                }
            });
        })
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
    componentWillReceiveProps(nextProps, nextState) {
        this.setState({
            params: nextProps.params,
        });
        this.fetch(nextProps.params);
    },
    componentDidMount() {
        this.fetch();

    },
    //分页
    handleTableChange(pagination, filters, sorter) {
        const pager = this.state.pagination;
        pager.current = pagination.current;
        this.setState({
            pagination: pager,
        });
    },
    fetch(params = {pageSize: 10,
        current: 1}) {
        Utils.ajaxData({
            url: '/modules/manage/system/user/find.htm',
            method: 'get',
            callback: (result) => {
                console.log("---："+result.sysUser.id);
                this.setState({
                    record: result.sysUser.id
                });
                params.userId = this.state.record;
                Utils.ajaxData({
                    url: '/acc/withCheck/list.htm',
                    method: "post",
                    data: params,
                    callback: (result) => {
                        console.log("-12-："+this.state.record);
                        const pagination = this.state.pagination;
                        pagination.current = params.current;
                        pagination.pageSize = params.pageSize;
                        pagination.total = result.page.total;
                        pagination.showTotal = () => `总共 ${result.page.total} 条`;
                        if (!pagination.current) {
                            pagination.current = 1
                        }
                        this.setState({
                            loading: false,
                            data: result.data,
                            pagination
                        });
                    }
                });
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
    delete(record) {
        var me = this;
        confirm({
            title: '确认要删除这项内容,不可恢复！',
            onOk: function () {
                Utils.ajaxData({
                    url: "/act/flowControl/delete.htm",
                    data: {
                        id: record.id,
                        code: record.code,
                        picName:record.picName
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
            title: '日期',
            dataIndex: 'date',
            render:(text) => text.substring(0,10)
        }, {
            title: '用户数',
            dataIndex: "count_borrower"
        }, {
            title: 'CPA单价（元）',
            dataIndex: "unit_price"
        },  {
            title: '扣款金额（元）',
            dataIndex: "amt"
        }, {
            title: '更新时间',
            dataIndex: "update_date"
        }];
        var state = this.state;
        return (
            <div className="block-panel">
                <Table columns={columns} rowKey={this.rowKey}  size="middle"  params ={this.props.params}
                       dataSource={this.state.data}
                       pagination={this.state.pagination}
                       loading={this.state.loading}
                       onChange={this.handleTableChange}  />
            </div>
        );
    }
})