import CONTENT from "./components/pages/homenewsContent";
import ADD_NEWS from "./components/pages/addnewsContent";
import LOGIN from './components/pages/authentication';
import {BrowserRouter, Route} from 'react-router-dom';
import {Footer} from './components/common/js';
import HOME from './components/pages/home';
import GitAuth from './components/login/js/github'
import React, {Component} from "react";
import './App.css';


class App extends Component {

    render() {
        return (
            <BrowserRouter>
                <div className="App">
                    <Route exact path ="/oauth2/code/github" component={GitAuth}/>
                    <Route exact path="/" component={LOGIN}/>
                    <Route path="/home" component={HOME}/>
                    <Route exact path="/home" component={CONTENT}/>
                    <Route exact path="/home/news" component={ADD_NEWS}/>
                </div>
                <Footer/>
            </BrowserRouter>
        );
    }
}


export default App;
