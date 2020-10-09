package com.cg.fms.springjwt.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.cg.fms.springjwt.security.jwt.AuthEntryPointJwt;
import com.cg.fms.springjwt.security.jwt.AuthTokenFilter;
import com.cg.fms.springjwt.security.services.UserDetailsServiceImpl;

/*
 * This class provides all the configurations for web security using spring security.
 * WebSecurityConfigurerAdapter is the crux of our security implementation. 
 * It provides HttpSecurity configurations to configure cors, csrf, session management, rules for protected resources. 
 * @EnableWebSecurity allows Spring to find and automatically apply the class to the global Web Security.
 * @EnableGlobalMethodSecurity provides AOP security on methods. It enables @PreAuthorize, @PostAuthorize.
 * 
 * author sanskar.
 * 
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
		// for @preAuthorized annotation
		prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	UserDetailsServiceImpl userDetailsService;

	@Autowired
	private AuthEntryPointJwt unauthorizedHandler;

	@Bean
	public AuthTokenFilter authenticationJwtTokenFilter() {
		return new AuthTokenFilter();
	}

	/*
	 * Spring Security will load User details to perform authentication & authorization. 
	 * So it has UserDetailsService interface that we need to implement.
	 * The implementation of UserDetailsService will be used for configuring DaoAuthenticationProvider 
	 * by AuthenticationManagerBuilder.userDetailsService() method.
	 * 
	 * AuthenticationManager is something that manages authentication in spring security apps.
	 * 
	 */
	@Override
	public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
		authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}

	/*
	 * AuthenticationManagerBean() method can be used to expose the resulting AuthenticationManager as a Bean.
	 */
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	
	/*
	 * Defining a PasswordEncoder for the DaoAuthenticationProvider, If we don’t specify, it will use plain text.
	 * Return type is an instance of PasswordEncoder.
	 */
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	
	/*
	 * We override the configure(HttpSecurity http) method from WebSecurityConfigurerAdapter interface. 
	 * It tells Spring Security how we configure CORS and CSRF, 
	 * when we want to require all users to be authenticated or not, 
	 * which filter (AuthTokenFilter) and when we want it to work (filter before UsernamePasswordAuthenticationFilter), 
	 * which Exception Handler is chosen (AuthEntryPointJwt).
	 * 
	 * HttpSecurity lets you configure what are the paths & 
	 * what are access restrictions for those paths.
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors().and().csrf().disable()
			.exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
			/*
			 * for spring security to not bother about creating sessions, since we are using jwt
			 */
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
			.authorizeRequests().antMatchers("/api/auth/**").permitAll()
			.antMatchers("/api/test/**").permitAll()
			.anyRequest().authenticated();

		/*
		 * to make sure authenticationJwtTokenFilter() is called
		 * before UsernamePasswordAuthenticationFilter is called.
		 */
		http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
	}
}
