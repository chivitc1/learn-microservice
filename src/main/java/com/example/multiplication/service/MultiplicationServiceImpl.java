package com.example.multiplication.service;

import com.example.multiplication.domain.Multiplication;
import com.example.multiplication.domain.MultiplicationResultAttempt;
import com.example.multiplication.domain.User;
import com.example.multiplication.repository.MultiplicationRepository;
import com.example.multiplication.repository.MultiplicationResultAttemptRepository;
import com.example.multiplication.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;

@Service
public class MultiplicationServiceImpl implements MultiplicationService
{
	private RandomGeneratorService randomGeneratorService;

	private MultiplicationResultAttemptRepository attemptRepository;

	private UserRepository userRepository;

	private MultiplicationRepository multiplicationRepository;

	@Autowired
	public MultiplicationServiceImpl(final RandomGeneratorService _randomGeneratorService,
									 final MultiplicationResultAttemptRepository _attemptRepository,
									 final UserRepository _userRepository,
									 final MultiplicationRepository _multiplicationRepository)
	{
		this.randomGeneratorService = _randomGeneratorService;
		this.attemptRepository = _attemptRepository;
		this.userRepository = _userRepository;
		this.multiplicationRepository = _multiplicationRepository;
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
	@Transactional
	@Override
	public boolean checkAttempt(MultiplicationResultAttempt _resultAttempt)
	{
		// Check if the user already exists for that alias
		Optional<User> user = userRepository.findByAlias(_resultAttempt.getUser().getAlias());

		// Check if the multiplication already exists for (factorA, factorB)
		Optional<Multiplication> multiplication = multiplicationRepository.findFirstByFactorAAndFactorB(
				_resultAttempt.getMultiplication().getFactorA(),
				_resultAttempt.getMultiplication().getFactorB());

		// Avoids 'hack' attempts
		Assert.isTrue(!_resultAttempt.isCorrect(), "You can't send an attempt marked as correct!!");

		// Check if the attempt is correct
		boolean correct = _resultAttempt.getResultAttempt() ==
				_resultAttempt.getMultiplication().getFactorA() *
						_resultAttempt.getMultiplication().getFactorB();

		MultiplicationResultAttempt checkedAttempt =
				new MultiplicationResultAttempt(user.orElse(_resultAttempt.getUser()),
						multiplication.orElse(_resultAttempt.getMultiplication()),
						_resultAttempt.getResultAttempt(),
						correct);

		// Stores the attempt
		attemptRepository.save(checkedAttempt);

		return correct;
	}

}
