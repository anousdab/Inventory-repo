// import {Component} from "react";
//
// class App extends Component {
//   state = {
//     items: []
//   };
//
//   async componentDidMount() {
//     const response = await fetch('/inventory');
//     const body = await response.json();
//     this.setState({items: body});
//   }
//
//   render() {
//     const {items} = this.state;
//     return (
//         <div className="App">
//           <header className="App-header">
//             <div className="App-intro">
//               <h2>Items</h2>
//               {items.map(item =>
//                   <div key={item.id}>
//                     {item.name}
//                   </div>
//               )}
//             </div>
//           </header>
//         </div>
//     );
//   }
// }
// export default App;

import React, {Component} from 'react';
import './App.css';
import Home from './Home';
import {BrowserRouter as Router, Route, Switch} from 'react-router-dom';

import ItemList from "./itemList";
import ItemEdit from "./ItemEdit";

class App extends Component {
    render() {
        return (
            <Router>
                <Switch>
                    <Route path='/' exact={true} component={Home}/>
                    <Route path='/items' exact={true} component={ItemList}/>
                    <Route path='/items/:id' component={ItemEdit}/>
                </Switch>
            </Router>
        )
    }
}

export default App;