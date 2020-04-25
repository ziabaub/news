import {Redirect} from 'react-router-dom'
import React, {Component} from "react";
import loginImg from "../../assert/login.svg";
import gitImg from "../../assert/github-icon.png";
import axios from 'axios';

export class Login extends Component {

    constructor(props) {
        super(props);
        this.state = {
            username: '',
            password: '',
            message: ''
        };
    }

    handleSubmit = (e) => {
        e.preventDefault();
        if (this.notValidate()) {
            return;
        }
        const {username, password} = this.state;
        axios.post("/login", {username, password})
            .then(res => {
                sessionStorage.setItem("authentication", res.headers.authorization);
                sessionStorage.setItem("username", username);
                this.setState({message: 'logged in'});
            })
            .catch(() => {
                this.setState({message: 'username or password incorrect'});
            });
    };

    renderRedirect = () => {
        let auth = sessionStorage.getItem("authentication");
        if (auth) {
            return <Redirect to='/home'/>
        }
    };

    handleChange = (e) => {
        e.preventDefault();
        this.setState({[e.target.name]: e.target.value, message: ''});
    };

    render() {
        return (
            <div className="base-container" ref={this.props.containerRef}>
                {this.renderRedirect()}
                <main className="content">
                    <header className="header-login">Login</header>
                    <figure className="image">
                        <img src={loginImg} alt={""}/>
                    </figure>
                    <form className="form" onSubmit={this.handleSubmit}>
                        <div className="form-group">
                            <label htmlFor="username">Login</label>
                            <input type="text" name="username" placeholder="username"
                                   onChange={this.handleChange}/>
                        </div>
                        <div className="form-group">
                            <label htmlFor="password">Password</label>
                            <input type="password" name="password" placeholder="password" autoComplete="on"
                                   onChange={this.handleChange}/>
                        </div>
                        <label className="error" htmlFor="error" ref={ref => this.message = ref}>{this.state.message}</label>
                        <footer className="footer-login">
                            <input type="submit" id="submit-login" className="btn-more" value="Login"/>
                        </footer>
                    </form>
                </main>
                <figure className="git-icon">
                    <a href="https://github.com/login/oauth/authorize?client_id=a5ec3670f1d3cf76a3e6">
                        <img width="30px" height="30px" src={gitImg} alt={""}/>
                    </a>
                </figure>
            </div>

        )
    }

    notValidate = () => {
        let letter = /^[A-Za-z0-9]{4,20}$/;
        const {username, password} = this.state;
        if (!username.match(letter)) {
            this.setState({message: 'check username'});
            return true;
        } else if (password.length < 4) {
            this.setState({message: 'password is too short'});
            return true;
        }
        return false;

    }

}