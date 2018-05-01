package com.example.gamification.repository;

import com.example.gamification.domain.BadgeCard;

import java.util.List;

/**
 * Handles data operations with BadgeCards
 */
public interface BadgeCardRepository
{
	/**
	 * Retrieves all BadgeCards for a given user.
	 * @param userId the id of the user to look for BadgeCards
	 * @return the list of BadgeCards, sorted by most recent.
	 */
	List<BadgeCard> findByUserIdOrderByBadgeTimestampDesc(final Long userId);

	BadgeCard save(BadgeCard _badgeCard);
}
