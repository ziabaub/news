package com.epam.lab.security;

import com.epam.lab.model.entities.Roles;
import com.epam.lab.model.entities.User;
import com.epam.lab.model.security.UserPrincipal;
import com.google.api.client.auth.oauth2.AuthorizationCodeTokenRequest;
import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.api.client.http.BasicAuthentication;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson.JacksonFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

import static com.epam.lab.statics.ConstantHolder.*;

public class ApiAuthHandler {

    private static final Logger LOG = LoggerFactory.getLogger(ApiAuthHandler.class);
    private static AuthorizationCodeTokenRequest tokenRequest;

    static {
        tokenRequest = new AuthorizationCodeTokenRequest(
                new NetHttpTransport(),
                new JacksonFactory(),
                new GenericUrl(GITHUB_OAUTH_ACCESS_TOKEN_URL), "")
                .setRedirectUri(OAUTH2_CALLBACK_PATH)
                .setRequestInitializer(httpRequest -> httpRequest.getHeaders().setAccept(MediaType.APPLICATION_JSON_VALUE));
    }

    public void login(HttpServletResponse response, String userId, String secret) throws IOException {
        sendRedirectToOAuthProvider(response, userId, secret);
    }

    public UserPrincipal getToken(HttpServletRequest request) throws IOException {
        String oauthCode = request.getQueryString();
        String userId = request.getHeader(USER_ID);
        String secret = request.getHeader(USER_SECRET);
        TokenResponse tokenResponse = performOAuthTokenAuthentication(oauthCode, userId, secret);
        if (tokenResponse == null) {
            return null;
        }
        User user = buildUser(tokenResponse.getAccessToken());
        return new UserPrincipal(user);
    }

    public UserPrincipal findByToken(String token) throws IOException {
        String command = String.format(CURL, token);
        Process process = Runtime.getRuntime().exec(command);
        InputStream inputStream = process.getInputStream();
        String result = new BufferedReader(
                new InputStreamReader(inputStream))
                .lines().collect(Collectors.joining("\n"));
        if (result.contains(HTTP_STATUS_OK)) {
            User user = buildUser(token);
            return new UserPrincipal(user);
        }
        return null;
    }

    private void sendRedirectToOAuthProvider(HttpServletResponse response, String userId, String secret) throws IOException {
        String redirectURL = String.format(REDIRECT, userId, OAUTH2_CALLBACK_PATH);
        response.setHeader(USER_ID, userId);
        response.setHeader(USER_SECRET, secret);
        response.sendRedirect(redirectURL);
    }

    private TokenResponse performOAuthTokenAuthentication(String code, String userId, String secret) throws IOException {
        tokenRequest.setClientAuthentication(new BasicAuthentication(userId, secret));
        tokenRequest.setCode(code.substring(5));
        LOG.debug("Sending token request.");
        return tokenRequest.executeUnparsed().parseAs(TokenResponse.class);
    }

    private User buildUser(String token) {
        User user = new User();
        user.setLogin(API);
        user.setPassword(token);
        user.setRoles(new Roles());
        return user;
    }

}
