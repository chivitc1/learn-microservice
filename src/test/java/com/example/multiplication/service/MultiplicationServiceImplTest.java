package com.example.multiplication.service;

import com.example.multiplication.domain.Multiplication;
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
		assertThat(multiplication.getResult()).isEqualTo(1500);
	}

}