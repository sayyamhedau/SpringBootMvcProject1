package com.app.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.app.entity.Employee;
import com.app.repo.IEmployeeRepository;
import com.app.service.IAdminService;

@Service
public class AdminServiceImpl implements IAdminService {
	@Autowired
	private IEmployeeRepository employeeRepo;

	@Override
	public Page<Employee> getPageableEmployees(int currantPage, String sortField, String sortDir) {
		Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() : Sort.by(sortField).descending();
		Pageable page = PageRequest.of(currantPage - 1, 6, sort);
		return employeeRepo.findAll(page);
	}

}
