import {Redirect} from 'react-router-dom'
import React, {Component} from 'react';
import request from 'superagent';
import nJwt from 'njwt' ;
import '../login'


export default class GitAuth extends Component {

    authorize = () => {
        const queryString = window.location.search;
        const urlParams = new URLSearchParams(queryString);
        const code = urlParams.get('code');
        if (code) {
            request
                .post("/access_token")
                .send({
                    client_id: 'a5ec3670f1d3cf76a3e6',
                    client_secret: '8e1210d27504f7fea0b5aa368752244c334eed4d',
                    code: code
                })
                .set('Accept', 'application/json')
                .then((res) => {
                    let access_token = res.body.access_token;
                    let claims = {
                        "exp": 864000000
                    };
                    let njwt = nJwt.create(claims, "SomeSecretForJWTGeneration", "HS512");
                    njwt.setSubject("api:" + access_token);
                    let token = njwt.compact();
                    sessionStorage.setItem("authentication", "Bearer " + token);
                    sessionStorage.setItem("username", "Guest");
                    window.location.reload(false);
                })
        }
    };


    render() {
        this.authorize();
        return <Redirect to="/"/>
    }

}


