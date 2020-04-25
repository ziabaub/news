const proxy = require('http-proxy-middleware');

module.exports = (app) => {

    app.use(
        proxy("/login", {
            target: "http://localhost:8080/",
            changeOrigin: true
        })
    );

    app.use(
        proxy("/news/", {
            target: "http://localhost:8080/",
            security: false,
            changeOrigin: true
        })
    );
    app.use(
        proxy("/oauth/authorize", {
            target: "https://github.com/login/",
            security: false,
            changeOrigin: true
        })
    );

    app.use(
        proxy("/access_token", {
            target: "https://github.com/login/oauth/",
            security: false,
            changeOrigin: true
        })
    );

};