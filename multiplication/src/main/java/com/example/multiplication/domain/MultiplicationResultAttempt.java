package com.example.multiplication.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Identifies the attempt from a {@link User} to solve a
 * {@link Multiplication}.
 */
@Getter
@EqualsAndHashCode
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "multiplication_result_attempt")
public final class MultiplicationResultAttempt
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "user_id")
	private final User user;

	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "multiplication_id")
	private final Multiplication multiplication;

	@Column(name = "result_attempt")
	private final int resultAttempt;

	private final boolean correct;

	MultiplicationResultAttempt()
	{
		user = null;
		multiplication = null;
		resultAttempt = -1;
		correct = false;
	}

}
