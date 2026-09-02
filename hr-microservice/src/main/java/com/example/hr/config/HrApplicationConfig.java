package com.example.hr.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.hr.application.HrApplication;
import com.example.hr.application.business.StandardHrApplication;
import com.example.hr.application.business.event.HrEvent;
import com.example.hr.domain.TcKimlikNo;
import com.example.hr.infrastructure.EventPublisher;
import com.example.hr.repository.EmployeeRepository;

@Configuration
public class HrApplicationConfig {

	// factory method
	@Bean
	HrApplication createHrApplication(EmployeeRepository employeeRepository, EventPublisher<HrEvent<TcKimlikNo>> eventPublisher) {
		return new StandardHrApplication(employeeRepository, eventPublisher);
	}
}
