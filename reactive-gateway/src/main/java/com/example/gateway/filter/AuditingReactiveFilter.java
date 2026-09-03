package com.example.gateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

@Service
@Order(Integer.MIN_VALUE)
public class AuditingReactiveFilter implements GlobalFilter {

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		var request = exchange.getRequest();
		System.err.println("Path			 : %s".formatted(request.getPath()));
		System.err.println("Method		 : %s".formatted(request.getMethod()));
		System.err.println("URI   		 : %s".formatted(request.getURI()));
		System.err.println("Local Address: %s".formatted(request.getLocalAddress()));
		request.getHeaders().forEach((headerName,value)->{
			System.err.println("%24s: %s".formatted(headerName,value));
		});
		request.getBody().subscribe(System.err::println);
		var response = exchange.getResponse();
		System.out.println("Status code: %s".formatted(response.getStatusCode()));
		return chain.filter(exchange);
	}

}
