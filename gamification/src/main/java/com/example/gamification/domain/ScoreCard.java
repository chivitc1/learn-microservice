package com.example.gamification.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * This class represents the Score linked to an attempt in the game,
 * with an associated user and the timestamp in which the score
 * is registered.
 */
@RequiredArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "score_card")
public final class ScoreCard
{
	// The default score assigned to this card, if not specified.
	public static final int DEFAULT_SCORE = 10;

	@Id
	@GeneratedValue
	private final Long id;

	@Column(name = "user_id")
	private final Long userId;

	@Column(name = "attempt_id")
	private final Long attemptId;

	@Column(name = "score_timestamp")
	private final long scoreTimestamp;

	@Column(name = "SCORE")
	private final int score;

	// Empty constructor for JSON / JPA
	public ScoreCard() {
		this(null, null, null, 0, 0);
	}

	public ScoreCard(final Long userId, final Long attemptId) {
		this(null, userId, attemptId, System.currentTimeMillis(), DEFAULT_SCORE);
	}
}
