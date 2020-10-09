package com.cg.fms.springjwt.entity;

import javax.persistence.*;

/*
 * Bean Class for Roles.
 * 
 * author sanskar.
 * 
 */
@Entity
@Table(name = "roles")
public class Role {
	
	/*
	 * @GeneratedValue annotation specifies that a value will be automatically generated for that field.
	 * The IDENTITY strategy also generates an automatic value during commit for every new entity object.(same as auto)
	 * The difference is that a separate identity generator is managed per type hierarchy, so generated values are unique only per type hierarchy.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	
	/*
	 * To map an enum value to and from its database representation in JPA before 2.1. is to use the @Enumerated annotation. 
	 * This way, we can instruct a JPA provider to convert an enum to its ordinal or String value.
	 */
	@Enumerated(EnumType.STRING)
	@Column(length = 20)
	private ERole name;

	public Role() {

	}

	public Role(ERole name) {
		this.name = name;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public ERole getName() {
		return name;
	}

	public void setName(ERole name) {
		this.name = name;
	}
}