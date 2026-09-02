package com.example.hr.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.annotation.RequestScope;

import com.example.constraints.TcKimlikNo;
import com.example.ddd.helper.OpenHostService;
import com.example.hr.application.HrApplication;
import com.example.hr.dto.request.HireEmployeeRequest;
import com.example.hr.dto.request.IncreaseSalaryRequest;
import com.example.hr.dto.response.EmployeePhotoResponse;
import com.example.hr.dto.response.EmployeeResponse;
import com.example.hr.dto.response.HireEmployeeResponse;
import com.example.hr.service.HrService;

@RestController
@RequestScope
@RequestMapping("/employees")
@CrossOrigin
@Validated
@OpenHostService(boundedContext = "hr", port = HrApplication.class)
@Adapter(adaptee = HrService.class)
public class HrRestController {
	private final HrService hrService;
	
	public HrRestController(HrService hrService) {
		this.hrService = hrService;
	}

	@PostMapping
	public HireEmployeeResponse hireEmployee(@RequestBody @Validated HireEmployeeRequest request) {
		return hrService.hireEmployee(request);
	}

	@DeleteMapping("/{identity}")
	public EmployeeResponse fireEmployee(@PathVariable @Validated @TcKimlikNo String identity) {
		return hrService.fireEmployee(identity);
	}

	@PutMapping("/{identity}")
	public EmployeeResponse increaseSalary(@PathVariable @Validated @TcKimlikNo String identity, @RequestBody @Validated IncreaseSalaryRequest request) {
		return hrService.increaseSalary(identity,request);

	}

	@GetMapping("/{identity}")
	public EmployeeResponse getEmployee(@PathVariable @Validated @TcKimlikNo String identity) {
		return hrService.getEmployee(identity);
	}
	
	@GetMapping("/{identity}/photo")
	public EmployeePhotoResponse getEmployeePhoto(@PathVariable @Validated @TcKimlikNo String identity) {
		return hrService.getEmployeePhoto(identity);
	}

}
