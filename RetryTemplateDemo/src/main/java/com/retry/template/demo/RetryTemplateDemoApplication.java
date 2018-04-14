package com.retry.template.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.retry.template.demo")
public class RetryTemplateDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(RetryTemplateDemoApplication.class, args);
	}
}
