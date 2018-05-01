package com.example.gamification.repository;

import com.example.gamification.domain.LeaderBoardRow;
import com.example.gamification.domain.ScoreCard;

import java.util.List;

/**
 * Handles CRUD operations with ScoreCards
 */
public interface ScoreCardRepository
{
	/**
	 * Gets the total score for a given user, being the sum of
	 the scores of all his ScoreCards.
	 * @param userId the id of the user for which the total
	score should be retrieved
	 * @return the total score for the given user
	 */
	Integer getTotalScoreForUser(final Long userId);

	/**
	 * Retrieves a list of {@link LeaderBoardRow}s representing
	 the Leader Board of users and their total score.
	 * @return the leader board, sorted by highest score first.
	 */
	List<LeaderBoardRow> findFirst10();

	/**
	 * Retrieves all the ScoreCards for a given user,
	 identified by his user id.
	 * @param userId the id of the user
	 * @return a list containing all the ScoreCards for the
	given user, sorted by most recent.
	 */
	List<ScoreCard> findByUserIdOrderByScoreTimestampDesc(Long userId);

	void save(ScoreCard _scoreCard);
}
