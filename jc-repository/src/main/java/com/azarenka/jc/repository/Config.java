package com.azarenka.jc.repository;

import liquibase.integration.spring.SpringLiquibase;

import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperFactoryBean;
import org.postgresql.Driver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

import java.sql.SQLException;

import javax.sql.DataSource;

/**
 * Configuration of repository.
 * <p>
 * (c) ant-azarenko@mail.ru 2020
 * </p>
 * Date: 28.08.2020
 *
 * @author Anton Azarenka
 */
@Configuration
public class Config {

    @Value("${spring.datasource.url}")
    private String url;
    @Value("${spring.datasource.username}")
    private String username;
    @Value("${spring.datasource.password}")
    private String password;

    /**
     * Bean of datasource.
     *
     * @return instance od {@link DataSource}
     * @throws SQLException sql exception
     */
    @Bean
    public DataSource dataSource() throws SQLException {
        Driver driver = new Driver();
        DataSource dataSourceBuilder = new SimpleDriverDataSource(driver, url, username, password);
        return dataSourceBuilder;
    }

    /**
     * Bean of liquibase.
     *
     * @return instance od {@link SpringLiquibase}
     * @throws SQLException sql exception
     */
    @Bean
    public SpringLiquibase getLiquibase() throws SQLException {
        SpringLiquibase springLiquibase = new SpringLiquibase();
        springLiquibase.setDataSource(dataSource());
        springLiquibase.setChangeLog("classpath:liquibase/com.azarenka.jc/changelog.xml");
        return springLiquibase;
    }

    @Bean
    public SqlSessionFactoryBean sqlSessionFactory(ApplicationContext applicationContext) throws Exception {
        SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource());
        sessionFactory.setConfigLocation(applicationContext.getResource("classpath:mybatis.xml"));
        sessionFactory.setMapperLocations(applicationContext.getResources("classpath:com.azarenka.mapper/*.xml"));
        sessionFactory.setTypeAliasesPackage("com.azarenka.domain");
        sessionFactory.afterPropertiesSet();
        return sessionFactory;
    }

    @Bean
    public MapperFactoryBean<IUserRepository> userRepository(ApplicationContext applicationContext) throws Exception {
        MapperFactoryBean<IUserRepository> repository = new MapperFactoryBean<>();
        repository.setMapperInterface(IUserRepository.class);
        repository.setSqlSessionFactory(sqlSessionFactory(applicationContext).getObject());
        return repository;
    }

    @Bean
    public MapperFactoryBean<IUsersRoleMapRepository> userRoleMapRepository(
        ApplicationContext applicationContext) throws Exception {
        MapperFactoryBean<IUsersRoleMapRepository> repository = new MapperFactoryBean<>();
        repository.setMapperInterface(IUsersRoleMapRepository.class);
        repository.setSqlSessionFactory(sqlSessionFactory(applicationContext).getObject());
        return repository;
    }
/*
    @Bean
    public MapperFactoryBean<DayRepository> dayRepository(ApplicationContext applicationContext) throws Exception {
        MapperFactoryBean<DayRepository> repository = new MapperFactoryBean<>();
        repository.setMapperInterface(DayRepository.class);
        repository.setSqlSessionFactory(sqlSessionFactory(applicationContext).getObject());
        return repository;
    }

    @Bean
    public MapperFactoryBean<FoodRepository> foodRepository(ApplicationContext applicationContext) throws Exception {
        MapperFactoryBean<FoodRepository> repository = new MapperFactoryBean<>();
        repository.setMapperInterface(FoodRepository.class);
        repository.setSqlSessionFactory(sqlSessionFactory(applicationContext).getObject());
        return repository;
    }

    @Bean
    public MapperFactoryBean<MealRepository> mealRepository(ApplicationContext applicationContext) throws Exception {
        MapperFactoryBean<MealRepository> repository = new MapperFactoryBean<>();
        repository.setMapperInterface(MealRepository.class);
        repository.setSqlSessionFactory(sqlSessionFactory(applicationContext).getObject());
        return repository;
    }

    @Bean
    public MapperFactoryBean<MenuRepository> menuRepository(ApplicationContext applicationContext) throws Exception {
        MapperFactoryBean<MenuRepository> uR = new MapperFactoryBean<>();
        uR.setMapperInterface(MenuRepository.class);
        uR.setSqlSessionFactory(sqlSessionFactory(applicationContext).getObject());
        return uR;
    }

    }

    @Bean
    public MapperFactoryBean<BookerRepository> bookerRepository(
    ApplicationContext applicationContext) throws Exception {
        MapperFactoryBean<BookerRepository> repository = new MapperFactoryBean<>();
        repository.setMapperInterface(BookerRepository.class);
        repository.setSqlSessionFactory(sqlSessionFactory(applicationContext).getObject());
        return repository;
    }*/
}
