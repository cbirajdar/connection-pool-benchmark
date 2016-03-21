package com.demo.benchmark;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;

@Configuration public class DatasourceConfiguration {

    @Value("${datasource.driver-class-name}") private String dsDriverClassName;

    @Value("${datasource.url}") private String url;

    @Value("${datasource.username}") private String username;

    @Value("${datasource.password:''}") private String password;

    @Value("${datasource.max-active}") private int maxActive;

    @Value("${datasource.initial-size}") private int initialSize;

    @Bean public DataSource dataSource() throws PropertyVetoException {
        return "C3P0".equals(System.getProperty("datasource_type")) ? c3p0Datasource() : tomcatJdbcDatasource();
    }

    private DataSource tomcatJdbcDatasource() {
        org.apache.tomcat.jdbc.pool.DataSource dataSource = new org.apache.tomcat.jdbc.pool.DataSource();
        dataSource.setDriverClassName(dsDriverClassName);
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setMaxActive(maxActive);
        dataSource.setInitialSize(initialSize);
        return dataSource;
    }

    private DataSource c3p0Datasource() throws PropertyVetoException {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setDriverClass(dsDriverClassName);
        dataSource.setJdbcUrl(url);
        dataSource.setUser(username);
        dataSource.setPassword(password);
        dataSource.setMaxPoolSize(maxActive);
        dataSource.setInitialPoolSize(initialSize);
        return dataSource;
    }

}
