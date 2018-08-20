package com.abaza.test.config;

import com.abaza.test.dao.AuthorBookDao;
import com.abaza.test.dao.AuthorDao;
import com.abaza.test.dao.BookDao;
import com.abaza.test.dao.impl.AuthorBookDaoImpl;
import com.abaza.test.dao.impl.AuthorDaoImpl;
import com.abaza.test.dao.impl.BookDaoImpl;
import org.apache.tomcat.dbcp.dbcp.BasicDataSource;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
public class AppConfig {

    @Bean
    public AuthorDao authorDao() {
        return new AuthorDaoImpl();
    }

    @Bean
    public BookDao bookDao() {
        return new BookDaoImpl();
    }

    @Bean
    public AuthorBookDao authorBookDao() {
        return new AuthorBookDaoImpl();
    }

    @Bean

    public LocalSessionFactoryBean sessionFactory(DataSource dataSource, @Qualifier("hibProp") Properties hibernateProperties) {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);
        sessionFactory.setPackagesToScan(
                "com.abaza.test");
        sessionFactory.setHibernateProperties(hibernateProperties);

        return sessionFactory;
    }

    @Bean
    @Profile("default")
    public DataSource dataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/test");
        dataSource.setUsername("root");
        dataSource.setPassword("root");
        dataSource.setMaxWait(5000);
        dataSource.setInitialSize(5);
        dataSource.setMinIdle(1);

        return dataSource;
    }

    @Bean
    @Profile("test")
    public DataSource dataSourceTest() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl("jdbc:hsqldb:mem:paging");
        dataSource.setDriverClassName("org.hsqldb.jdbcDriver");
        dataSource.setUsername("sa");
        dataSource.setPassword("");

        return dataSource;
    }

    @Bean
    public PlatformTransactionManager hibernateTransactionManager(SessionFactory sessionFactory) {
        HibernateTransactionManager transactionManager
                = new HibernateTransactionManager();
        transactionManager.setSessionFactory(sessionFactory);
        return transactionManager;
    }

    @Bean("hibProp")
    @Profile("default")
    Properties hibernateProperties() {
        Properties hibernateProperties = new Properties();
        hibernateProperties.setProperty(
                "hibernate.hbm2ddl.auto", "update");
        hibernateProperties.setProperty(
                "hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");


        return hibernateProperties;
    }

    @Bean("hibProp")
    @Profile("test")
    Properties hibernatePropertiesTest() {
        Properties hibernateProperties = new Properties();
        hibernateProperties.setProperty(
                "hibernate.hbm2ddl.auto", "create-drop");
        hibernateProperties.setProperty(
                "hibernate.dialect", "org.hibernate.dialect.HSQLDialect");
        hibernateProperties.setProperty(
                "hibernate.cache.provider_class","org.hibernate.cache.HashtableCacheProvider");
        hibernateProperties.setProperty(
                "hibernate.id.new_generator_mappings","true");

        return hibernateProperties;
    }
}
