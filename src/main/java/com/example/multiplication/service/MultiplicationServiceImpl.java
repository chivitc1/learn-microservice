package com.example.multiplication.service;

import com.example.multiplication.domain.Multiplication;
import com.example.multiplication.domain.MultiplicationResultAttempt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

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

	/**
	 * @param _resultAttempt
	 * @return true if the attempt matches the result of the
	 * multiplication, false otherwise.
	 */
	@Override
	public boolean checkAttempt(MultiplicationResultAttempt _resultAttempt)
	{
		boolean correct = _resultAttempt.getResultAttempt() ==
				_resultAttempt.getMultiplication().getFactorA() *
						_resultAttempt.getMultiplication().getFactorB();
		Assert.isTrue(!_resultAttempt.isCorrect(), "You can't send an attempt marked as correct!!");

		MultiplicationResultAttempt checkedAttempt =
				new MultiplicationResultAttempt(_resultAttempt.getUser(),
						_resultAttempt.getMultiplication(),
						_resultAttempt.getResultAttempt(),
						correct);
		return correct;
	}
}
