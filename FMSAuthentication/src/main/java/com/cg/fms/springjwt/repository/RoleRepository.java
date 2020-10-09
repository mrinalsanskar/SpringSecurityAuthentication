package com.cg.fms.springjwt.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cg.fms.springjwt.entity.ERole;
import com.cg.fms.springjwt.entity.Role;

/*
 *This class has intefaces that extend Spring Data JPA JpaRepository to interact with Database.
 *This in particular is a repository for persisting and accessing role data.
 *
 * author sanskar.
 * 
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
	Optional<Role> findByName(ERole name);
}
