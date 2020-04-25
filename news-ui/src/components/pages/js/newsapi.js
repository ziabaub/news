import M from 'materialize-css/dist/js/materialize.min.js';
import {compareByTitle} from '../js/tools'
import $ from 'jquery';


let url = "http://localhost:8080/news/";
let count = 0;
let filtered = [];
export let jsonData = [];

let init = () => {
    if (typeof EventTarget !== "undefined") {
        let func = EventTarget.prototype.addEventListener;
        EventTarget.prototype.addEventListener = function (type, fn, capture) {
            this.func = func;
            if (typeof capture !== "boolean") {
                capture = capture || {};
                capture.passive = false;
            }
            this.func(type, fn, capture);
        };
    }
};


export let execute = (callback) => {
    init();
    request(url, callback);

    $("#more").on("click", (e) => {
        e.preventDefault();
        $("#content").html(print(filtered));
    });

    $("#reset-btn").on("click", (e) => {
        e.preventDefault();
        count = 0;
        $("#title-query").val("");
        $("#author-query").val("");
        filtered = jsonData;
        $("#content").html(print(filtered));
    });

    $("#search-btn").on("click", (e) => {
        e.preventDefault();
        filter();
        $("#content").html(print(filtered));
    });

    $("#add-news-btn").on("click", (e) => {
        e.preventDefault();
        retrieveData();
    });

    $("#news-btn-delete").on("click", (e) => {
        e.preventDefault();
        updateList()
    });

};

let request = (url, callback) => {
    $.ajax({
        url: url,
        method: "GET",
        dataType: "json",

        beforeSend: function () {
            $("#loader").show();
        },

        complete: function () {
            $("#loader").hide();
            $("#more").show();
        },
        success: function (data) {
            let sorted = data.sort(compareByTitle);
            setData(sorted);
            callback(sorted);
            let output = print(data);
            if (output !== "") {
                $("#content").html(output);
                M.toast({
                    html: 'here we go !!!',
                    classes: 'green'
                });
            } else {
                $("#content").html("");
                M.toast({
                    html: "This news isn't available",
                    classes: 'red'
                });
            }
        },

        error: function () {
            setData("");
            $("#content").html("");
            M.toast({
                html: "error found ",
                classes: 'red'
            });
        }
    })
};

let print = (latestNews) => {
    count += 5;
    let output = "<div class=\"columns\">";
    let headline = 1;
    const keys = Object.keys(latestNews);
    for (let i of keys) {
        output +=
            `<div class="column">
                <div class="head">
                    <span class="headline hl${headline % 6}">${latestNews[i].title}</span>
                      <p>
                          <small>
                            <time>
                                <cite>
                                    <small>Pub:</small>
                                </cite>
                                <small>${latestNews[i].creationDate}</small>
                            </time>
                             <time>
                                <cite>
                                    <small>Last mod:</small>
                                </cite>
                                <small>${latestNews[i].modificationDate}</small>
                            </time>
                          </small>
                          <span class="headline hl${headline % 6 + 1}">${latestNews[i].shortText}</span>
                      </p>
                </div>
               ${latestNews[i].fullText}
               <br/><br/>
               <cite> ${latestNews[i].author.name + " " + latestNews[i].author.surname}</cite>
            </div>`;

        headline += 2;
        if (i >= count - 1) {
            break;
        }
    }
    output += "<div/>";
    return output;
};

let filter = () => {
    let title = $("#title-query").val();
    let auth = $("#author-query").val();
    if (!title && !auth) {
        M.toast({
            html: "input can't be empty",
            classes: 'red'
        });
    }

    if (title) {
        filtered = jsonData.filter(n => n.title.includes(title))
    }
    if (auth) {
        filtered = jsonData.filter(n => n.author.name.includes(auth))
    }
};

let retrieveData = () => {
    let title = $("#title-query").val();
    let auth = $("#author-query").val();
    if (title && auth) {
        let news = jsonData.filter(n => n.title === title && n.author.name === auth)[0];
        let today = new Date();
        news.modificationDate = today.getFullYear() + '-' + (today.getMonth() + 1) + '-' + today.getDate();
        sessionStorage.setItem("news", JSON.stringify(news));
    }
    $(window).attr("location", "/home/news");
};

let updateList = () => {
    let title = $("#title-query").val();
    const index = filtered.findIndex(n => n.title === title);
    if (index !== undefined) {
        filtered.splice(index, 1);
    }
};

let setData = (data) => {
    jsonData = data;
    filtered = data;
};