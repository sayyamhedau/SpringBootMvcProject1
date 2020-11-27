package com.app.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.app.entity.Employee;
import com.app.entity.Role;
import com.app.exceptions.EmployeeNotFoundException;
import com.app.repo.IEmployeeRepository;
import com.app.repo.IRoleRepository;
import com.app.service.IEmployeeService;

@Service
public class EmployeeServiceImpl implements IEmployeeService, UserDetailsService {

	@Autowired
	private IEmployeeRepository employeeRepo;

	@Autowired
	private IRoleRepository roleRepository;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Override
	public Employee Save(Employee employee) {
		employee.setIsActive(true);
		employee.setPassword(passwordEncoder.encode(employee.getPassword()));
		Role roleList = roleRepository.findByRole("ADMIN");
		employee.setRoles(new HashSet<>(Arrays.asList(roleList)));
		return employeeRepo.save(employee);
	}

	@Override
	public boolean employeeExistsOrNotByEmail(String email) {
		return employeeRepo.findByEmail(email).isPresent();
	}

	@Override
	public boolean employeeExistsOrNotByMobileno(String mobileno) {
		return employeeRepo.findByMobileno(mobileno).isPresent();
	}

	@Override
	public Employee findEmployeeById(Long id) {
		return employeeRepo.findById(id).orElseThrow(EmployeeNotFoundException::new);
	}

	@Override
	public List<Employee> findAllEmployees() {
		return employeeRepo.findAll();
	}

	@Override
	public UserDetails loadUserByUsername(String username) {
		Employee employee = employeeRepo.findByEmail(username).orElseThrow(EmployeeNotFoundException::new);
		List<GrantedAuthority> authorities = getAuthority(employee.getRoles());
		return new User(employee.getEmail(), employee.getPassword(), authorities);
	}

	private List<GrantedAuthority> getAuthority(Set<Role> userRoles) {
		Set<GrantedAuthority> roles = new HashSet<>();
		for (Role role : userRoles) {
			roles.add(new SimpleGrantedAuthority(role.getRole()));
		}
		return new ArrayList<>(roles);
	}

	@Override
	public boolean deleteEmployee(Employee employee) {
		employeeRepo.delete(employee);
		return true;
	}
}
