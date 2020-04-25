package com.epam.lab.configuration;

import org.springframework.context.annotation.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;


@Configuration
@ComponentScan(basePackages = "com.epam.lab")
@Profile("jdbc")
public class TestConfiguration {
    private static final String SCRIPT_CREATE = "db.migration/V1.0__createTable.sql";
    private static final String SCRIPT_INSERTION = "V1.2__insert_test.sql";

    @Bean
    public DataSource dataSrc() {
        return new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .addScript(SCRIPT_CREATE)
                .addScript(SCRIPT_INSERTION)
                .build();
    }

    @Bean
    public NamedParameterJdbcTemplate jdbcTemplate(DataSource dataSource) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        return new NamedParameterJdbcTemplate(jdbcTemplate);
    }

}
