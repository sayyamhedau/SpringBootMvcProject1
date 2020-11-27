package com.app.service;

import org.springframework.data.domain.Page;

import com.app.entity.Employee;

public interface IAdminService {
	Page<Employee> getPageableEmployees(int currantPage, String sortField, String sortDir);
}
