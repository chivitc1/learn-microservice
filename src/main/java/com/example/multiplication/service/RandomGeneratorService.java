package com.example.multiplication.service;

public interface RandomGeneratorService
{
	/**
	 * @return a randomly-generated factor. It's always a number
	 * between 11 - 99
	 */
	int generateRandomFactor();
}
