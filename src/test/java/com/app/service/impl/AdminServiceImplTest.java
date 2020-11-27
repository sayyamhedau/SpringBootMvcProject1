package com.app.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.app.entity.Employee;
import com.app.repo.IEmployeeRepository;
import com.app.service.IAdminService;

@SpringBootTest
class AdminServiceImplTest {
	@MockBean
	private IEmployeeRepository employeeRepository;

	@Autowired
	private IAdminService adminService;

	@Tag("Pagination&Sorting")
	@Test
	void testGetPageableEmployees() {
		Employee employee1 = new Employee();
		employee1.setId(1001L);
		employee1.setFirstname("Sayyam");
		employee1.setLastname("Hedau");

		Employee employee2 = new Employee();
		employee2.setId(1002L);
		employee2.setFirstname("Divynka");
		employee2.setLastname("Tripathi");

		List<Employee> employeesList = Stream.of(employee1, employee2).collect(Collectors.toList());

		Pageable page = PageRequest.of(1, 2, Sort.by("id").ascending());

		when(employeeRepository.findAll(page)).thenReturn(new PageImpl<>(employeesList));

		assertEquals(3, adminService.getPageableEmployees(1, "id", "asc").getContent().size());
	}

}
