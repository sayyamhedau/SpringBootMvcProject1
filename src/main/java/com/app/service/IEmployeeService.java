package com.app.service;

import java.util.List;

import com.app.entity.Employee;

public interface IEmployeeService {

	Employee findEmployeeById(Long id);

	Employee Save(Employee employee);

	boolean employeeExistsOrNotByEmail(String email);

	boolean employeeExistsOrNotByMobileno(String mobileno);

	List<Employee> findAllEmployees();
	
	boolean deleteEmployee(Employee employee);

}
