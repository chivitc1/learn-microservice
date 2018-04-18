package com.example.multiplication.service;

import com.example.multiplication.domain.Multiplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MultiplicationServiceImpl implements MultiplicationService
{
	private RandomGeneratorService randomGeneratorService;

	@Autowired
	public MultiplicationServiceImpl(RandomGeneratorService _randomGeneratorService) {
		this.randomGeneratorService = _randomGeneratorService;
	}
	/**
	 * Create a Multiplication object with two random generated factors
	 * between 11 - 99
	 * @return a Multiplication with random factors
	 */
	@Override
	public Multiplication createRandomMultiplication()
	{
		int factorA = randomGeneratorService.generateRandomFactor();
		int factorB = randomGeneratorService.generateRandomFactor();
		return new Multiplication(factorA, factorB);
	}
}
