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
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement // need to give 
@Configuration
@EnableJpaRepositories(
		basePackages = "com.MultipleDataSourceConnection.orderrepo",
		transactionManagerRef = "postgresTransactionManager")
public class PostgresConfig {

	@ConfigurationProperties("spring.datasource.postgresql")
	@Bean

	public DataSourceProperties postgreSqlDataSourceProperties() {
		DataSourceProperties a = new DataSourceProperties();
		System.err.println(a.toString());
		return a;
	}

	// we can set the datasource in two ways
	// 1) my setup in the instance of driverManagerDataSource
	// 2)
	@Bean
	@Primary
	public DataSource postgreSqlDataSource() {
		// 1
		DataSource dataSource = postgreSqlDataSourceProperties().initializeDataSourceBuilder().build();

		// 2
//		DriverManagerDataSource dataSource = new DriverManagerDataSource();
//		dataSource.setUsername(postgreSqlDataSourceProperties().getUsername());
//		dataSource.setPassword(postgreSqlDataSourceProperties().getPassword());
//		dataSource.setUrl(postgreSqlDataSourceProperties().getUrl());
//		dataSource.setDriverClassName(postgreSqlDataSourceProperties().getDriverClassName());

		return dataSource;
	}

	// create bean for entity manager
	@Bean(name = "entityManagerFactory")
	LocalContainerEntityManagerFactoryBean postgresEntityManagerFactory(
			EntityManagerFactoryBuilder entityManagerFactoryBuiler,
			@Qualifier("postgreSqlDataSource") DataSource dataSource) {
		return entityManagerFactoryBuiler.dataSource(dataSource).packages("com.MultipleDataSourceConnection.orderentity")
				.build();
	}
	// create transaction manager
	@Bean
	PlatformTransactionManager postgresTransactionManager(@Qualifier("entityManagerFactory")
			LocalContainerEntityManagerFactoryBean entityManagerFactoryBean) {
		return new JpaTransactionManager(entityManagerFactoryBean.getObject());
	}
}
