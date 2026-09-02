package com.example.hr.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.example.hr.application.business.event.HrEvent;
import com.example.hr.domain.TcKimlikNo;

import tools.jackson.databind.ObjectMapper;

@Service
//@ConditionalOnProperty(name = "messaging", havingValue = "kafka")
//@ConditionalOnProperty(name = "messaging", havingValue = "amqp-kafka")
public class HrEventKafkaPublisherService {
	private final ObjectMapper objectMapper;
	private final KafkaTemplate<String, String> kafkaTemplate;
	private final String topicName;

	public HrEventKafkaPublisherService(ObjectMapper objectMapper, KafkaTemplate<String, String> kafkaTemplate,
			@Value("${topicName}") String topicName) {
		this.objectMapper = objectMapper;
		this.kafkaTemplate = kafkaTemplate;
		this.topicName = topicName;
	}

	@EventListener
	public void listenHrEvents(HrEvent<TcKimlikNo> event) {
		var eventAsJson = objectMapper.writeValueAsString(event);
		kafkaTemplate.send(topicName, event.getIdentity().getValue(), eventAsJson);
	}
}
