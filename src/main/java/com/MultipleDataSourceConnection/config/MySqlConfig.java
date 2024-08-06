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

@Configuration
@EnableJpaRepositories(basePackages = "com.MultipleDataSourceConnection.productrepo", entityManagerFactoryRef = "mySqlEntityManagerFactoryBeen", transactionManagerRef = "mySqlTransactionManager")
public class MySqlConfig {

	// 1) configure the data source
	// 2) create entity manager object
	// 3) create transaction manager been
	@ConfigurationProperties("spring.datasource.mysql")
	@Bean
	public DataSourceProperties mySqlDataSourceProperties() {
		return new DataSourceProperties();
	}

	// we can set the datasource in two ways
	// 1) my setup in the instance of driverManagerDataSource
	// 2)
	@Bean
	// @Primary
	public DataSource mySqlDataSource() {
		// 1
		DataSource dataSource = mySqlDataSourceProperties().initializeDataSourceBuilder().build();

		// 2
//		DriverManagerDataSource dataSource = new DriverManagerDataSource();
//		dataSource.setUsername(mySqlDataSourceProperties().getUsername());
//		dataSource.setPassword(mySqlDataSourceProperties().getPassword());
//		dataSource.setUrl(mySqlDataSourceProperties().getUrl());
//		dataSource.setDriverClassName(mySqlDataSourceProperties().getDriverClassName());

		return dataSource;
	}

	// creating entity manager

	@Bean
	// (name = "entityManagerFactory") // we need to give bean name as
	// entitymanagerfactory bcoz spring will search in
	// this name or alternative way is
	// =@EnableJpaRepositories(entityManagerFactoryRef =
	// "mySqlEntityManagerFactoryBeen") give in above of the class

	LocalContainerEntityManagerFactoryBean mySqlEntityManagerFactoryBeen(
			EntityManagerFactoryBuilder entityManagerFactoryBuilder,
			@Qualifier("mySqlDataSource") DataSource dataSource) {
		return entityManagerFactoryBuilder.dataSource(dataSource)
				.packages("com.MultipleDataSourceConnection.productEntity").build();
	}

	// creating transaction manager been
	@Bean
	PlatformTransactionManager mySqlTransactionManager(
			@Qualifier("mySqlEntityManagerFactoryBeen") LocalContainerEntityManagerFactoryBean entityManagerFactoryBean) {
		return new JpaTransactionManager(entityManagerFactoryBean.getObject());
	}
}
