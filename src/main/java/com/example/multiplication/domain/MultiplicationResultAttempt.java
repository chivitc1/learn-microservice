package com.example.multiplication.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * Identifies the attempt from a {@link User} to solve a
 * {@link Multiplication}.
 */
@Getter
@EqualsAndHashCode
@ToString
@RequiredArgsConstructor
public final class MultiplicationResultAttempt
{
	private final User user;
	private final Multiplication multiplication;
	private final int resultAttempt;

	private final boolean correct;

	MultiplicationResultAttempt() {
		user = null;
		multiplication = null;
		resultAttempt = -1;
		correct = false;
	}
}
