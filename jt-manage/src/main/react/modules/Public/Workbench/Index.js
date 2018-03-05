import React from 'react';
import { Row, Col } from 'antd';
import './style.css';
import Map from './Map';
import Bar1 from './Bar1';
import Bar2 from './Bar2';
import Pie1 from './Pie1';
import Pie2 from './Pie2';
var Reflux = require('reflux');
var AppActions = require('../../frame/actions/AppActions');
var UserMessageStore = require('../../frame/stores/UserMessageStore');
export default React.createClass({
    mixins: [
        Reflux.connect(UserMessageStore, "userMessage")
    ],
    getInitialState() {
        return {
            menuData: [],
            assetsData: {},
            investmentData: {},
            loading: false,
            data: [],
            userMessage: {},
            record: "",
        }
    },
    fetch() {
        var me = this;
        this.setState({
            loading: true
        });

        Utils.ajaxData({
            url: '/modules/manage/system/user/find.htm',
            method: 'get',
            callback: (result) => {
                console.log("----："+result.sysUser.id);
                this.setState({
                    record: result.sysUser.id
                });
                Utils.ajaxData({
                    url: '/acc/account/balance.htm',
                    method: "post",
                    data:{userId:this.state.record},
                    callback: (result) => {
                        console.log(result.data);
                        me.setState({
                            loading: false,
                            data: result.data,
                        });
                    }
                });
            }
        });
    },

    componentDidMount() {
        AppActions.initUserMessageStore();
        this.fetch();
    },
    render() {
        var { data } = this.state;
        var userMessage = this.state.userMessage;
        return (
            <div style={{display: 'block' }}>
                <div className="block-panel">
                    <h2 className="navLine-title">信息预览</h2>
                    <div className='blk-top'>
                        <div className='blk-top-item'>
                            <div className='blk-title'>余额（元）</div>
                            <div className='blk-number'>{data.balance}</div>
                        </div>
                        <div className='blk-top-item'>
                            <div className='blk-title'>用户数（单位：个）</div>
                            <div className='blk-number'>{data.count}</div>
                        </div>
                    </div>
                </div>
            </div>
        )
    }
});