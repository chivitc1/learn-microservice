package com.example.multiplication.configuration;

import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configures RabbitMQ to use events in our application.
 */
@Configuration
public class RabbitMQConfiguration
{
	@Bean
	public TopicExchange multiplicationExchange(
			@Value("${multiplication.exchange}") final String _exchangeName) {
		return new TopicExchange(_exchangeName);
	}

	@Bean
	public RabbitTemplate rabbitTemplate(
			final ConnectionFactory _connectionFactory) {
		final RabbitTemplate rabbitTemplate = new RabbitTemplate(_connectionFactory);
		rabbitTemplate.setMessageConverter(producerJackson2MessageConverter());

		return rabbitTemplate;
	}

	@Bean
	public Jackson2JsonMessageConverter producerJackson2MessageConverter() {
		return new Jackson2JsonMessageConverter();
	}

}
