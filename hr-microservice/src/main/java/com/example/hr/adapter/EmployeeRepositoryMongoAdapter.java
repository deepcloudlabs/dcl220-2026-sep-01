package com.example.hr.adapter;

import java.util.Optional;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.hr.controller.Adapter;
import com.example.hr.document.EmployeeDocument;
import com.example.hr.domain.Department;
import com.example.hr.domain.Employee;
import com.example.hr.domain.TcKimlikNo;
import com.example.hr.repository.EmployeeDocumentRepository;
import com.example.hr.repository.EmployeeRepository;

@Repository
@Adapter(adaptee = EmployeeDocumentRepository.class)
@ConditionalOnProperty(name="persistence", havingValue = "useMongo")
public class EmployeeRepositoryMongoAdapter implements EmployeeRepository {
	private final EmployeeDocumentRepository employeeDocumentRepository;

	public EmployeeRepositoryMongoAdapter(EmployeeDocumentRepository employeeDocumentRepository) {
		this.employeeDocumentRepository = employeeDocumentRepository;
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Employee> findEmployeeByIdentity(TcKimlikNo identity) {
		return employeeDocumentRepository.findById(identity.getValue()).map(EmployeeRepositoryMongoAdapter::mapDocumentToAggregate);
	}

	@Override
	@Transactional(readOnly = true)
	public boolean employeeExists(TcKimlikNo identity) {
		return employeeDocumentRepository.existsById(identity.getValue());
	}

	@Override
	@Transactional
	public Employee persist(Employee employee) {
		var employeeDocument = mapAggregateToDocument(employee);
		var insertedEmployeeDocument = employeeDocumentRepository.insert(employeeDocument);
		return mapDocumentToAggregate(insertedEmployeeDocument);
	}

	@Override
	@Transactional
	public void save(Employee employee) {
		employeeDocumentRepository.save(mapAggregateToDocument(employee));
	}

	@Override
	@Transactional
	public void remove(Employee employee) {
		employeeDocumentRepository.deleteById(employee.getIdentity().getValue());
	}
	
	public static Employee mapDocumentToAggregate(EmployeeDocument document) {
		return new Employee.Builder(document.getIdentity())
	              .fullName(document.getFirstName(), document.getLastName())
	              .salary(document.getSalary(), document.getCurrency())
	              .iban(document.getIban())
	              .birthYear(document.getBirthYear())
	              .email(document.getEmail())
	              .jobStyle(document.getJobStyle())
	              .photo(document.getPhoto())
	              .departmentList(document.getDepartmentList().stream().map(Department::valueOf).toList().toArray(new Department[0]))
	              .build();
	}
	
	public static EmployeeDocument mapAggregateToDocument(Employee employee) {
		return new EmployeeDocument(
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
