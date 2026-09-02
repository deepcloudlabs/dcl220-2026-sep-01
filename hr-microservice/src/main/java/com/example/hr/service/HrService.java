package com.example.hr.service;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.example.hr.application.HrApplication;
import com.example.hr.domain.Employee;
import com.example.hr.domain.Rate;
import com.example.hr.domain.TcKimlikNo;
import com.example.hr.dto.request.HireEmployeeRequest;
import com.example.hr.dto.request.IncreaseSalaryRequest;
import com.example.hr.dto.response.EmployeePhotoResponse;
import com.example.hr.dto.response.EmployeeQLResponse;
import com.example.hr.dto.response.EmployeeResponse;
import com.example.hr.dto.response.HireEmployeeResponse;

@Service
public class HrService {
	private static final String EMPLOYEES_CACHE = "employees";

	private final HrApplication hrApplication;

	public HrService(HrApplication hrApplication) {
		this.hrApplication = hrApplication;
	}

	@Transactional
	public HireEmployeeResponse hireEmployee(HireEmployeeRequest request) {
		Employee employee = request.toEmployee();
		hrApplication.hireEmployee(employee);
		return new HireEmployeeResponse("success");
	}

	@Transactional
	@CacheEvict(cacheNames = EMPLOYEES_CACHE, key = "#identity")
	public EmployeeResponse fireEmployee(String identity) {
		var firedEmployee = hrApplication.fireEmployee(toTcKimlikNo(identity))
				.orElseThrow(() -> notFound(identity, "fire"));
		return EmployeeResponse.fromEmployee(firedEmployee);
	}

	@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
	@CacheEvict(cacheNames = EMPLOYEES_CACHE, key = "#identity")
	public EmployeeResponse increaseSalary(String identity, IncreaseSalaryRequest request) {
		var updatedEmployee = hrApplication.increaseSalary(toTcKimlikNo(identity), new Rate(request.rate()))
				.orElseThrow(() -> notFound(identity, "increase salary for"));
		return EmployeeResponse.fromEmployee(updatedEmployee);
	}

	@Transactional(readOnly = true)
	@Cacheable(cacheNames = EMPLOYEES_CACHE, key = "#identity")
	public EmployeeResponse getEmployee(String identity) {
		return EmployeeResponse.fromEmployee(getEmployeeOrThrow(identity, "get"));
	}

	@Transactional(readOnly = true)
	public EmployeePhotoResponse getEmployeePhoto(String identity) {
		Employee employee = hrApplication.getEmployee(TcKimlikNo.valueOf(identity)).orElseThrow(
				() -> new IllegalArgumentException("No such employee (%s) to get photo.".formatted(identity)));
		return new EmployeePhotoResponse(identity, employee.getPhoto().toBase64());
	}

	private Employee getEmployeeOrThrow(String identity, String action) {
		return hrApplication.getEmployee(toTcKimlikNo(identity)).orElseThrow(() -> notFound(identity, action));
	}

	private TcKimlikNo toTcKimlikNo(String identity) {
		return TcKimlikNo.valueOf(identity);
	}

	private IllegalArgumentException notFound(String identity, String action) {
		return new IllegalArgumentException("No such employee (%s) to %s.".formatted(identity, action));
	}

	@Transactional(readOnly = true)
	public EmployeeQLResponse findEmployeeByIdentity(String identity) {
		return EmployeeQLResponse.fromEmployee(getEmployeeOrThrow(identity, "get"));
	}
}
