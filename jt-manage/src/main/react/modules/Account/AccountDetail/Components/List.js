import React from 'react'
import { Table, Modal, Icon, Button } from 'antd';
import AddFlowInfo from './AddFlowInfo'
const objectAssign = require('object-assign');
var sysusername;
export default React.createClass({
    getInitialState() {
        return {
            selectedRowKeys: [], // 这里配置默认勾选列
            loading: false,
            data: [],
            pagination: {
                pageSize: 10,
                current: 1
            },
            canEdit: true,
            visible: false,
            visible1: false,
            visible2: false,
            pictureData: [],
            creditReportData: [],
            rowRecord: [],
            dataRecord: '',
            record: "",
            visibleAc: false,

        };
    },

    componentWillReceiveProps(nextProps, nextState) {
        this.fetch(nextProps.params);
    },

    queryUserMessage() {
        Utils.ajaxData({
            url: '/modules/manage/system/user/find.htm',
            method: 'get',
            async:false,
            callback: (result) => {
                sysuser = result;
            }
        });
    },

    componentDidMount() {
        this.fetch();
        []
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
                    url: '/acc/accountDetail/list.htm',
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

    showModal(title, record, canEdit) {
        if (title == '充值') {
            record = null
        }
        this.setState({
            canEdit: canEdit,
            visible: true,
            title: title,
            record: record
        });
    },

    //隐藏弹窗
    hideModal() {
        console.log(1)
        this.setState({
            visible: false,
            visible1: false,
            visible2: false,
            selectedIndex: '',
            selectedRowKeys: [],
            visibleAc: false,
            dataRecord: ''
        });
        this.refreshList();
    },
    rowKey(record) {
        return record.id;
    },

    //选择
    onSelectChange(selectedRowKeys) {
        this.setState({
            selectedRowKeys
        });
    },

    //分页
    handleTableChange(pagination, filters, sorter) {
        const pager = this.state.pagination;
        pager.current = pagination.current;
        this.setState({
            pagination: pager,
        });
        this.refreshList();
    },

    refreshList() {
        var pagination = this.state.pagination;
        var params = objectAssign({}, this.props.params, {
            current: pagination.current,
            pageSize: pagination.pageSize
        });
        this.fetch(params);
    },

    //行点击事件
    onRowClick(record) {
        //console.log(record)
        this.setState({
            selectedRowKeys: [record.id],
            selectedRow: record,
            rowRecord: record
        }, () => {
            this
        });
    },

    // 列表添加选中行样式
    rowClassName(record) {
        let selected = this.state.selectedIndex;
        //console.log('selected', this.state.selectedIndex)
        return (record.id == selected && selected !== '') ? 'selectRow' : '';

    },

    //选择
    onSelectAll(selected, selectedRowKeys, selectedRows, changeRows) {
        if (selected) {
            this.setState({
                selectedRowKeys
            })
        } else {
            this.setState({
                selectedRowKeys: []
            })
        }
    },

    render() {
        const {
            loading,
            selectedRowKeys
            } = this.state;
        const rowSelection = {
            type: 'checkbox',
            selectedRowKeys,
            onSelectAll: this.onSelectAll,
        };
        let me = this;
        const hasSelected = selectedRowKeys.length > 0;
        let openEdit = true;
        if (hasSelected && selectedRowKeys.indexOf("0") === -1) {
            openEdit = false;
        }
        var columns = [
            {
                title: '充值时间',
                dataIndex: 'create_time'
            },
            {
                title: '充值金额（元）',
                dataIndex: 'amt'
            }];

        var state = this.state;
        return (
            <div className="block-panel">
                {/*<div className="actionBtns" style={{ marginBottom: 16 }}>*/}
                    {/*<button className="ant-btn" onClick={this.showModal.bind(this, '充值', null, true) }>*/}
                        {/*充值*/}
                    {/*</button>*/}
                {/*</div>*/}
                <Table columns={columns} rowKey={this.rowKey}
                       onRowClick={this.onRowClick}
                       dataSource={this.state.data}
                       pagination={this.state.pagination}
                       loading={this.state.loading}
                       onChange={this.handleTableChange} />
                <AddFlowInfo ref="AddFlowInfo"  visible={state.visible}    title={state.title} hideModal={me.hideModal} record={state.record}
                             canEdit={state.canEdit}/>
            </div>
        );

    }
})
