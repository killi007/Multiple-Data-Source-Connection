package com.MultipleDataSourceConnection.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableJpaRepositories(basePackages = "com.MultipleDataSourceConnection.productrepo", entityManagerFactoryRef = "msSqlEntityManagerFactoryBeen", transactionManagerRef = "msSqlTransactionManager")
public class MsSql {

	// step 1: get the configuration data from yml
	@Bean
	@ConfigurationProperties("spring.datasource.msserver")
	public DataSourceProperties msSqlDataSourceProperties() {
		return new DataSourceProperties();
	}

	// step 2: set the configuration data in the data source
	@Bean
	public DataSource msSqlDataSource() {

		DataSource dataSource = msSqlDataSourceProperties().initializeDataSourceBuilder().build();
//or

//		DriverManagerDataSource dataSource = new DriverManagerDataSource();
//		dataSource.setUsername(mySqlDataSourceProperties().getUsername());
//		dataSource.setPassword(mySqlDataSourceProperties().getPassword());
//		dataSource.setUrl(mySqlDataSourceProperties().getUrl());
//		dataSource.setDriverClassName(mySqlDataSourceProperties().getDriverClassName());

		return dataSource;
	}

	//step 3: create entity manager factory instance
	@Bean
	// (name = "entityManagerFactory") // we need to give bean name as
	// entitymanagerfactory bcoz spring will search in
	// this name or alternative way is
	// =@EnableJpaRepositories(entityManagerFactoryRef =
	// "mySqlEntityManagerFactoryBeen") give in above of the class

	LocalContainerEntityManagerFactoryBean msSqlEntityManagerFactoryBeen(
			EntityManagerFactoryBuilder entityManagerFactoryBuilder,
			@Qualifier("msSqlDataSource") DataSource dataSource) {
		return entityManagerFactoryBuilder.dataSource(dataSource)
				.packages("com.MultipleDataSourceConnection.productEntity").build();
	}

	//step 4: create a transaction manager instance 
	@Bean
	PlatformTransactionManager msSqlTransactionManager(
			@Qualifier("msSqlEntityManagerFactoryBeen") LocalContainerEntityManagerFactoryBean entityManagerFactoryBean) {
		return new JpaTransactionManager(entityManagerFactoryBean.getObject());
	}
}
