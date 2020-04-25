import {faHome, faSearch, faBroom , faEdit} from "@fortawesome/free-solid-svg-icons/index";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome/index";
import LinearProgress from '@material-ui/core/LinearProgress';
import {compareByAuthor} from "./js/tools";
import Autocomplete from '@material-ui/lab/Autocomplete';
import TextField from '@material-ui/core/TextField';
import {Redirect} from 'react-router-dom';
import React, {Component} from 'react';

import './css/nav.css';


export default class Nav extends Component {

    constructor(props) {
        super(props);
        this.state = {
            username: sessionStorage.getItem("username"),
            auth: sessionStorage.getItem("authentication"),
        }
    }

    onLogout = () => {
        sessionStorage.clear();
        const initialState = {};
        this.setState(initialState);
        return <Redirect to="home/news"/>;
    };

    isNotLoggedIn = () => {
        return (this.state.auth == null)
    };

    render() {
        if (this.isNotLoggedIn()) {
            return <Redirect to="/"/>;
        }
        return (
            <div>
                <nav className="navigator">
                    <header className="header">
                        <h1 className="top">NEWS API</h1>
                        <div className="hello_message">
                            <span>Hello {this.state.username}</span>
                            <a className="register_login_a" onClick={this.onLogout} href="/">Logout</a>
                        </div>
                    </header>

                    <menu className="users_menu">
                        <ul>
                            <li>
                                <a href="http://localhost:3000/home">
                                    <FontAwesomeIcon className="home-icon" icon={faHome} color="#2f2f2f"/>
                                </a>
                            </li>
                            <li>
                                <form className="search-nav">
                                    <label>Title</label>
                                    <div className="input-search-query">
                                        <Autocomplete
                                            id="title-query"
                                            clearOnEscape
                                            options={this.props.data}
                                            getOptionLabel={(option) => option.title}
                                            renderInput={(params) => <TextField  {...params} label="news"
                                                                                 margin="none"/>}
                                        />
                                    </div>
                                    <div className="input-search-query">
                                        <Autocomplete
                                            id="author-query"
                                            style={{width: 100}}
                                            options={this.props.data.sort(compareByAuthor)}
                                            clearOnEscape
                                            getOptionLabel={(option) => option.author.name}
                                            renderInput={(params) => <TextField  {...params} label="author"
                                                                                 margin="none"/>}
                                        />
                                    </div>
                                    <button id="search-btn" type="submit"><FontAwesomeIcon icon={faSearch}/></button>
                                    <button id="reset-btn" type="submit"><FontAwesomeIcon icon={faBroom}/></button>
                                    <button id="add-news-btn" type="submit" ><FontAwesomeIcon icon={faEdit}/></button>
                                </form>
                            </li>
                        </ul>
                    </menu>
                </nav>
                <LinearProgress id="loader"/>
            </div>
        )
    }
}