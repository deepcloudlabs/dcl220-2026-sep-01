package com.example.hr.entity;

import java.util.List;
import java.util.Objects;

import com.example.constraints.Iban;
import com.example.constraints.TcKimlikNo;
import com.example.hr.domain.Department;
import com.example.hr.domain.JobStyle;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "employees")
public class EmployeeEntity {
	@Id
	@TcKimlikNo
	@NotBlank
	private String identity;

	@NotBlank
	@Size(min = 2, max = 50)
	@Column(name = "fname")
	private String firstName;

	@NotBlank
	@Size(min = 2, max = 50)
	@Column(name = "lname")
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
	@Column(name = "byear")
	private int birthYear;

	@NotNull
	@Enumerated(EnumType.ORDINAL)
	private JobStyle jobStyle;

	@NotNull
	@ElementCollection(targetClass = Department.class)
	@CollectionTable(name = "emp_depts", joinColumns = @JoinColumn(name = "employee_id"))
	@Enumerated(EnumType.STRING)
	private List<Department> departmentList;

	@Lob
	@Column(columnDefinition = "LONGBLOB")
	private byte[] photo;
	
	public EmployeeEntity() {
	}

	public EmployeeEntity(
			@NotBlank String identity, 
			@NotBlank @Size(min = 2, max = 50) String firstName,
			@NotBlank @Size(min = 2, max = 50) String lastName, 
			@NotBlank String iban,
			@DecimalMin("30000.0") double salary, 
			@NotBlank String currency, 
			@NotBlank @Email String email,
			@Max(2010) int birthYear, 
			@NotBlank JobStyle jobStyle, 
			@NotNull List<Department> departmentList,
			byte[] photo) {
		this.identity = identity;
		this.firstName = firstName;
		this.lastName = lastName;
		this.iban = iban;
		this.salary = salary;
		this.currency = currency;
		this.email = email;
		this.birthYear = birthYear;
		this.jobStyle = jobStyle;
		this.departmentList = departmentList;
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

	public JobStyle getJobStyle() {
		return jobStyle;
	}

	public List<Department> getDepartmentList() {
		return departmentList;
	}

	public void setDepartmentList(List<Department> departmentList) {
		this.departmentList = departmentList;
	}

	public byte[] getPhoto() {
		return photo;
	}

	public void setPhoto(byte[] photo) {
		this.photo = photo;
	}

	public void setJobStyle(JobStyle jobStyle) {
		this.jobStyle = jobStyle;
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
		EmployeeEntity other = (EmployeeEntity) obj;
		return Objects.equals(identity, other.identity);
	}

	@Override
	public String toString() {
		return "EmployeeDocument [identity=" + identity + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", iban=" + iban + ", salary=" + salary + ", currency=" + currency + ", email=" + email
				+ ", birthYear=" + birthYear + ", jobStyle=" + jobStyle + ", departmentList=" + departmentList + "]";
	}

}
