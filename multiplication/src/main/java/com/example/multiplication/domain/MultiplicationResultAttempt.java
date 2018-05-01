package com.example.multiplication.domain;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Identifies the attempt from a {@link User} to solve a
 * {@link Multiplication}.
 */
@Getter
@EqualsAndHashCode
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
public final class MultiplicationResultAttempt
{
	@Setter
	private Long id;

	@Setter
	private User user;

	@Setter
	private Multiplication multiplication;

	private final int resultAttempt;

	private final boolean correct;

	MultiplicationResultAttempt()
	{
		id = null;
		user = null;
		multiplication = null;
		resultAttempt = -1;
		correct = false;
	}

}
