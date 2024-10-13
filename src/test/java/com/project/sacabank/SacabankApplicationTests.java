package com.project.sacabank;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.project.sacabank.formRegister.repository.FormRegisterRepository;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class SacabankApplicationTests {

	@Autowired
	FormRegisterRepository formRegisterRepository;

	@Test
	void contextLoads() {
	}

	@Test
	void testExistsByPhoneOrEmail() {

		var result = formRegisterRepository.existsByPhoneOrEmail("cc", "contact.vgdat@gmail.com");
		// then
		assertThat(result).isFalse();
	}

}
