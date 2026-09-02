package com.example.hr.config;

import org.springframework.boot.amqp.autoconfigure.RabbitAutoConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(name = "messaging", havingValue = "kafka")
@EnableAutoConfiguration(exclude = { RabbitAutoConfiguration.class })
public class DisableAmqpAutoConfiguration {

}
