package com.example.multiplication.service;

import com.example.multiplication.domain.Multiplication;
import com.example.multiplication.domain.MultiplicationResultAttempt;

public interface MultiplicationService
{
	/**
	 * Create a Multiplication object with two random generated factors
	 * between 11 - 99
	 * @return a Multiplication with random factors
	 */
	Multiplication createRandomMultiplication();

	/**
	 * @return true if the attempt matches the result of the
	 * multiplication, false otherwise.
	 */
	boolean checkAttempt(final MultiplicationResultAttempt _resultAttempt);
}
