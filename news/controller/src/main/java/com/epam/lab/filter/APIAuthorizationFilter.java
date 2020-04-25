package com.epam.lab.filter;

import com.auth0.jwt.JWT;
import com.epam.lab.model.security.UserPrincipal;
import com.epam.lab.security.ApiAuthHandler;
import com.epam.lab.statics.ConstantHolder;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;
import static com.epam.lab.statics.ConstantHolder.DELIMITER;
import static com.epam.lab.statics.ConstantHolder.URL_OAUTH2;

public class APIAuthorizationFilter extends BasicAuthenticationFilter {


    private ApiAuthHandler apiAuthHandler;

    public APIAuthorizationFilter(AuthenticationManager authenticationManager, ApiAuthHandler apiAuthHandler) {
        super(authenticationManager);
        this.apiAuthHandler = apiAuthHandler;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String uri = request.getRequestURI();
        if (uri.contains(URL_OAUTH2)) {
            successfulAuthentication(response, apiAuthHandler.getToken(request));
            return;
        }
        chain.doFilter(request, response);
    }

    private void successfulAuthentication(HttpServletResponse response, UserPrincipal principal) {
        if(principal == null){
            return;
        }
        String token = JWT.create()
                .withSubject(principal.getUsername() + DELIMITER + principal.getPassword())
                .withExpiresAt(new Date(System.currentTimeMillis() + ConstantHolder.EXPIRATION_TIME))
                .sign(HMAC512(ConstantHolder.SECRET.getBytes()));
        response.addHeader(ConstantHolder.HEADER_STRING, ConstantHolder.TOKEN_PREFIX + token);
    }
}
