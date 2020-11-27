package com.app.controller;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.app.entity.Employee;
import com.app.service.IEmployeeService;

@Controller
@RequestMapping(value = "/employee")
public class EmployeeController {

	@Autowired
	private IEmployeeService employeeService;

	@GetMapping(value = "/register")
	public String getRegisterPage(Model model) {
		model.addAttribute("employee", new Employee());
		return "EmployeeRegister";
	}

	@PostMapping(value = "/register")
	public String processRegister(final @Valid @ModelAttribute("employee") Employee employee,
			BindingResult bindingResult, Model model, RedirectAttributes attributes) {

		// check user exists or not
		if (employeeService.employeeExistsOrNotByEmail(employee.getEmail())) {
			bindingResult.addError(new FieldError("employee", "email", "Email already exists, Try with another email"));
		}

		// check password & re-password not exactly same
		if (employee.getPassword() != null && employee.getRepeatPassword() != null) {
			if (!employee.getPassword().equals(employee.getRepeatPassword())) {
				bindingResult.addError(
						new FieldError("employee", "repeatPassword", "Password & Re-Password should be same"));
			}
		}

		// check user exists by mobileno
		if (!employee.getMobileno().isEmpty()) {
			if (employeeService.employeeExistsOrNotByMobileno(employee.getMobileno())) {
				bindingResult.addError(
						new FieldError("employee", "mobileno", "Mobile No already exists, Try with another mobile no"));
			}
		}

		if (bindingResult.hasErrors()) {
			return "EmployeeRegister";
		}

		Employee savedEmployee = employeeService.Save(employee);
		attributes.addFlashAttribute("successMsg", savedEmployee.getFirstname() + " " + savedEmployee.getLastname()
				+ ", Employee Registered Successfully!");
		return "redirect:/employee/register"; 
	}

	@GetMapping(value = "/update/{id}")
	public String viewEmployeeUpdatePage(@NotNull @PathVariable(name = "id") Long id, Model model) {
		Employee employee = employeeService.findEmployeeById(id);
		model.addAttribute("employee", employee);
		return "UpdateEmployeePage";
	}

	@PostMapping(value = "/update")
	public String processEmployeeUpdatePage(final @Valid @ModelAttribute("employee") Employee employee,
			BindingResult bindingResult) {
		
		String mobileno = employee.getMobileno();
		List<Employee> employeeList = employeeService.findAllEmployees();

		for (Employee emp : employeeList) {
			if (emp.getMobileno().equals(mobileno)) {
				if (!emp.getId().equals(employee.getId())) {
					bindingResult.rejectValue("mobileno", null, "Mobile No already exisits, Try with another no");
				}
			}
		}

		if (bindingResult.hasErrors()) {
			return "UpdateEmployeePage";
		} else {
			Employee updatedEmployee = employeeService.Save(employee);
			return "redirect:/employee/update/" + employee.getId() + "?employeename=" + updatedEmployee.getFirstname()
					+ "&success";
		}
	}

	@GetMapping(value = "/validateEmail")
	public @ResponseBody String checkEmailValidity(@RequestParam("email") String email) {
		if (employeeService.employeeExistsOrNotByEmail(email))
			return "Duplicate";
		else
			return "Unique";
	}

	@GetMapping(value = "/validateMobileno")
	public @ResponseBody String checkMobilenoValidity(@RequestParam("mobileno") String mobileno) {
		if (employeeService.employeeExistsOrNotByMobileno(mobileno))
			return "Duplicate";
		else
			return "Unique";
	}

	@GetMapping(value = "/delete/{id}")
	public String processEmployeeDelete(@NotNull @PathVariable(name = "id") Long id) {
		Employee employee = employeeService.findEmployeeById(id);
		employeeService.deleteEmployee(employee);
		return "redirect:/admin/viewEmployees?id=" + employee.getId() + "&deleted";
	}
}
