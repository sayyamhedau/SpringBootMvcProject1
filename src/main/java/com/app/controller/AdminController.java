package com.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.app.entity.Employee;
import com.app.service.IAdminService;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {
	@Autowired
	private IAdminService adminService;

	@GetMapping("/dashboard")
	public String viewAdminDashboard() {
		return "AdminDashboardPage";
	}

	@GetMapping(value = "/viewEmployees")
	public String viewEmployeeList(Model model) {
		int currrantPage = 1;
		return viewPageByEmployee(model, currrantPage, "id", "asc");
	}

	@GetMapping(value = "/page/{pageNumber}")

	public String viewPageByEmployee(Model model, @PathVariable("pageNumber") int pageNumber,
			@RequestParam("sortField") String sortField, @RequestParam("sortDir") String sortDir) {

		Page<Employee> employeePage = adminService.getPageableEmployees(pageNumber, sortField, sortDir);
		int totalRecords = employeePage.getNumberOfElements();
		int totalPages = employeePage.getTotalPages();

		List<Employee> employeeList = employeePage.getContent();

		model.addAttribute("currantPage", pageNumber);
		model.addAttribute("totalRecords", totalRecords);
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("employeeList", employeeList);

		model.addAttribute("sortField", sortField);
		model.addAttribute("sortDir", sortDir);
		model.addAttribute("reverseSortDir", sortDir.equalsIgnoreCase("asc") ? "desc" : "asc");

		return "ManageEmployees";
	}
}
