import 'materialize-css/dist/css/materialize.min.css';
import React, {Component} from "react";
import {execute} from './js/newsapi';
import NAV from './nav';
import './css/home.css';

export default class Home extends Component {

    constructor(props) {
        super(props);
        this.state = ({
            notMounted: true,
            data: []
        })
    }

    updateData = (data) => {
        this.setState({
            notMounted: false,
            data: data
        });
    };

    componentDidMount() {
        if (this.state.notMounted) {
            execute(this.updateData);
        }
    }

    render() {
        return (
            <div>
                <NAV data={this.state.data}/>
            </div>
        )
    }

}

