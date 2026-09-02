package com.example.hr.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.data.mongodb.autoconfigure.DataMongoRepositoriesAutoConfiguration;
import org.springframework.boot.mongodb.autoconfigure.MongoAutoConfiguration;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration(
		exclude = {
				MongoAutoConfiguration.class,
				DataMongoRepositoriesAutoConfiguration.class
		}
)
@ConditionalOnProperty(name="persistence",havingValue = "useJpa")
public class DisableMongoConfig {

}
