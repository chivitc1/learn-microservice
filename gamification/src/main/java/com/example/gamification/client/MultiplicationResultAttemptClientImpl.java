package com.example.gamification.client;

import com.example.gamification.client.dto.MultiplicationResultAttempt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * This implementation of MultiplicationResultAttemptClient
 interface connects to
 * the Multiplication microservice via REST.
 */
@Component
public class MultiplicationResultAttemptClientImpl
		implements MultiplicationResultAttemptClient
{
	private final RestTemplate restTemplate;

	private final String multiplicationHost;

	@Autowired
	public MultiplicationResultAttemptClientImpl
			(final RestTemplate restTemplate,
			 @Value("${multiplicationHost}") final String multiplicationHost) {
		this.restTemplate = restTemplate;
		this.multiplicationHost = multiplicationHost;
	}

	@Override
	public MultiplicationResultAttempt retrieveMultiplicationResultAttemptbyId(Long multiplicationId)
	{
		return restTemplate.getForObject(
				multiplicationHost + "/results/" + multiplicationId,
				MultiplicationResultAttempt.class
		);
	}
}
