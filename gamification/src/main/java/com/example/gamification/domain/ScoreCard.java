package com.example.gamification.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.sql.Timestamp;

/**
 * This class represents the Score linked to an attempt in the game,
 * with an associated user and the timestamp in which the score
 * is registered.
 */
@RequiredArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
public final class ScoreCard
{
	// The default score assigned to this card, if not specified.
	public static final int DEFAULT_SCORE = 10;

	private final Long id;

	@NonNull
	private final Long userId;

	@NonNull
	private final Long attemptId;

	private final Timestamp scoreTimestamp;

	@NonNull
	private final Integer score;

	// Empty constructor for JSON / JPA
	public ScoreCard() {
		this(null, null, null, new Timestamp(System.currentTimeMillis()), 0);
	}

	public ScoreCard(final Long userId, final Long attemptId) {
		this(null, userId, attemptId, new Timestamp(System.currentTimeMillis()), DEFAULT_SCORE);
	}
}
