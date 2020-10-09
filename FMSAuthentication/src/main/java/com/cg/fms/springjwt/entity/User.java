package com.cg.fms.springjwt.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/*
 * This class creates the Entity class and will be used while performing database operations.
 * Works on DAO layer.
 * DAO(Data Access Object) encapsulates the logic for retrieving, saving and updating data in your data storage (a database, a file-system, whatever).
 * 
 * author sanskar.
 * 
 * The @UniqueConstraint annotation is for annotating multiple unique keys at the table level only.
 */
@Entity
@Table(	name = "users", 
		uniqueConstraints = { 
			@UniqueConstraint(columnNames = "username"),
			@UniqueConstraint(columnNames = "email") 
		})
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank
	@Size(max = 10)
	private String firstName;
	
	@NotBlank
	@Size(max = 10)
	private String lastName;

	@NotBlank
	@Size(max = 50)
	@Email
	private String email;
	
	@NotBlank
	@Size(max = 10)
	private String mobile;
	
	@NotBlank
	@Size(min = 3, max = 20)
	private String username;

	@NotBlank
	@Size(min = 6, max = 90)
	private String password;

	/*
	 * Many-To-Many mapping represents a collection-valued association where any number of entities can be associated with a collection of other entities. 
	 * In relational database any number of rows of one entity can be referred to any number of rows of another entity.
	 * 
	 * A join table is typically used in the mapping of many-to-many and unidirectional one-to-many associations. 
	 * It may also be used to map bidirectional many-to-one/one-to-many associations, 
	 * unidirectional many-to-one relationships, and one-to-one associations (both bidirectional and unidirectional).
	 * 
	 * joinColumns refers to the foreign key columns of the join table which reference 
	 * the primary table of the entity owning the association. (I.e. the owning side of the association).
     *
	 * InverseJoinColumns refers to the foreign key columns of the join table which reference 
	 * the primary table of the entity that does not own the association. (I.e. the inverse side of the association
	 */
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(	name = "user_roles", 
				joinColumns = @JoinColumn(name = "user_id"), 
				inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles = new HashSet<>();

	
	/*
	 * An empty constructor is needed to create a new instance via reflection by your persistence framework. 
	 * If you don't provide any additional constructors with arguments for the class, 
	 * you don't need to provide an empty constructor because you get one per default.
	 * 
	 */
	public User() {
	}

	/*
	 * A Java constructor is special method that is called when an object is instantiated. 
	 * When you use the new keyword, it's purpose is to initializes the newly created object before it is used. 
	 */
	public User(String firstName, String lastName, String email, String mobile, String username,  String password) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.mobile = mobile;
		this.username = username;
		this.password = password;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
}
