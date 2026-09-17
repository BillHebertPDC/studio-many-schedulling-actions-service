package com.example.studio_many_scheduling_actions_service;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = {
		"whatsapp.provider=noop",
		"spring.datasource.url=jdbc:h2:mem:contexttest;DB_CLOSE_DELAY=-1",
		"spring.sql.init.mode=always"
})
class StudioManySchedullingActionsServiceApplicationTests {

	@Test
	void contextLoads() {
	}

}
