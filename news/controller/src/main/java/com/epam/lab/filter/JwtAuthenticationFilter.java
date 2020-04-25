package com.epam.lab.filter;

import com.auth0.jwt.JWT;
import com.epam.lab.exception.MissingHeaderInfoException;
import com.epam.lab.model.security.LoginViewModel;
import com.epam.lab.model.security.UserPrincipal;
import com.epam.lab.security.ApiAuthHandler;
import com.epam.lab.statics.ConstantHolder;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.bind.annotation.CrossOrigin;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;
import static com.epam.lab.statics.ConstantHolder.USER_ID;
import static com.epam.lab.statics.ConstantHolder.USER_SECRET;

//@CrossOrigin(origins = "http://localhost:3000")
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private AuthenticationManager authenticationManager;
    private ApiAuthHandler apiAuthHandler;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager, ApiAuthHandler apiAuthHandler) {
        this.apiAuthHandler = apiAuthHandler;
        this.authenticationManager = authenticationManager;
    }

    /* Trigger when we issue POST request to /login
    We also need to pass in {"username":"ziad", "password":"1234"} in the request body
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
        String userId = request.getHeader(USER_ID);
        String secret = request.getHeader(USER_SECRET);
        try {
            if (userId == null || secret == null) {
                return attemptByUserNameAndPassword(request);
            }
            apiAuthHandler.login(response, userId, secret);
            return null;
        } catch (IOException e) {
            throw new MissingHeaderInfoException("exception during attempting authentication", e);
        }
    }

    private Authentication attemptByUserNameAndPassword(HttpServletRequest request) throws IOException {
        UsernamePasswordAuthenticationToken authenticationToken;
        LoginViewModel credentials = new ObjectMapper().readValue(request.getInputStream(), LoginViewModel.class);
        authenticationToken = new UsernamePasswordAuthenticationToken(
                credentials.getUsername(),
                credentials.getPassword(),
                new ArrayList<>());
        return authenticationManager.authenticate(authenticationToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) {
        UserPrincipal principal = (UserPrincipal) authResult.getPrincipal();
        String token = JWT.create()
                .withSubject(principal.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + ConstantHolder.EXPIRATION_TIME))
                .sign(HMAC512(ConstantHolder.SECRET.getBytes()));
        response.addHeader(ConstantHolder.HEADER_STRING, ConstantHolder.TOKEN_PREFIX + token);
    }
}
