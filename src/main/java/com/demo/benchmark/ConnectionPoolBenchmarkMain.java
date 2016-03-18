package com.demo.benchmark;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.endpoint.SystemPublicMetrics;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@SpringBootApplication
@ComponentScan(basePackages = "com.demo.benchmark")
public class ConnectionPoolBenchmarkMain {

	public static void main(String[] args) {
		SpringApplication.run(ConnectionPoolBenchmarkMain.class, args);
	}
}


@Component
class ConnectionBenchmarkComponent {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private DataSource dataSource;

    @PostConstruct
    public void init() throws SQLException{
        log.info("**************** Datasource name: {} ****************", dataSource.getConnection().getMetaData());
        int numberOfInserts = Integer.valueOf(System.getProperty("number_of_inserts"));
        singleInsert(numberOfInserts);
        batchInsert(numberOfInserts);
    }

    private void singleInsert(int num) throws SQLException {
        long startTime = System.currentTimeMillis();
        Connection connection = dataSource.getConnection();
        PreparedStatement ps = connection.prepareStatement("CREATE TABLE TEST (COL INTEGER NOT NULL)");
        ps.execute();
        connection.close();
        for (int i = 0; i < num; i++) {
            connection = dataSource.getConnection();
            PreparedStatement insertPs = connection.prepareStatement("INSERT INTO TEST (COL) VALUES (?)");
            insertPs.setInt(1, i);
            insertPs.execute();
            connection.close();
        }
        long endTime = System.currentTimeMillis();
        log.info("**************** SINGLE-INSERT: Total Time to execute insert statements: {} ****************", endTime - startTime);
    }

    private void batchInsert(int num) throws SQLException {
        long startTime = System.currentTimeMillis();
        Connection connection = dataSource.getConnection();
        PreparedStatement insertPs = connection.prepareStatement("INSERT INTO TEST (COL) VALUES (?)");
        for (int i = 0; i < num; i++) {
            insertPs.setInt(1, i);
            insertPs.addBatch();
        }
        insertPs.executeBatch();
        long endTime = System.currentTimeMillis();
        log.info("**************** BATCH-INSERT: Total Time to execute insert statements: {} ****************", endTime - startTime);
    }

}
