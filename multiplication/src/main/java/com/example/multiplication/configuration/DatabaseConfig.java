package com.example.multiplication.configuration;

import com.zaxxer.hikari.HikariDataSource;
import org.apache.commons.dbcp2.ConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;

import org.apache.commons.dbcp2.DriverManagerConnectionFactory;
import org.apache.commons.dbcp2.PoolableConnection;
import org.apache.commons.dbcp2.PoolableConnectionFactory;
import org.apache.commons.dbcp2.PoolingDataSource;
import org.apache.commons.pool2.ObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPool;

import javax.sql.DataSource;

@Configuration
public class DatabaseConfig
{
	@Autowired
	private Environment env;

	@Bean
	@Primary
	public DataSourceProperties dataSourceProperties() {
		return new DataSourceProperties();
	}

//	@Bean(name = "dataSource")
//	public DataSource hikariDataSource(DataSourceProperties properties) {
//		return properties.initializeDataSourceBuilder()
//				.type(HikariDataSource.class)
//				.build();
//	}

	@Bean(name = "dataSource")
	public DataSource dbcpDataSource() {
		String url = env.getProperty("spring.datasource.url");
		String username = env.getProperty("spring.datasource.username");
		String password = env.getProperty("spring.datasource.password");

		ConnectionFactory connectionFactory =
				new DriverManagerConnectionFactory(url, username, password);

		PoolableConnectionFactory poolableConnectionFactory =
				new PoolableConnectionFactory(connectionFactory,null);

		ObjectPool<PoolableConnection> connectionPool =
				new GenericObjectPool<>(poolableConnectionFactory);

		poolableConnectionFactory.setPool(connectionPool);

		PoolingDataSource dataSource =
				new PoolingDataSource(connectionPool);
		return dataSource;
	}
}
