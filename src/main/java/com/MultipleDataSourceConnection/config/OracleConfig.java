package com.MultipleDataSourceConnection.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement // need to give
@Configuration
@EnableJpaRepositories(basePackages = "com.MultipleDataSourceConnection.orderrepo", transactionManagerRef = "oracleSqlDataSourceTransactionManager")
public class OracleConfig {
	// step 1: get the configuration data from yml

	@ConfigurationProperties("spring.datasource.oracle")
	@Bean

	public DataSourceProperties oracleSqlDataSourceProperties() {
		return new DataSourceProperties();

	}

	// step 2: set the configuration data in the data source

	@Bean
	@Primary
	public DataSource oracleSqlDataSource() {
		// 1
		DataSource dataSource = oracleSqlDataSourceProperties().initializeDataSourceBuilder().build();

		// 2
//		DriverManagerDataSource dataSource = new DriverManagerDataSource();
//		dataSource.setUsername(postgreSqlDataSourceProperties().getUsername());
//		dataSource.setPassword(postgreSqlDataSourceProperties().getPassword());
//		dataSource.setUrl(postgreSqlDataSourceProperties().getUrl());
//		dataSource.setDriverClassName(postgreSqlDataSourceProperties().getDriverClassName());

		return dataSource;
	}
	// step 3: create entity manager factory instance

	@Bean(name = "entityManagerFactory")
	LocalContainerEntityManagerFactoryBean postgresEntityManagerFactory(
			EntityManagerFactoryBuilder entityManagerFactoryBuiler,
			@Qualifier("oracleSqlDataSource") DataSource dataSource) {
		return entityManagerFactoryBuiler.dataSource(dataSource)
				.packages("com.MultipleDataSourceConnection.orderentity").build();
	}

	// step 4: create a transaction manager instance
	@Bean
	PlatformTransactionManager oracleSqlDataSourceTransactionManager(
			@Qualifier("entityManagerFactory") LocalContainerEntityManagerFactoryBean entityManagerFactoryBean) {
		return new JpaTransactionManager(entityManagerFactoryBean.getObject());
	}
}
