package org.hillel.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.hibernate.dialect.PostgreSQL10Dialect;
import org.postgresql.ds.PGSimpleDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Objects;
import java.util.Properties;

@Configuration
@PropertySource("classpath:/config/database.properties")
@ComponentScan({"org.hillel.persistence", "org.hillel.service", "org.hillel.controller.converter"})
@EnableJpaRepositories(entityManagerFactoryRef = "emf", basePackages = {"org.hillel.persistence.jpa.repository"})
@EnableTransactionManagement
public class RootConfig {

    @Bean
    public DataSource dataSource(
            @Value("${database.password}") String password,
            @Value("${database.username}") String username,
            @Value("${database.url}") String url,
            @Value("${database.name}") String databaseName,
            @Value("${database.minimumIdle}") int minIdle,
            @Value("${database.maxPoolSize}") int poolSize
    ) {
        HikariConfig config = new HikariConfig();
        config.setPassword(password);
        config.setUsername(username);
        config.setJdbcUrl(url);
        config.addDataSourceProperty("databaseName", databaseName);
        config.setDataSourceClassName(PGSimpleDataSource.class.getName());
        config.setMinimumIdle(minIdle);
        config.setMaximumPoolSize(poolSize);
        return new HikariDataSource(config);
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean emf(
            DataSource dataSource,
            @Value("${hibernate.hbm2ddl}") String hbm2ddl,
            @Value("${hibernate.show_sql}") String showSql,
            @Value("${hibernate.query.timeout}") int timeout
    ) {
        LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
        emf.setDataSource(dataSource);
        emf.setPackagesToScan("org.hillel.persistence.entity");
        emf.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        Properties properties = new Properties();
        properties.put("hibernate.dialect", PostgreSQL10Dialect.class.getName());
        properties.put("hibernate.hbm2ddl.auto", hbm2ddl);
        properties.put("hibernate.show_sql", showSql);
        properties.put("javax.persistence.query.timeout", timeout);
        emf.setJpaProperties(properties);
        return emf;
    }

    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory, DataSource dataSource) {
        JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();
        jpaTransactionManager.setEntityManagerFactory(entityManagerFactory);
        jpaTransactionManager.setDataSource(dataSource);
        return jpaTransactionManager;
    }

}
