package com.cg.fms.springjwt.controllers;

import java.util.HashSet;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cg.fms.springjwt.entity.ERole;
import com.cg.fms.springjwt.entity.Role;
import com.cg.fms.springjwt.entity.User;
import com.cg.fms.springjwt.payload.request.LoginRequest;
import com.cg.fms.springjwt.payload.request.SignupRequest;
import com.cg.fms.springjwt.payload.response.JwtResponse;
import com.cg.fms.springjwt.payload.response.MessageResponse;
import com.cg.fms.springjwt.repository.RoleRepository;
import com.cg.fms.springjwt.repository.UserRepository;
import com.cg.fms.springjwt.security.jwt.JwtUtils;
import com.cg.fms.springjwt.security.services.UserDetailsImpl;

/*
 * Controller receives and handles request after it was filtered by OncePerRequestFilter.
 * AuthController handles signup/login requests.
 * 
 * Spring 4.2 has introduced @CrossOrigin annotation to handle Cross-Origin-Resource-Sharing (CORS). 
 * This annotation is used at class level as well as method level in RESTful Web service controller. 
 * 
 * Access-Control-Allow-Origin: This header specifies the URI that can be accessed by resource. 
 * If this header is not specified then by default all origins are allowed. 
 *If the value (*) is specified then all origins are allowed in the same way as default.
 *
 * Access-Control-Max-Age: This header defines the maximum age in seconds for cache to be alive for a pre-flight request. 
 * Once the maximum age is over then a pre-flight request will be sent again to cross-origin.
 * 
 * @RestController annotation was introduced in Spring 4.0 to simplify the creation of RESTful web services. 
 * It's a convenience annotation that combines @Controller and @ResponseBody 
 * – which eliminates the need to annotate every request handling method of the controller class with the @ResponseBody annotation
 * 
 * @RequestMapping here maps HTTP requests to handler methods of MVC and REST controllers.
 * 
 * author sanskar.
 * 
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

	/*
	 * @Autowired enables you to inject the object dependency implicitly & internally uses setter or constructor injection.
	 *  Autowiring can't be used to inject primitive and string values. It works with reference only.
	 *  For eg. here Spring is taking care of creating of the objects and 
	 *  whenever you want to use it you don't need to create it you can just say:
	 */
	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtUtils jwtUtils;
	

	/*
	 * /api/auth/signin :
	 * 1. authenticate { username, password }
	 * 2. update SecurityContext using Authentication object
	 * 3. generate JWT
	 * 4. get UserDetails from Authentication object
	 * 5. response contains JWT and UserDetails data
	 */
	@PostMapping("/login")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

		/*
		 * UsernamePasswordAuthenticationToken gets {username, password} from login Request, 
		 * AuthenticationManager will use it to authenticate a login account.
		 * AuthenticationManager has a DaoAuthenticationProvider (with help of UserDetailsService & PasswordEncoder) 
		 * to validate UsernamePasswordAuthenticationToken object. If successful, 
		 * AuthenticationManager returns a fully populated Authentication object (including granted authorities).
		 */
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

		/*
		 * The SecurityContext is used to store the details of the currently authenticated user, also known as a principle. 
		 * So, if you have to get the username or any other user details, you need to get this SecurityContext first.
		 * The SecurityContextHolder is a helper class that provides access to the security context. 
		 *Everytime you want to get UserDetails, just use SecurityContext like this:
		 *UserDetails userDetails =(UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		 * 
		 */
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);

		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		
		List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
				.collect(Collectors.toList());

		return ResponseEntity.ok(
				new JwtResponse(jwt, userDetails.getId(), userDetails.getUsername(), userDetails.getEmail(), roles));
	}

	
	/*
	 * /api/auth/register :
	 * 1. It checks existing username/email
	 * 2. It creates new User (here every user that registers is a CUSTOMER & we can make him admin by changing roles from db)
	 * 3. It saves User to database using UserRepository.
	 */
	@PostMapping("/register")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
		if (userRepository.existsByUsername(signUpRequest.getUsername())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
		}

		if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
		}

		/*
		 *  Create new user's account
		 */
		User user = new User(signUpRequest.getFirstName(), signUpRequest.getLastName(), signUpRequest.getEmail(),
				signUpRequest.getMobile(), signUpRequest.getUsername(), encoder.encode(signUpRequest.getPassword()));

		Set<Role> roles = new HashSet<>();

		Role userRole = roleRepository.findByName(ERole.CUSTOMER)
				.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
		roles.add(userRole);

		user.setRoles(roles);
		userRepository.save(user);

		return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
	}
}
