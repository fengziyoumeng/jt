import React from 'react'
import {
    Table,
    Modal,
    Popover
} from 'antd';
import AddFlowInfo from './AddFlowInfo'
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
    componentWillReceiveProps(nextProps, nextState) {
        this.setState({
            params: nextProps.params,
        });
        // this.fetch(nextProps.params);
    },
    componentDidMount() {
        // this.fetch();
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
        /*console("翻页查询参数>>>>"+this.state.params);
        this.fetch({
            pageSize: pagination.pageSize,
            current: pagination.current,
            params:this.state.params
        });*/
    },
    fetch(params = {
        pageSize: 10,
        current: 1
    }) {
        this.setState({
            loading: true
        });
        Utils.ajaxData({
            url: '/act/flowControl/getInfoManage.htm',
            data: params,
            callback: (result) => {
                // console.info("=======>"+JSON.stringify(result.data));
                const pagination = this.state.pagination;
                pagination.total = result.totalCount;
                if (!pagination.current) {
                    pagination.current = 1
                };
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
    delete(record) {
        var me = this;
        confirm({
            title: '删除后不可恢复，确定要删除吗？',
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
            title: '手机号',
            dataIndex: 'pname'
        }, {
            title: '运营商',
            dataIndex: "minLimit",
        }, {
            title: '姓名',
            dataIndex: "ploanMinTime",
        },  {
            title: '年龄',
            dataIndex: "id"
        }, {
            title: '性别',
            dataIndex: "premark",
        },{
            title: '地区',
            dataIndex: "pcode"
        },{
            title: 'QQ号',
            dataIndex: "pChannelPrice"
        },{
            title: '芝麻分',
            dataIndex: "pHandPerson"
        }, {
            title: '日期',
            dataIndex: "psort"
        }, {
            title: '扣款',
            dataIndex: "pstate"  ,
        }, {
            title: '信息',
            dataIndex: "pBorrowNum"
        },{
            title: '操作',
            dataIndex: "",
        }];

        var state = this.state;
        return (
            <div className="block-panel">
                {/*<div className="actionBtns" style={{ marginBottom: 16 }}>
                    <button className="ant-btn" onClick={this.showModal.bind(this, '新增', null, true) }>
                        新增
                    </button>
                </div>*/}
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