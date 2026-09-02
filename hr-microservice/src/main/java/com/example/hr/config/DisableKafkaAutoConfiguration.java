package com.example.hr.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.kafka.autoconfigure.KafkaAutoConfiguration;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(name = "messaging", havingValue = "amqp")
@EnableAutoConfiguration(exclude = { KafkaAutoConfiguration.class })
public class DisableKafkaAutoConfiguration {

}
