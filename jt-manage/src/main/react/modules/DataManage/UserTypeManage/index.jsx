/*角色管理*/
import React from 'react';
import List from './Components/List';
export default React.createClass({

    getInitialState() {
        return {
            params: {}
        }
    },
    render() {
        return <div>
                <List params={this.state.params} canEdit={true}/>
            </div>
    }
});
