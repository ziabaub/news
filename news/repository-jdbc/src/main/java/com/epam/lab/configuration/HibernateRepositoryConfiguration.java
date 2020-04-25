package com.epam.lab.configuration;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.*;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;


@Configuration
@ComponentScan("com.epam.lab")
@PropertySource("classpath:hibernate.db.properties")
@EnableTransactionManagement
@Profile(value = "hibernate")
public class HibernateRepositoryConfiguration {


    private static final String PATH = "hibernate.db.properties";

    @Bean
    public LocalSessionFactoryBean getSessionFactory() throws IOException {
        LocalSessionFactoryBean factoryBean = new LocalSessionFactoryBean();
        factoryBean.setHibernateProperties(getProperties());
        factoryBean.setPackagesToScan("com.epam.lab");

        return factoryBean;
    }

    @Bean
    public HibernateTransactionManager getTransactionMgr() throws IOException {
        HibernateTransactionManager transactionManager = new HibernateTransactionManager();
        transactionManager.setSessionFactory(getSessionFactory().getObject());
        return transactionManager;
    }

    @Bean
    public static Properties getProperties() throws IOException {
        Properties properties = new Properties();
        java.net.URL propertiesURL = Thread.currentThread()
                .getContextClassLoader()
                .getResource(StringUtils.defaultString(PATH));
        if (propertiesURL == null) {
            return properties;
        }
        try (FileInputStream inputStream = new FileInputStream(propertiesURL.getFile())) {
            properties.load(inputStream);
        }
        return properties;
    }

}
