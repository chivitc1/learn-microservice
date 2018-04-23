package com.example.multiplication.event;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * Event that models the fact that a {@link
 * com.example.multiplication.domain.Multiplication}
 * has been solved in the system. Provides some context
 information about the multiplication.
 */
@RequiredArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
public class MultiplicationSolvedEvent implements Serializable
{
	private final Long MultiplicationResultAttemptId;
	private final Long userId;
	private final boolean correct;
}
