import React, {Component} from 'react';
import {Button, ButtonGroup, Container, Table} from 'reactstrap';
import AppNavbar from './AppNavBar';
import {Link} from 'react-router-dom';

class ItemList extends Component {

    constructor(props) {
        super(props);
        this.state = {items: []};
        this.remove = this.remove.bind(this);
    }

    componentDidMount() {
        fetch('/inventory')
            .then(response => response.json())
            .then(data => this.setState({items: data}));
    }

    async remove(id) {
        await fetch(`/inventory/${id}`, {
            method: 'DELETE',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        }).then(() => {
            let updatedItems = [...this.state.items].filter(i => i.id !== id);
            this.setState({items: updatedItems});
        });
    }

    async exportItemToCsv(id) {
        await fetch(`/inventory/${id}/download`, {
            method: 'get',
            headers: {
                'Content-Type': 'text/csv'
            }
        }).then(res => res.blob())
            .then(blob => {
                var file = window.URL.createObjectURL(blob);
                window.location.assign(file);
            })
    }

    async exportAllItemToCsv() {
        await fetch(`/inventory/download`, {
            method: 'get',
            headers: {
                'Content-Type': 'text/csv'
            }
        }).then(res => res.blob())
            .then(blob => {
                var file = window.URL.createObjectURL(blob);
                window.location.assign(file);
            })
    }


    render() {
        const {items, isLoading} = this.state;

        if (isLoading) {
            return <p>Loading...</p>;
        }

        const itemList = items.map(item => {
            return <tr key={item.id}>
                <td style={{whiteSpace: 'nowrap'}}>{item.id}</td>
                <td style={{whiteSpace: 'nowrap'}}>{item.name}</td>
                <td>{item.quantity}</td>
                <td>{item.location}</td>
                <td>
                    <ButtonGroup>
                        <Button size="sm" color="success" onClick={() => this.exportItemToCsv(item.id)}>Export to
                            CSV</Button>
                        <Button size="sm" color="primary" tag={Link} to={"/items/" + item.id}>Edit</Button>
                        <Button size="sm" color="danger" tag={Link} to={"/delete/item/" + item.id}>Delete</Button>
                    </ButtonGroup>
                </td>
            </tr>
        });

        return (
            <div>
                <AppNavbar/>
                <Container fluid>
                    <div className="float-right">
                        <Button color="primary" tag={Link} to="/items/new">Add Item</Button>
                        <Button color="success" tag={Link} onClick={() => this.exportAllItemToCsv()}>Export inventory to
                            CSV</Button>
                        <Button color="danger" tag={Link} to="/items/deleted">Recently deleted items</Button>
                    </div>
                    <h3>Inventory</h3>
                    <Table className="mt-4">
                        <thead>
                        <tr>
                            <th width="10%">Id</th>
                            <th width="10%">Name</th>
                            <th width="10%">Quantity</th>
                            <th width="10%">Location</th>
                            <th width="10%">Action</th>
                        </tr>
                        </thead>
                        <tbody>
                        {itemList}
                        </tbody>
                    </Table>
                </Container>
            </div>
        );
    }
}

export default ItemList;