package com.example.hr.document;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.example.constraints.Iban;
import com.example.constraints.TcKimlikNo;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Document(collection = "employees")
public class EmployeeDocument {
	@Id
	@TcKimlikNo
	@NotBlank
	private String identity;
	
	@NotBlank
	@Size(min = 2, max = 50)
	@Field("fname")
	private String firstName;
	
	@NotBlank
	@Size(min = 2, max = 50)
	@Field("lname")
	private String lastName;
	
	@NotBlank
	@Iban
	private String iban;
	
    @DecimalMin("30000.0")
	private double salary;
	
	@NotBlank
	private String currency;
	
	@NotBlank
	@Email
	private String email;
	
	@Max(2010)
	@Field("byear")
	private int birthYear;

	@NotBlank
	private String jobStyle;
	
    @NotNull
	private List<String> departmentList= new ArrayList<>();
    
	private String photo;

	
	
	public EmployeeDocument() {
	}

	
	public EmployeeDocument(
			@NotBlank String identity, 
			@NotBlank @Size(min = 2, max = 50) String firstName,
			@NotBlank @Size(min = 2, max = 50) String lastName, 
			@NotBlank String iban,
			@DecimalMin("30000.0") double salary, 
			@NotBlank String currency, 
			@NotBlank @Email String email,
			@Max(2010) int birthYear, 
			@NotBlank String jobStyle, 
			@NotNull List<String> departmentList, 
			String photo) {
		this.identity = identity;
		this.firstName = firstName;
		this.lastName = lastName;
		this.iban = iban;
		this.salary = salary;
		this.currency = currency;
		this.email = email;
		this.birthYear = birthYear;
		this.jobStyle = jobStyle;
        setDepartmentList(departmentList); 
		this.photo = photo;
	}


	public String getIdentity() {
		return identity;
	}

	public void setIdentity(String identity) {
		this.identity = identity;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getIban() {
		return iban;
	}

	public void setIban(String iban) {
		this.iban = iban;
	}

	public double getSalary() {
		return salary;
	}

	public void setSalary(double salary) {
		this.salary = salary;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getBirthYear() {
		return birthYear;
	}

	public void setBirthYear(int birthYear) {
		this.birthYear = birthYear;
	}

	public String getJobStyle() {
		return jobStyle;
	}

	public void setJobStyle(String jobStyle) {
		this.jobStyle = jobStyle;
	}

	public List<String> getDepartmentList() {
		return departmentList;
	}

    public void setDepartmentList(List<String> departmentList) {
        this.departmentList = (departmentList == null) ? new ArrayList<>() : new ArrayList<>(departmentList);
    }

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	@Override
	public int hashCode() {
		return Objects.hash(identity);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EmployeeDocument other = (EmployeeDocument) obj;
		return Objects.equals(identity, other.identity);
	}

	@Override
	public String toString() {
		return "EmployeeDocument [identity=" + identity + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", iban=" + iban + ", salary=" + salary + ", currency=" + currency + ", email=" + email
				+ ", birthYear=" + birthYear + ", jobStyle=" + jobStyle + ", departmentList=" + departmentList + "]";
	}

}
