import React, {Component} from "react";
import loginImg from '../../assert/login.svg';
import axios from 'axios';

export class Register extends Component {

    constructor(props) {
        super(props);
        this.state = {
            name: '',
            surname: '',
            login: '',
            password: '',
            roles: {role: 'user'},
            message: '',
            redirect: false,
        };
    }

    handleSubmit = (e) => {
        e.preventDefault();
        if (this.notValidate()) {
            return;
        }
        this.setState({message: ''});
        const {name, surname, login, password, roles} = this.state;
        console.log({name, surname, login, password, roles});
        axios.post("user/", {name, surname, login, password, roles})
            .then(res => {
                console.log(res);
                this.setState({redirect: true, message: 'success !!!'});
            })
            .catch((e) => {
                this.setState({message: e.getMessage});
            });
    };

    handleChange = (e) => {
        e.preventDefault();
        this.setState({[e.target.name]: e.target.value,message:''});
    };

    renderRedirect = () => {
        if (this.state.redirect) {
            window.location.reload(false);
        }
    };

    render() {
        return (
            <div className="base-container" ref={this.props.containerRef}>
                {this.renderRedirect()}
                <main className="content">
                    <header className="header-login">Register</header>
                    <figure className="image">
                        <img src={loginImg} alt={""}/>
                    </figure>
                    <form className="form" onSubmit={this.handleSubmit}>
                        <div className="form-group">
                            <label htmlFor="name">Name</label>
                            <input id="name" type="text" name="name" placeholder="name"
                                   onChange={this.handleChange}/>
                        </div>
                        <div className="form-group">
                            <label htmlFor="surname">Surname</label>
                            <input type="text" name="surname" placeholder="surname"
                                   onChange={this.handleChange}/>
                        </div>
                        <div className="form-group">
                            <label htmlFor="login">Login</label>
                            <input type="text" name="login" placeholder="login"
                                   onChange={this.handleChange}/>
                        </div>
                        <div className="form-group">
                            <label htmlFor="password">Password</label>
                            <input type="password" name="password" placeholder="password" autoComplete="on"
                                   onChange={this.handleChange}/>
                        </div>
                        <label htmlFor="error">{this.state.message}</label>
                        <footer className="footer-login">
                            <input type="submit" id="submit-register" className="btn-more" value="Register"/>
                        </footer>
                    </form>
                </main>
            </div>

        )
    }

    notValidate = () => {
        const {name, surname, login, password} = this.state;
        let letterDigit = /^[A-Za-z0-9]{4,20}$/;
        let letter = /^[A-Za-z]{4,20}$/;

        if (!name.match(letter)) {
            this.setState({message: 'check name'});
            return true;
        }  else if (!surname.match(letter)) {
            this.setState({message: 'check surname'});
            return true;
        } else if (!login.match(letterDigit)) {
            this.setState({message: 'check login'});
            return true;
        } else if (password.length < 4) {
            this.setState({message: 'password is too short'});
            return true;
        }
        return false;

    }

}