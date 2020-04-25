import {faPlus, faMinus, faBroom} from "@fortawesome/free-solid-svg-icons/index";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome/index";
import Autocomplete from "@material-ui/lab/Autocomplete/Autocomplete";
import TextField from "@material-ui/core/TextField/TextField";
import './css/addnewsContent.css'
import axios from "axios";
import React from 'react';
import $ from 'jquery';


export default class AddNews extends React.Component {

    constructor(props) {
        super(props);
        this.state = this.init();
    }

    init = () => {
        return {
            id: 0,
            title: '',
            author: {
                id: 0,
                name: '',
                surname: ''
            },
            tags: [],
            modificationDate: this.onAddDate(),
            fullText: '',
            shortText: '',
            creationDate: this.onAddDate()
        };
    };

    onReset = () => {
        sessionStorage.removeItem("news");
        this.setState(this.init());

    };

    onDeleteNewsReq = (e) => {
        e.preventDefault();
        const headers = {'Authorization': sessionStorage.getItem("authentication")};
        axios.delete("/news/" + this.state.id, {headers: headers})
            .then(() => {
                this.message = "Deleted Successfully !!!";
                this.onReset();
            })
            .catch(() => {
                this.message = "You have no permission !!!";
                this.setState({});
            });
    };

    onSubmitNewsReq = (e) => {
        e.preventDefault();
        const headers = {'Authorization': sessionStorage.getItem("authentication")};
        if (this.state.id !== 0) {
            this.updateNews(headers);
        } else {
            this.addNews(headers);
        }

    };

    addNews = (headers) => {
        axios.post("/news/", this.state, {headers: headers})
            .then(() => {
                this.message = "Added Successfully !!!";
                this.onReset();
            })
            .catch(() => {
                this.message = "Not Added !!!";
                this.setState({});
            });
    };

    updateNews = (headers) => {
        axios.put("/news/", this.state, {headers: headers})
            .then(() => {
                this.message = "Updated Successfully !!!";
                this.onReset()
            })
            .catch(() => {
                this.message = "Not Updated !!!";
                this.setState({});
            });
    };

    onAddDate = () => {
        let today = new Date();
        return today.getFullYear() + '-' + (today.getMonth() + 1) + '-' + today.getDate();
    };

    onAddNews = (e) => {
        e.preventDefault();
        this.setState({[e.target.name]: e.target.value});
    };

    onAddAuthor = (e) => {
        e.preventDefault();
        this.setState({author: {...this.state.author, [e.target.name]: e.target.value}});
    };

    onAddTag = (e) => {
        e.preventDefault();
        let name = $("#tags-input").val();
        if (name && !this.isContain(name)) {
            let tag = {name: name};
            console.log(tag);
            this.setState({tags: [...this.state.tags, tag]})
        }
    };

    onResetTags = (e) => {
        e.preventDefault();
        this.setState({tags: []});
    };

    onRemoveTag = (e) => {
        e.preventDefault();
        let name = $("#tags-input").val();
        if (name && this.isContain(name)) {
            this.setState({tags: this.state.tags.filter(t => t.name !== name)})
        }
    };

    isContain = (name) => {
        return this.state.tags.filter(t => t.name === name).length > 0;
    };

    componentDidMount =() => {
        let state = JSON.parse(sessionStorage.getItem('news'));
        if (state) {
            this.setState(state);
        }
    };

    render() {
        return (
            <div>
                <div className="content-add-news-title">
                    <span className="headline hl2">News</span>
                </div>
                <div className="content-add-news">
                    <div className="row">
                        <form className="col s12">
                            <div className="row">
                                <div className="input-field col s6">
                                    <input id="author-name" type="text" name="name" maxLength="30"
                                           value={this.state.author.name}
                                           onChange={this.onAddAuthor}/>
                                    <label>Author Name</label>
                                </div>
                                <div className="input-field col s6">
                                    <input id="author-surname" type="text" name="surname" maxLength="30"
                                           value={this.state.author.surname}
                                           onChange={this.onAddAuthor}/>
                                    <label>Author Last Name</label>
                                </div>
                            </div>
                            <div className="row">
                                <div className="input-field col s6">
                                    <input id="news-title" type="text" name="title" maxLength="50"
                                           onChange={this.onAddNews} value={this.state.title}/>
                                    <label>Title</label>
                                </div>
                                <div className="input-field col s6 ">
                                    <Autocomplete
                                        clearOnEscape
                                        freeSolo
                                        id="tags-input"
                                        options={this.state.tags}
                                        getOptionLabel={(option) => option.name}
                                        renderInput={(params) => (<TextField{...params} label="Tag"/>)}
                                    />
                                    <button type="submit" onClick={this.onAddTag}><FontAwesomeIcon icon={faPlus}/>
                                    </button>
                                    <button type="submit" onClick={this.onRemoveTag}><FontAwesomeIcon icon={faMinus}/>
                                    </button>
                                    <button type="submit" onClick={this.onResetTags}><FontAwesomeIcon icon={faBroom}/>
                                    </button>
                                    <small> double click to see added tags</small>
                                </div>
                            </div>
                            <div className="row">
                                <div className="input-field col s6">
                                    <input id="news-shortText" type="text" name="shortText" maxLength="100"
                                           onChange={this.onAddNews} value={this.state.shortText}/>
                                    <label>Brief</label>
                                </div>
                            </div>
                            <div className="row">
                                <div className="input-field col s12">
                                    <textarea id="news-fullText" className="materialize-textarea" maxLength="1000"
                                              name="fullText" onChange={this.onAddNews} value={this.state.fullText}/>
                                    <label>Article</label>
                                </div>
                            </div>
                            <div>
                                <label id="message">{this.message}</label>
                            </div>
                        </form>
                    </div>
                </div>
                <div className="create-news-button">
                    <button type="submit" onClick={this.onSubmitNewsReq}><FontAwesomeIcon icon={faPlus}/></button>
                    <button id="news-btn-delete" type="submit" onClick={this.onDeleteNewsReq}><FontAwesomeIcon
                        icon={faMinus}/></button>
                    <button type="submit" onClick={this.onReset}><FontAwesomeIcon icon={faBroom}/></button>
                </div>
            </div>
        )
    }
}