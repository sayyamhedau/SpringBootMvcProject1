package com.app;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Supplier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.app.entity.Employee;
import com.app.repo.IEmployeeRepository;

@SpringBootApplication
public class SpringBootMvcProject1Application {

	@Autowired
	private IEmployeeRepository employeeRepository;
	
	private static final Logger log = LoggerFactory.getLogger(SpringBootMvcProject1Application.class);

	Supplier<String> generateRandomPassword = () -> {
		StringBuilder passwordBuilder = new StringBuilder();
		Random random = new Random();
		for (int i = 0; i < 10; i++) {
			passwordBuilder.append(random.nextInt(10));
		}
		return passwordBuilder.toString();
	};

	public static void main(String[] args) {
		SpringApplication.run(SpringBootMvcProject1Application.class, args);
	}

	public CommandLineRunner commandLineRunner() {
		List<Employee> employeeList = new ArrayList<>();
		
		return args -> {
			for (int i = 0; i < 14; i++) {
				employeeList.add(new Employee("Firstname" + i, "Lastname" + i, "firstnamelastname" + i + "@gmail.com",
						generateRandomPassword.get(), generateRandomPassword.get(), true));
			}
			employeeRepository.saveAll(employeeList);
			log.info("Employee Added Successfully");
		};
	}
}
