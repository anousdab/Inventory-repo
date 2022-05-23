import React, {Component} from 'react';
import {Link, withRouter} from 'react-router-dom';
import {Button, Container, Form, FormGroup, Input, Label} from 'reactstrap';
import AppNavbar from './AppNavBar';

class ItemDelete extends Component {

    emptyItem = {
        name: '',
    };

    constructor(props) {
        super(props);
        this.state = {
            item: this.emptyItem
        };
        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    async componentDidMount() {
        if (this.props.match.params.id !== 'new') {
            const item = await (await fetch(`/inventory/${this.props.match.params.id}`)).json();
            this.setState({item: item});
        }
    }

    handleChange(event) {
        const target = event.target;
        const value = target.value;
        const name = target.name;
        let item = {...this.state.item};
        item[name] = value;
        this.setState({item});
    }

    async handleSubmit(event) {
        event.preventDefault();
        const {item} = this.state;

        await fetch('/inventory' + (item.id ? '/' + item.id : ''), {
            method: (item.id) ? 'PUT' : 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(item),
        });
        this.props.history.push('/');
        await this.remove(item.id)
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


    render() {
        const {item} = this.state;
        const title = <h2>{item.id ? 'Delete Item' : 'Delete item'}</h2>;

        return <div>
            <AppNavbar/>
            <Container>
                {title}
                <Form onSubmit={this.handleSubmit}>
                    <FormGroup>
                        <Label for="name">Name</Label>
                        <Input type="text" name="name" id="name" value={item.name || ''}
                               onChange={this.handleChange} autoComplete="name"/>
                    </FormGroup>
                    <FormGroup>
                        <Label for="quantity">Quantity</Label>
                        <Input type="text" name="quantity" id="quantity" value={item.quantity || ''}
                               onChange={this.handleChange} autoComplete="quantity"/>
                    </FormGroup><FormGroup>
                    <Label for="location">Location</Label>
                    <Input type="text" name="location" id="location" value={item.location || ''}
                           onChange={this.handleChange} autoComplete="location"/>
                </FormGroup>
                    <FormGroup>
                        <Label for="name">Deletion Message</Label>
                        <Input type="text" name="deletionMessage" id="deletionMessage"
                               value={item.deletionMessage || ''}
                               onChange={this.handleChange} autoComplete="deletionMessage"/>
                    </FormGroup>

                    <FormGroup>
                        <Button color="warning" type="submit">Delete</Button>{' '}
                        <Button color="danger" tag={Link} to="/">Cancel</Button>
                    </FormGroup>
                </Form>
            </Container>
        </div>
    }

}

export default withRouter(ItemDelete);