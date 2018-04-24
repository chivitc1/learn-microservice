package com.example.gamification.service;

import com.example.gamification.domain.Badge;
import com.example.gamification.domain.BadgeCard;
import com.example.gamification.domain.GameStats;
import com.example.gamification.domain.ScoreCard;
import com.example.gamification.repository.BadgeCardRepository;
import com.example.gamification.repository.ScoreCardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GameServiceImpl implements GameService
{
	@Autowired
	private ScoreCardRepository scoreCardRepository;

	@Autowired
	private BadgeCardRepository badgeCardRepository;

	public GameServiceImpl(ScoreCardRepository _scoreCardRepository,
						   BadgeCardRepository _badgeCardRepository)
	{
		this.scoreCardRepository = _scoreCardRepository;
		this.badgeCardRepository = _badgeCardRepository;
	}

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
		int totalScore = scoreCardRepository.getTotalScoreForUser(userId);
		List<Badge> badgeList = new ArrayList<>();
		if (totalScore == ScoreCard.DEFAULT_SCORE) {
			badgeList.add(Badge.FIRST_WON);
		}

		return new GameStats(userId, ScoreCard.DEFAULT_SCORE, badgeList);
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
