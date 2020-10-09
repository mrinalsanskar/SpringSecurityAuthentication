package com.cg.fms.springjwt.security.jwt;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

/*
 * AuthEntryPointJwt class that implements AuthenticationEntryPoint interface.
 * This class will catch authentication error, if any.
 * 
 * author sanskar.
 * 
 */
@Component
public class AuthEntryPointJwt implements AuthenticationEntryPoint {

	private static final Logger logger = LoggerFactory.getLogger(AuthEntryPointJwt.class);

	/*
	 * We override the commence() method here & it gets triggerd anytime unauthenticated User 
	 * requests a secured HTTP resource and an AuthenticationException is thrown.
	 */
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		logger.error("Unauthorized error: {}", authException.getMessage());
		/*
		 * HttpServletResponse.SC_UNAUTHORIZED is the 401 Status code. 
		 * It indicates that the request requires HTTP authentication.
		 */
		response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Error 401: Unauthorized");
	}

}
