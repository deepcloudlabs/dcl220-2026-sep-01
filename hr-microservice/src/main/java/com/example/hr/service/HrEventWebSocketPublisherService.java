package com.example.hr.service;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import com.example.hr.application.business.event.HrEvent;
import com.example.hr.domain.TcKimlikNo;

import tools.jackson.databind.ObjectMapper;

@Service
public class HrEventWebSocketPublisherService implements WebSocketHandler {
	private final Map<String,WebSocketSession> websocketSessions = new ConcurrentHashMap<>();
	private final ObjectMapper objectMapper;
	
	public HrEventWebSocketPublisherService(ObjectMapper objectMapper) {
		this.objectMapper = objectMapper;
	}

	@EventListener
	public void listenHrEvents(HrEvent<TcKimlikNo> event) {
		var eventMessage = new TextMessage(objectMapper.writeValueAsString(event));
		websocketSessions.forEach((_,session) -> {
			try {
				session.sendMessage(eventMessage);
			} catch (IOException e) {
				System.err.println("An eeor has occured while sending the event messge: %s".formatted(e.getMessage()));
			}
		});
	}

	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		websocketSessions.put(session.getId(), session);
	}

	@Override
	public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
	}

	@Override
	public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
		websocketSessions.remove(session.getId());
		System.err.println("Error has occured in websocket session: %s".formatted(session.getId()));
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
		websocketSessions.remove(session.getId());
	}

	@Override
	public boolean supportsPartialMessages() {
		return false;
	}
}
