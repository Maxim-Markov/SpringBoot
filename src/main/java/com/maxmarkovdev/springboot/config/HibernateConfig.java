package com.maxmarkovdev.springboot.config;

import com.maxmarkovdev.springboot.model.Role;
import com.maxmarkovdev.springboot.model.User;
import com.maxmarkovdev.springboot.service.RoleService;
import com.maxmarkovdev.springboot.service.UserService;
import org.apache.tomcat.dbcp.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.util.Properties;


@Configuration
@EnableTransactionManagement
public class HibernateConfig {

    @Value("${spring.driver-class-name}")
    String driver;
    @Value("${spring.url}")
    String url;
    @Value("${spring.username}")
    String username;
    @Value("${spring.password}")
    String password;

    @Value("${spring.show-sql}")
    private String SHOW_SQL;

    @Value("${spring.ddl-auto}")
    private String HBM2DDL_AUTO;

    @Bean
    public DataSource getDataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(driver);
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        return dataSource;
    }

    @Bean
    public LocalSessionFactoryBean getSessionFactory() {
        LocalSessionFactoryBean factoryBean = new LocalSessionFactoryBean();
        factoryBean.setDataSource(getDataSource());
        Properties hibernateProperties = new Properties();
//        hibernateProperties.put("hibernate.dialect", DIALECT);
        hibernateProperties.put("hibernate.show_sql", SHOW_SQL);
        hibernateProperties.put("hibernate.hbm2ddl.auto", HBM2DDL_AUTO);
        factoryBean.setHibernateProperties(hibernateProperties);
        factoryBean.setAnnotatedClasses(User.class, Role.class);
        return factoryBean;
    }

    @Bean
    public HibernateTransactionManager getTransactionManager() {
        HibernateTransactionManager transactionManager = new HibernateTransactionManager();
        transactionManager.setSessionFactory(getSessionFactory().getObject());
        return transactionManager;
    }

    UserService service;
    RoleService roleService;

    @Autowired
    public void setService(UserService service, RoleService roleService) {
        this.service = service;
        this.roleService = roleService;
    }

    @PostConstruct
    public void createData() {
        Role role1 = new Role("ROLE_ADMIN");
        Role role2 = new Role("ROLE_USER");
        Role role3 = new Role("ROLE_UNDEFINED");
        roleService.createRole(role1);
        roleService.createRole(role2);
        roleService.createRole(role3);

        User user1 = new User("ADMIN", "sws", (byte) 36, "dg@xsjb.com", "admin");
        User user2 = new User("test", "sws", (byte) 33, "dg@xsjb.com", "test");
        User user3 = new User("tr", "sws", (byte) 39, "dg@xsjb.com", "admin");
        user1.addRole(role1);
        user2.addRole(role2);
        user2.addRole(role3);
        user3.addRole(role1);
        service.createUser(user1);
        service.createUser(user2);
        service.createUser(user3);
    }

}