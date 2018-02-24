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
        }
    },
    fetch() {
        var me = this;
        this.setState({
            loading: true
        });
        Utils.ajaxData({
            url: '/modules/manage/count/homeInfo.htm',
            method: "get",
            callback: (result) => {
                console.log(result.data);
                me.setState({
                    loading: false,
                    data: result.data,
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
        if(true){
            alert("账户余额不足请及时充值！");
        }
        return (
            <div style={{display: userMessage.name&&userMessage.name!='代理商' ? 'block' : 'none' }}>
                <div className="block-panel">
                    <h2 className="navLine-title">信息预览</h2>
                    <div className='blk-top'>
                        <div className='blk-top-item'>
                            <div className='blk-title'>余额</div>
                            <div className='blk-number'><a href="http://www.baidu.com">0.00</a></div>
                        </div>
                        <div className='blk-top-item'>
                            <div className='blk-title'>用户数（单位：个）</div>
                            <div className='blk-number'><a href="http://www.baidu.com">2001</a></div>
                        </div>
                    </div>
                </div>
            </div>
        )
    }
});