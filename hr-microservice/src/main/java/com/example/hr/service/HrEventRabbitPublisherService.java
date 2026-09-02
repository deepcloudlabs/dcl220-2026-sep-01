package com.example.hr.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import com.example.hr.application.business.event.HrEvent;
import com.example.hr.domain.TcKimlikNo;

import tools.jackson.databind.ObjectMapper;

@Service
//@ConditionalOnProperty(name = "messaging", havingValue = "amqp")
//@ConditionalOnProperty(name = "messaging", havingValue = "amqp-kafka")
public class HrEventRabbitPublisherService {
	private final ObjectMapper objectMapper;
	private final RabbitTemplate rabbitTemplate;
	private final String exchangeName;

	public HrEventRabbitPublisherService(ObjectMapper objectMapper, RabbitTemplate rabbitTemplate,
			@Value("${exchangeName}") String exchangeName) {
		this.objectMapper = objectMapper;
		this.rabbitTemplate = rabbitTemplate;
		this.exchangeName = exchangeName;
	}

	@EventListener
	public void listenHrEvents(HrEvent<TcKimlikNo> event) {
		var eventAsJson = objectMapper.writeValueAsString(event);
		rabbitTemplate.convertAndSend(exchangeName, "", eventAsJson);
	}
}
