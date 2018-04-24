package com.example.gamification.service;

import com.example.gamification.domain.GameStats;

public class GameServiceImpl implements GameService
{
	/**
	 * Process a new attempt from a given user.
	 * *
	 * @param userId    the user's unique id
	 * @param attemptId the attempt id, can be used to retrieve
	 *                  extra data if needed
	 * @param correct   indicates if the attempt was correct
	 *                  *
	 * @return a {@link GameStats} object containing the new
	 * score and badge cards obtained
	 */
	@Override
	public GameStats newAttemptForUser(Long userId, Long attemptId, boolean correct)
	{
		return null;
	}

	/**
	 * Gets the game statistics for a given user
	 * @param userId the user
	 * @return the total statistics for that user
	 */
	@Override
	public GameStats retrieveStatsForUser(Long userId)
	{
		return null;
	}
}