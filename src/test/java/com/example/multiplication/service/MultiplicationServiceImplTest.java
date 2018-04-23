package com.example.multiplication.service;

import com.example.multiplication.domain.Multiplication;
import com.example.multiplication.domain.MultiplicationResultAttempt;
import com.example.multiplication.domain.User;
import com.example.multiplication.repository.MultiplicationRepository;
import com.example.multiplication.repository.MultiplicationResultAttemptRepository;
import com.example.multiplication.repository.UserRepository;
import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class MultiplicationServiceImplTest
{
	private MultiplicationServiceImpl multiplicationServiceImpl;

	@Mock
	private RandomGeneratorService randomGeneratorService;

	@Mock
	private MultiplicationResultAttemptRepository attemptRepository;

	@Mock
	private UserRepository userRepository;

	@Mock
	private MultiplicationRepository multiplicationRepository;

	@Before
	public void setUp() throws Exception
	{
		multiplicationServiceImpl = new MultiplicationServiceImpl(randomGeneratorService,
				attemptRepository, userRepository, multiplicationRepository);
		multiplicationRepository.save(new Multiplication(50, 60));
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
				new MultiplicationResultAttempt(user, multiplication, 3000, false);

		MultiplicationResultAttempt verifiedAttempt =
				new MultiplicationResultAttempt(user, multiplication, 3000, true);
		given(userRepository.findByAlias("chinv"))
				.willReturn(Optional.empty());
		given(multiplicationRepository.findFirstByFactorAAndFactorB(
				attempt.getMultiplication().getFactorA(),
				attempt.getMultiplication().getFactorB())).willReturn(Optional.empty());

		// when
		MultiplicationResultAttempt attemptChecked = multiplicationServiceImpl.checkAttempt(attempt);

		// verify
		assertThat(attemptChecked.isCorrect()).isTrue();
		verify(attemptRepository).save(verifiedAttempt);
	}

	@Test
	public void checkWrongAttemptTest() throws Exception
	{
		// given
		Multiplication multiplication = new Multiplication(50, 60);
		User user = new User("chinv");
		MultiplicationResultAttempt attempt =
				new MultiplicationResultAttempt(user, multiplication, 3001, false);

		given(userRepository.findByAlias("chinv"))
				.willReturn(Optional.empty());
		given(multiplicationRepository.findFirstByFactorAAndFactorB(
				attempt.getMultiplication().getFactorA(),
				attempt.getMultiplication().getFactorB())).willReturn(Optional.empty());

		// when
		MultiplicationResultAttempt attemptChecked = multiplicationServiceImpl.checkAttempt(attempt);

		// verify
		assertThat(attemptChecked.isCorrect()).isFalse();
		verify(attemptRepository).save(attempt);
	}

	@Test
	public void retrieveStatsTest() {
		// given
		Multiplication multiplication = new Multiplication(50, 60);
		User user = new User("chinv");

		MultiplicationResultAttempt attempt1 = new
				MultiplicationResultAttempt(
				user, multiplication, 3010, false);
		MultiplicationResultAttempt attempt2 = new
				MultiplicationResultAttempt(
				user, multiplication, 3051, false);
		List<MultiplicationResultAttempt> latestAttempts =
				Lists.newArrayList(attempt1, attempt2);

		given(userRepository.findByAlias("chinv")).willReturn(Optional.empty());
		given(attemptRepository.findTop5ByUserAliasOrderByIdDesc("chinv"))
			.willReturn(latestAttempts);

		// when
		List<MultiplicationResultAttempt> latestAttemptResult =
				multiplicationServiceImpl.getStatsForUser("chinv");

		// verify
		assertThat(latestAttemptResult).isEqualTo(latestAttempts);
	}
}