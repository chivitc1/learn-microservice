package com.example.multiplication.configuration;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Configuration
public class DatabaseConfig
{
	@Bean
	@Primary
	@ConfigurationProperties("spring.datasource")
	public DataSourceProperties dataSourceProperties() {
		return new DataSourceProperties();
	}

	@Bean(name = "dataSource")
	@ConfigurationProperties("spring.datasource")
	public DataSource hikariDataSource(DataSourceProperties properties) {
		return properties.initializeDataSourceBuilder()
				.type(HikariDataSource.class)
				.build();
	}
}
