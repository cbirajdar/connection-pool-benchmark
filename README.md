# connection-pool-benchmark
Running simple benchmark for Tomcat JDBC vs C3P0 connection pools

## How to run the benchmark

- ```git clone git@github.com:cbirajdar/connection-pool-benchmark.git```
- Datasource Type: Tomcat JDBC, number of inserts: 1000
   -    ```gradle bootRun -PjvmArgs="-Dnumber_of_inserts=1000"```
-  Datasource Type: C3P0, number of inserts: 100000
   -    ```gradle bootRun -PjvmArgs="-Ddatasource_type=C3P0 -Dnumber_of_inserts=100000"```
