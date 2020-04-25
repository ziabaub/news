package com.epam.lab.configuration;

import com.epam.lab.filter.APIAuthorizationFilter;
import com.epam.lab.filter.JwtAuthenticationFilter;
import com.epam.lab.filter.JwtAuthorizationFilter;
import com.epam.lab.model.entities.User;
import com.epam.lab.repository.Repository;
import com.epam.lab.security.ApiAuthHandler;
import com.epam.lab.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;

import static com.epam.lab.statics.ConstantHolder.*;

@Configuration
@EnableWebSecurity
@ComponentScan(basePackages = "com.epam.lab")
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private UserService userService;
    private Repository<User> userRepository;
    private PasswordEncoder passwordEncoder;

    public SecurityConfiguration(UserService userService, Repository<User> userRepository, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(authenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilter(new APIAuthorizationFilter(authenticationManager(), apiLoginAuth()))
                .addFilter(new JwtAuthenticationFilter(authenticationManager(), apiLoginAuth()))
                .addFilter(new JwtAuthorizationFilter(authenticationManager(), this.userRepository, apiLoginAuth()))
                .authorizeRequests()
                .antMatchers(USER).permitAll()
                .antMatchers(HttpMethod.GET, PATTERN_URL).permitAll()
                .antMatchers(HttpMethod.PUT, PATTERN_URL).hasAnyRole(USERS,ARCHIVE,JOURNALIST,CLIENT,ADMIN)
                .antMatchers(HttpMethod.POST, PATTERN_URL).hasAnyRole(USERS,ARCHIVE,JOURNALIST,CLIENT,ADMIN)
                .antMatchers(HttpMethod.DELETE,PATTERN_URL).hasRole(ADMIN)
                .anyRequest().authenticated();
    }

    @Bean
    ApiAuthHandler apiLoginAuth() {
        return new ApiAuthHandler();
    }

    @Bean
    DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
        daoAuthenticationProvider.setUserDetailsService(this.userService);

        return daoAuthenticationProvider;
    }


}
