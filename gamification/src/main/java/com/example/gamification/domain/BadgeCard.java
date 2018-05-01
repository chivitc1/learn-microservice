package com.example.gamification.domain;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.sql.Timestamp;

/**
 * This class links a Badge to a User. Contains also a
 * timestamp with the moment in which the user got it.
 */
@RequiredArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public final class BadgeCard
{
	@Setter
	private Long id;

	@NonNull
	private final Long userId;


	private final Timestamp badgeTimestamp;

	private final Badge badge;

	// Empty constructor for JSON
	public BadgeCard() {
		this(null, -1L, new Timestamp(System.currentTimeMillis()), null);
	}

	public BadgeCard(final Long userId, final Badge badge) {
		this(null, userId, new Timestamp(System.currentTimeMillis()), badge);
	}
}
