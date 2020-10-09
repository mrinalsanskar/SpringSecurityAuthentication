package com.cg.fms.springjwt.security.services;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.cg.fms.springjwt.entity.User;
import com.cg.fms.springjwt.model.UserModel;
import com.cg.fms.springjwt.repository.UserRepository;

/*
 * UserDetailsService interface has a method to load User by username and returns a UserDetails object 
 * that Spring Security can use for authentication and validation. This class does the same job.
 * 
 * author sanskar.
 * 
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	
	@Autowired
	UserRepository userRepository;

	/*
	 * In the code below, we get full custom User object using UserRepository, 
	 * then we build a UserDetails object using static build() method.
	 */
	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

		return UserDetailsImpl.build(user);
	}
	
	@Transactional
	public UserModel getUserByUserName(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

		UserModel userModel = new UserModel();
		BeanUtils.copyProperties(user, userModel);
		return userModel;
	}

}
