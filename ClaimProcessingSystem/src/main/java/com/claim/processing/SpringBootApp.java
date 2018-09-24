package com.claim.processing;

import java.util.Arrays;
import java.util.List;

import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Declarable;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableJpaRepositories
public class SpringBootApp {
	public static void main(String[] args) throws Exception {
		new SpringApplication(SpringBootApp.class).run(args);
	}

	@Bean
	public RestTemplate RestTemplate() {
		return new RestTemplate();
	}

	@Bean
	public MappingJackson2MessageConverter consumerJackson2MessageConverter() {
		return new MappingJackson2MessageConverter();
	}

	@Bean
	public Jackson2JsonMessageConverter producerJackson2MessageConverter() {
		return new Jackson2JsonMessageConverter();
	}

	@Bean
	public List<Declarable> topicBindings() {
		Queue topicQueue = new Queue("claimSystemInbound", true);
		DirectExchange topicExchange = new DirectExchange("claimExchange");
		return Arrays.asList(topicQueue, topicExchange,
				BindingBuilder.bind(topicQueue).to(topicExchange).withQueueName());
	}
}
