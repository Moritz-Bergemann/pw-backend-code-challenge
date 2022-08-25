package com.demo.pwbackendchallenge;

import com.demo.pwbackendchallenge.battery.BatteryController;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PwBackendChallengeApplicationTests {
	@Autowired
	BatteryController batteryController;

	@Test
	public void applicationLoads() {
		assertNotNull(batteryController);
	}

}
