package com.app.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.app.entity.Employee;
import com.app.repo.IEmployeeRepository;
import com.app.service.IEmployeeService;

@SpringBootTest
class EmployeeServiceImplTest {

	@MockBean
	private IEmployeeRepository employeeRepository;

	@Autowired
	private IEmployeeService employeeService;

	@Test
	void testSave() {
		Employee employee1 = new Employee("Sayyam", "Hedau", "sayyamhedau01@gmail.com", "8007390103", "1234", true);
		Employee employee2 = new Employee("Divynka", "Tripathi", "divynkatriapthi@gmail.com", "8007290102", "1234",
				true);

		when(employeeRepository.save(employee1)).thenReturn(employee1);

		assertNotEquals(employee2, employeeService.Save(employee1), () -> "Failed to save employee record");
	}

	@Test
	void testEmployeeExistsOrNotByEmail() {
		String email = "sayyamhedau01@gmail.com";

		Employee employee = new Employee();
		employee.setEmail(email);
		employee.setFirstname("Sayyam");
		employee.setLastname("Hedau");

		when(employeeRepository.findByEmail(email)).thenReturn(Optional.ofNullable(employee));

		assertTrue(employeeService.employeeExistsOrNotByEmail(email), () -> "Failed test case");
	}

	@Test
	void testEmployeeExistsOrNotByMobileno() {
		String mobileno = "8007390103";

		Employee employee = new Employee();
		employee.setEmail("sayyamhedau01@gmail.com");
		employee.setFirstname("Sayyam");
		employee.setLastname("Hedau");
		employee.setMobileno("8007390103");

		when(employeeRepository.findByMobileno(mobileno)).thenReturn(Optional.ofNullable(employee));

		assertTrue(employeeService.employeeExistsOrNotByMobileno(mobileno), () -> "Failed test case");
	}

}
