package com.example.hr.dto.request;

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

@DataTransferObject(TransferDirectionType.INBOUND)
public record HireEmployeeRequest(
		@TcKimlikNo
		String identity, 
		@Size(min = 2, max=50)
		String firstName, 
		@Size(min = 2, max=50)
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
		List<String> departmentList,		
		String photo
) {

	public Employee toEmployee() {
		return new Employee.Builder(identity)
	              .fullName(firstName, lastName)
	              .salary(salary, currency)
	              .iban(iban)
	              .birthYear(birthYear)
	              .email(email)
	              .jobStyle(jobStyle)
	              .photo(photo)
	              .departmentList(departmentList.stream().map(Department::valueOf).toList().toArray(new Department[0]))
	              .build();		
	}

}
