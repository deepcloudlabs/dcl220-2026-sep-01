package com.example.hr.dto.response;

import java.util.List;

import com.example.constraints.Iban;
import com.example.constraints.TcKimlikNo;
import com.example.hexagonal.helper.DataTransferObject;
import com.example.hexagonal.helper.TransferDirectionType;
import com.example.hr.domain.Department;
import com.example.hr.domain.Employee;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@DataTransferObject(TransferDirectionType.OUTBOUND)
public record EmployeeResponse(@TcKimlikNo
		String identity, 
		@Size(min = 2)
		String firstName, 
		@Size(min = 2)
		String lastName, 
		@Iban
		String iban,
		@Min(30_000)
		double salary,
		@NotBlank
		String currency,
		@Email
		String email,
		@Max(2010)
		int birthYear, 
		@NotBlank
		String jobStyle, 
		List<String> departments) {

	public static EmployeeResponse fromEmployee(Employee employee) {
		return new EmployeeResponse(
				employee.getIdentity().getValue(),
				employee.getFullName().firstName(),
				employee.getFullName().lastName(),
				employee.getIban().getValue(),
				employee.getSalary().value(),
				employee.getSalary().currency().name(),
				employee.getEmail().value(),
				employee.getBirthYear().value(),
				employee.getJobStyle().name(),
				employee.getDepartmentList().getDepartments().stream().map(Department::name).toList()
		);
	}
}
