import React, {Component} from 'react';
import {Button, ButtonGroup, Container, Table} from 'reactstrap';
import AppNavbar from './AppNavBar';
import {Link} from 'react-router-dom';

class ItemDeletedList extends Component {

    constructor(props) {
        super(props);
        this.state = {items: []};
        this.undoDeletion = this.undoDeletion.bind(this);
    }

    componentDidMount() {
        fetch('/inventory?is_deleted=true')
            .then(response => response.json())
            .then(data => this.setState({items: data}));
    }

    async undoDeletion(item) {

        item.isDeleted = false;
        console.log(item)

        await fetch('/inventory' + (item.id ? '/' + item.id : ''), {
            method: (item.id) ? 'PUT' : 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(item),
        });
        this.props.history.push('/');
    }

    render() {
        const {items, isLoading} = this.state;

        if (isLoading) {
            return <p>Loading...</p>;
        }

        const itemDeletedList = items.map(item => {
            return <tr key={item.id}>
                <td style={{whiteSpace: 'nowrap'}}>{item.id}</td>
                <td style={{whiteSpace: 'nowrap'}}>{item.name}</td>
                <td>{item.quantity}</td>
                <td>{item.location}</td>
                <td>True</td>
                <td>{item.deletionMessage}</td>
                <td>
                    <ButtonGroup>
                        <Button size="sm" color="danger" onClick={() => this.undoDeletion(item)}>Undo deletion</Button>
                    </ButtonGroup>
                </td>
            </tr>
        });

        return (
            <div>
                <AppNavbar/>
                <Container fluid>
                    <div className="float-right">
                        <Button color="primary" tag={Link} to="/">Go back to inventory</Button>
                    </div>
                    <h3>Inventory of deleted items</h3>
                    <Table className="mt-4">
                        <thead>
                        <tr>
                            <th width="10%">Id</th>
                            <th width="10%">Name</th>
                            <th width="10%">Quantity</th>
                            <th width="10%">Location</th>
                            <th width="10%">isDeleted</th>
                            <th width="10%">DeletionMessage</th>
                            <th width="10%">Action</th>
                        </tr>
                        </thead>
                        <tbody>
                        {itemDeletedList}
                        </tbody>
                    </Table>
                </Container>
            </div>
        );
    }
}

export default ItemDeletedList;