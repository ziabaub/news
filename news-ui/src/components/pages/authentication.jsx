import React from "react";
import "./css/authentication.css";
import {Login, Register} from "../login/js";

class Authentication extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            isLogginActive: true
        };
    }

    componentDidMount = () => {
        this.rightSide.classList.add("right");
    };

    changeState = () => {
        const {isLogginActive} = this.state;

        if (isLogginActive) {
            this.rightSide.classList.remove("right");
            this.rightSide.classList.add("left");
        } else {
            this.rightSide.classList.remove("left");
            this.rightSide.classList.add("right");
        }
        this.setState(prevState => ({isLogginActive: !prevState.isLogginActive}));
    };

    render() {
        const {isLogginActive} = this.state;
        const current = isLogginActive ? "Register" : "Login";
        const currentActive = isLogginActive ? "login" : "register";
        return (
            <div className="login">
                <hgroup className="container" ref={ref => (this.container = ref)}>
                    {isLogginActive && (
                        <Login containerRef={ref => (this.current = ref)}/>
                    )}
                    {!isLogginActive && (
                        <Register containerRef={ref => (this.current = ref)}/>
                    )}
                </hgroup>
                <RightSide
                    current={current}
                    currentActive={currentActive}
                    containerRef={ref => (this.rightSide = ref)}
                    onClick={this.changeState}
                />
            </div>
        );
    }
}

const RightSide = props => {
    return (
        <hgroup
            className="right-side"
            ref={props.containerRef}
            onClick={props.onClick}
        >
            <div className="inner-container">
                <div className="text">{props.current}</div>
            </div>
        </hgroup>
    );
};

export default Authentication;
