package com.lti;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@EntityScan("com.lti")
@ComponentScan("com.lti")
public class KeepSafeBankApplication {

	public static void main(String[] args) {
		SpringApplication.run(KeepSafeBankApplication.class, args);
	}

}
