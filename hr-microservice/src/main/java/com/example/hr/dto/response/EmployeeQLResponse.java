package com.example.hr.dto.response;

import java.util.List;

import com.example.hexagonal.helper.DataTransferObject;
import com.example.hexagonal.helper.TransferDirectionType;
import com.example.hr.domain.Department;
import com.example.hr.domain.Employee;

@DataTransferObject(TransferDirectionType.OUTBOUND)
public record EmployeeQLResponse(
		String identity, 
		String firstName, 
		String lastName, 
		String iban,
		double salary,
		String currency,
		String email,
		int birthYear, 
		String jobStyle, 
		List<String> departments,
		String photo) {

	public static EmployeeQLResponse fromEmployee(Employee employee) {
		return new EmployeeQLResponse(
				employee.getIdentity().getValue(),
				employee.getFullName().firstName(),
				employee.getFullName().lastName(),
				employee.getIban().getValue(),
				employee.getSalary().value(),
				employee.getSalary().currency().name(),
				employee.getEmail().value(),
				employee.getBirthYear().value(),
				employee.getJobStyle().name(),
				employee.getDepartmentList().getDepartments().stream().map(Department::name).toList(),
				employee.getPhoto().toBase64()
		);
		}

}
