package com.app.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "EMPLOYEE_MASTER")
public class Employee {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "EMPLOYEE_ID")
	private Long id;

	@NonNull
	@NotBlank(message = "Enter firstname")
	@Column(name = "FIRSTNAME")
	private String firstname;

	@NonNull
	@NotBlank(message = "Enter lastname")
	@Column(name = "LASTNAME")
	private String lastname;

	@NonNull
	@Email(message = "Enter valid email id")
	@Column(name = "EMAIL", length = 50, unique = true)
	private String email;

	@NonNull
	@NotBlank(message = "Enter currant mobileno")
	@Size(min = 10, max = 10, message = "Enter 10 digit mobileno")
	@Column(name = "MOBILE_NO")
	private String mobileno;

	@NonNull
	@Column(name = "PASSWORD")
	@Size(min = 6, message = "Password must be at least 6 character")
	private String password;

	@Transient
	@Column(name = "REPEAT_PASSWORD")
	private String repeatPassword;

	@NonNull
	@Column(name = "EMPLOYEE_IS_ACTIVE")
	private Boolean isActive;

	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(name = "EMPLOYEE_ROLE", joinColumns = @JoinColumn(name = "EMPLOYEE_ID"), inverseJoinColumns = @JoinColumn(name = "ROLE_ID"))
	private Set<Role> roles;
}
