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
public class MultiplicationResultAttempt
{
	private final User user;
	private final Multiplication multiplication;
	private final int resultAttempt;

	MultiplicationResultAttempt() {
		user = null;
		multiplication = null;
		resultAttempt = -1;
	}
}
