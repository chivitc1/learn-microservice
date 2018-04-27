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
 * This class links a Badge to a User. Contains also a
 * timestamp with the moment in which the user got it.
 */
@RequiredArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "badge_card")
public final class BadgeCard
{
	@Id
	@GeneratedValue
	private final Long id;

	@Column(name = "user_id")
	private final Long userId;

	@Column(name = "badge_timestamp")
	private final long badgeTimestamp;
	private final Badge badge;

	// Empty constructor for JSON / JPA
	public BadgeCard() {
		this(null, null, 0, null);
	}

	public BadgeCard(final Long userId, final Badge badge) {
		this(null, userId, System.currentTimeMillis(), badge);
	}
}
