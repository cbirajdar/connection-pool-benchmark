package com.demo.benchmark;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ConnectionPoolBenchmarkMain.class)
public class ConnectionPoolBenchmarkMainTests {

	@Test
	public void contextLoads() {
	}

}
