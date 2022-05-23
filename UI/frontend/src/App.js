import React, {Component} from 'react';
import {BrowserRouter as Router, Route, Switch} from 'react-router-dom';

import ItemList from "./itemList";
import ItemEdit from "./ItemEdit";
import ItemDeletedList from "./itemDeletedList";
import ItemDelete from "./itemDelete";

class App extends Component {
    render() {
        return (
            <Router>
                <Switch>
                    <Route path='/' exact={true} component={ItemList}/>
                    <Route path='/items/deleted' exact={true} component={ItemDeletedList}/>
                    <Route path='/items/:id' component={ItemEdit}/>
                    <Route path='/delete/item/:id' component={ItemDelete}/>
                </Switch>
            </Router>
        )
    }
}

export default App;