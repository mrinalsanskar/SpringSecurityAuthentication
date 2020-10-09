 package com.cg.fms.springjwt.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/*
 * This class has accessing protected resource methods with role based validations.
 *
 * author sanskar.
 * 
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/test")
public class TestController {

	
	/*
	 * /api/test/all provides public access
	 */
	@GetMapping("/all")
	public String allAccess() {
		return "Public Content.";
	}

	
	/*
	 * We used @EnableGlobalMethodSecurity(prePostEnabled = true) for WebSecurityConfig class,
	 * so now we can secure methods in our Apis with @PreAuthorize annotation easily.
	 * This annotation is used for specifying a method access-control expression 
	 * which will be evaluated to decide whether a method invocation is allowed or not.
	 * 
	 * /api/test/customer provides access for users that have ROLE_CUSTOMER
	 * 
	 */
	@GetMapping("/customer")
	@PreAuthorize("hasAuthority('CUSTOMER')")
	public String customerAccess() {
		return "Customer Content only.";
	}

	
	/*
	 * /api/test/admin provides access for users that have ROLE_ADMIN
	 */
	@GetMapping("/admin")
	@PreAuthorize("hasAuthority('ADMIN')")
	public String adminAccess() {
		return "Admin Content only";
	}

}
