package com.example.multiplication.service;

import com.example.multiplication.domain.Multiplication;

public interface MultiplicationService
{
	/**
	 * Create a Multiplication object with two random generated factors
	 * between 11 - 99
	 * @return a Multiplication with random factors
	 */
	Multiplication createRandomMultiplication();
}
