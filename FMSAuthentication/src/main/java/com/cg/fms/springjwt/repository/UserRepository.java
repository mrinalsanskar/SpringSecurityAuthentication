package com.cg.fms.springjwt.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cg.fms.springjwt.entity.User;

/*
 * This class has intefaces that extend Spring Data JPA JpaRepository to interact with Database for user details.
 * 
 * author sanskar.
 * 
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	
	Optional<User> findByUsername(String username);

	Boolean existsByUsername(String username);

	Boolean existsByEmail(String email);
}
