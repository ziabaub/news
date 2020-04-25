package com.epam.lab.configuration;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

/**
 * Java configuration contains 4 spring beans
 * <p>
 * 1-DataSource
 * 2-JdbcTemplate
 * 3-PlatformTransactionManager
 * 4-KeyHolder
 * </p>
 */

@Configuration
@ComponentScan(basePackages = "com.epam.lab")
@EnableTransactionManagement
@PropertySource("classpath:jdbc.db.properties")
@Profile(value = "jdbc")
public class JDBCRepositoryConfiguration {

    private static final String DATA_PROPERTIES = "/jdbc.db.properties";

    @Bean
    public DataSource dataSource() {
        HikariConfig config = new HikariConfig(DATA_PROPERTIES);
        return new HikariDataSource(config);
    }

    @Bean
    public NamedParameterJdbcTemplate jdbcTemp() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource());
        return new NamedParameterJdbcTemplate(jdbcTemplate);
    }

    @Bean
    public PlatformTransactionManager platformTransactionManager() {
        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager();
        transactionManager.setDataSource(dataSource());
        return transactionManager;
    }
}
