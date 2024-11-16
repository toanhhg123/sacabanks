package com.project.sacabank;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.project.sacabank.formRegister.repository.FormRegisterRepository;
import com.project.sacabank.web.home.HomeService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SacabankApplicationTests {

	@Autowired
	FormRegisterRepository formRegisterRepository;

	@Autowired
	HomeService homeService;

	@Test
	void contextLoads() {
	}

	@Test
	void getCategoryFilter() {

		var result = homeService.getCategoryFilter();
		// then
		assertNotEquals(20, result.size());
	}

}
