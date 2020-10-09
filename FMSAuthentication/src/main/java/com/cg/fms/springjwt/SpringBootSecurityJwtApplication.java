package com.cg.fms.springjwt;

import org.springframework.boot.SpringApplication;

import org.springframework.boot.autoconfigure.SpringBootApplication;

/*
 * Code below integrates your main() with Spring Boot. 
 * It is created by default when you create a new spring boot project.
 * @SpringBootApplication annotation is used to mark a configuration class 
 * that declares one or more @Bean methods and also triggers auto-configuration and component scanning. 
 * It's same as declaring a class with @Configuration, @EnableAutoConfiguration and @ComponentScan annotations.
 * 
 * author sanskar.
 * 
 */
@SpringBootApplication
public class SpringBootSecurityJwtApplication {

	public static void main(String[] args) {
		/*
		 *  We run Application.run() because this method starts whole Spring Framework. 
		 */
		SpringApplication.run(SpringBootSecurityJwtApplication.class, args);
	}

}
