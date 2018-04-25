package com.example.gamification.client;

import com.example.gamification.client.dto.MultiplicationResultAttempt;

/**
 * This interface allows us to connect to the Multiplication
 * microservice.
 * Note that it's agnostic to the way of communication.
 */
public interface MultiplicationResultAttemptClient
{
	MultiplicationResultAttempt retrieveMultiplicationResultAttemptbyId(final Long multiplicationId);
}
