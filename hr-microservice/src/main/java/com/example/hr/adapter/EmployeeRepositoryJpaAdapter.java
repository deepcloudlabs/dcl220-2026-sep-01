package com.example.hr.adapter;

import java.util.Optional;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.hr.controller.Adapter;
import com.example.hr.domain.Department;
import com.example.hr.domain.Employee;
import com.example.hr.domain.TcKimlikNo;
import com.example.hr.entity.EmployeeEntity;
import com.example.hr.repository.EmployeeEntityRepository;
import com.example.hr.repository.EmployeeRepository;

@Repository
@Adapter(adaptee = EmployeeEntityRepository.class)
@ConditionalOnProperty(name = "persistence", havingValue = "useJpa")
public class EmployeeRepositoryJpaAdapter implements EmployeeRepository {
	private final EmployeeEntityRepository employeeEntityRepository;

	public EmployeeRepositoryJpaAdapter(EmployeeEntityRepository employeeEntityRepository) {
		this.employeeEntityRepository = employeeEntityRepository;
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Employee> findEmployeeByIdentity(TcKimlikNo identity) {
		return employeeEntityRepository.findById(identity.getValue())
				.map(EmployeeRepositoryJpaAdapter::mapJpaEntityToAggregate);
	}

	@Override
	@Transactional(readOnly = true)
	public boolean employeeExists(TcKimlikNo identity) {
		return employeeEntityRepository.existsById(identity.getValue());
	}

	@Override
	@Transactional()
	public Employee persist(Employee employee) {
		var employeeEntity = mapAggregateToJpaEntity(employee);
		var insertedEmployeeEntity = employeeEntityRepository.save(employeeEntity);
		return mapJpaEntityToAggregate(insertedEmployeeEntity);
	}

	@Override
	@Transactional
	public void save(Employee employee) {
		var employeeEntity = mapAggregateToJpaEntity(employee);
		employeeEntityRepository.save(employeeEntity);
	}

	@Override
	@Transactional
	public void remove(Employee employee) {
		employeeEntityRepository.deleteById(employee.getIdentity().getValue());
	}

	public static Employee mapJpaEntityToAggregate(EmployeeEntity entity) {
		return new Employee.Builder(entity.getIdentity()).fullName(entity.getFirstName(), entity.getLastName())
				.salary(entity.getSalary(), entity.getCurrency()).iban(entity.getIban())
				.birthYear(entity.getBirthYear()).email(entity.getEmail()).jobStyle(entity.getJobStyle().name())
				.photo(entity.getPhoto()).departmentList(entity.getDepartmentList().toArray(new Department[0])).build();
	}

	public static EmployeeEntity mapAggregateToJpaEntity(Employee employee) {
		return new EmployeeEntity(employee.getIdentity().getValue(), employee.getFullName().firstName(),
				employee.getFullName().lastName(), employee.getIban().getValue(), employee.getSalary().value(),
				employee.getSalary().currency().name(), employee.getEmail().value(), employee.getBirthYear().value(),
				employee.getJobStyle(), employee.getDepartmentList().getDepartments(), employee.getPhoto().values());
	}
}
