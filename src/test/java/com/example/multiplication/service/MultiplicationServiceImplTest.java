package com.example.multiplication.service;

import com.example.multiplication.domain.Multiplication;
import com.example.multiplication.domain.MultiplicationResultAttempt;
import com.example.multiplication.domain.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class MultiplicationServiceImplTest
{
	private MultiplicationServiceImpl multiplicationServiceImpl;

	@Mock
	private RandomGeneratorService randomGeneratorService;

	@Before
	public void setUp() throws Exception
	{
		multiplicationServiceImpl = new MultiplicationServiceImpl(randomGeneratorService);
	}

	@Test
	public void createRandomMultiplication() throws Exception
	{
		// Given
		given(randomGeneratorService.generateRandomFactor()).willReturn(50, 30);

		// When
		Multiplication multiplication = multiplicationServiceImpl.createRandomMultiplication();

		// Assert
		assertThat(multiplication.getFactorA()).isEqualTo(50);
		assertThat(multiplication.getFactorB()).isEqualTo(30);
	}

	@Test
	public void checkCorrectAttemptTest() throws Exception
	{
		// given
		Multiplication multiplication = new Multiplication(50, 60);
		User user = new User("chinv");
		MultiplicationResultAttempt attempt =
				new MultiplicationResultAttempt(user, multiplication, 3000);

		// when
		boolean attemptResult = multiplicationServiceImpl.checkAttempt(attempt);

		// verify
		assertThat(attemptResult).isTrue();
	}

	@Test
	public void checkWrongAttemptTest() throws Exception
	{
		// given
		Multiplication multiplication = new Multiplication(50, 60);
		User user = new User("chinv");
		MultiplicationResultAttempt attempt =
				new MultiplicationResultAttempt(user, multiplication, 3001);

		// when
		boolean attemptResult = multiplicationServiceImpl.checkAttempt(attempt);

		// verify
		assertThat(attemptResult).isFalse();
	}
}