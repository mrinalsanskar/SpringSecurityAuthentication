package com.cg.fms.springjwt.security.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.cg.fms.springjwt.security.services.UserDetailsServiceImpl;

/*
 * This class defines a filter that executes once per request. 
 * So we create AuthTokenFilter class that extends OncePerRequestFilter and overrides doFilterInternal() method.
 * OncePerRequestFilter makes a single execution for each request to our API. It provides a doFilterInternal() method 
 * that we will implement parsing & validating JWT, 
 * loading User details (using UserDetailsService) & checking Authorizaion (using UsernamePasswordAuthenticationToken).
 */
public class AuthTokenFilter extends OncePerRequestFilter {
	@Autowired
	private JwtUtils jwtUtils;

	@Autowired
	private UserDetailsServiceImpl userDetailsService;

	private static final Logger logger = LoggerFactory.getLogger(AuthTokenFilter.class);
	
	/*
	 * What we do inside doFilterInternal():
	 * – get JWT from the Authorization header (by removing Bearer prefix)
	 * – if the request has JWT, validate it, parse username from it
	 * – from username, get UserDetails to create an Authentication object
	 * – set the current UserDetails in SecurityContext using setAuthentication(authentication) method.
	 */
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		try {
			System.out.println("API PATH : "+ request.getRequestURL().toString());
			String jwt = parseJwt(request);
			if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
				/*
				 * Extract user information
				 */
				String username = jwtUtils.getUserNameFromJwtToken(jwt);

				UserDetails userDetails = userDetailsService.loadUserByUsername(username);
				/*
				 *Create AuthenticationToken
				 */
				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities());
				System.out.println("User Authority : "+userDetails.getAuthorities());
				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				
				/*
				 * SecurityContextHolder is the most fundamental object where we store details
				 * of the present security context of the application (includes details of the principal).
				 */
				SecurityContextHolder.getContext().setAuthentication(authentication);
			}
		} catch (Exception e) {
			logger.error("Cannot set user authentication: {}", e);
		}

		filterChain.doFilter(request, response);
	}

	private String parseJwt(HttpServletRequest request) {
		String headerAuth = request.getHeader("Authorization");

		if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
			return headerAuth.substring(7, headerAuth.length());
		}

		return null;
	}
}
