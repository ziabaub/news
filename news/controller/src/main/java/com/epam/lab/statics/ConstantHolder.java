package com.epam.lab.statics;

public class ConstantHolder {

    private ConstantHolder() {
    }

    public static final String USER = "/user/";
    public static final String AUTHOR = "/author/";
    public static final String NEWS = "/news/";
    public static final String TAG = "/tag/";
    public static final String ROLE = "/role/";

    public static final String ADMIN = "ADMIN";
    public static final String USERS = "USER";
    public static final String JOURNALIST = "JOURNALIST";
    public static final String CLIENT = "CLIENT";
    public static final String ARCHIVE = "ARCHIVE";

    public static final String PATTERN_URL = "/**/**";
    public static final String SECRET = "SomeSecretForJWTGeneration";
    public static final int EXPIRATION_TIME = 864_000_000; // 10 days
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String URL_OAUTH2 = "/login/oauth2/code/github";
    public static final String API = "api";
    public static final String USER_ID = "user_id";
    public static final String USER_SECRET = "secret";
    public static final String DELIMITER = ":";
    public static final String HTTP_STATUS_OK = "200 OK";
    public static final String CURL = "curl -H 'Authorization: token %s' https://api.github.com/users/codertocat -I";
    public static final String REDIRECT = "https://github.com/login/oauth/authorize?client_id=%s&redirect_uri=%s&response_type=code";
    public static final String OAUTH2_CALLBACK_PATH = "http://localhost:8080/login/oauth2/code/github";
    public static final String GITHUB_OAUTH_ACCESS_TOKEN_URL = "https://github.com/login/oauth/access_token";
}
