package com.example.hr.adapter;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import com.example.hr.application.business.event.HrEvent;
import com.example.hr.controller.Adapter;
import com.example.hr.domain.TcKimlikNo;
import com.example.hr.infrastructure.EventPublisher;

@Service
@Adapter(adaptee = ApplicationEventPublisher.class)
public class EventPublisherSpringAdapter implements EventPublisher<HrEvent<TcKimlikNo>> {
	private final ApplicationEventPublisher eventPublisher;

	public EventPublisherSpringAdapter(ApplicationEventPublisher eventPublisher) {
		this.eventPublisher = eventPublisher;
	}

	@Override
	public void publishEvent(HrEvent<TcKimlikNo> event) {
		eventPublisher.publishEvent(event);
	}

}
