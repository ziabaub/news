package com.epam.lab.configuration;

import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import static com.epam.lab.statics.ConstantHolder.PATTERN_URL;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.epam.lab")
public class ControllerConfiguration {

    private static final String CONFIDENTIAL = "CONFIDENTIAL";
    private static final String HTTP = "http";
    private static final int CURR_PORT = 8080;
    private static final int REDIRECT_PORT = 8443;

    @Bean
    public ServletWebServerFactory servletContainer() {
        // Enable SSL Traffic
        TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory() {
            @Override
            protected void postProcessContext(Context context) {
                SecurityConstraint securityConstraint = new SecurityConstraint();
                securityConstraint.setUserConstraint(CONFIDENTIAL);
                SecurityCollection collection = new SecurityCollection();
                collection.addPattern(PATTERN_URL);
                securityConstraint.addCollection(collection);
                context.addConstraint(securityConstraint);
            }
        };

        // Add HTTP to HTTPS redirect
        tomcat.addAdditionalTomcatConnectors(httpToHttpsRedirectConnector());

        return tomcat;
    }

    /*
    We need to redirect from HTTP to HTTPS. Without SSL, this application used
    port 8080. With SSL it will use port 8443. So, any request for 8080 needs to be
    redirected to HTTPS on 8443.
     */
    private Connector httpToHttpsRedirectConnector() {
        Connector connector = new Connector(TomcatServletWebServerFactory.DEFAULT_PROTOCOL);
        connector.setScheme(HTTP);
        connector.setPort(CURR_PORT);
        connector.setSecure(false);
        connector.setRedirectPort(REDIRECT_PORT);
        return connector;
    }

}
