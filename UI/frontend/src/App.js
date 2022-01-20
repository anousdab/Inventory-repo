
import React, {Component} from 'react';
import {BrowserRouter as Router, Route, Switch} from 'react-router-dom';

import ItemList from "./itemList";
import ItemEdit from "./ItemEdit";

class App extends Component {
    render() {
        return (
            <Router>
                <Switch>
                    <Route path='/' exact={true} component={ItemList}/>
                    <Route path='/items/:id' component={ItemEdit}/>
                </Switch>
            </Router>
        )
    }
}

export default App;