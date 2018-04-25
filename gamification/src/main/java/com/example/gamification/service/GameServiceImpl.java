package com.example.gamification.service;

import com.example.gamification.domain.Badge;
import com.example.gamification.domain.BadgeCard;
import com.example.gamification.domain.GameStats;
import com.example.gamification.domain.ScoreCard;
import com.example.gamification.repository.BadgeCardRepository;
import com.example.gamification.repository.ScoreCardRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
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
	public GameStats newAttemptForUser(final Long userId, final Long attemptId, final boolean correct)
	{
		ScoreCard scoreCard = new ScoreCard(userId, attemptId);
		log.info("User with id {} score {} points for attempt id {}",
				userId, scoreCard.getScore(), attemptId);
		scoreCardRepository.save(scoreCard);
		if (correct) {
			List<BadgeCard> badgeCards = processForBadges(userId, attemptId);

			return new GameStats(userId, scoreCard.getScore(),
					badgeCards.stream().map(BadgeCard::getBadge)
					.collect(Collectors.toList()));
		} else {
			List<BadgeCard> badgeCards = badgeCardRepository.findByUserIdOrderByBadgeTimestampDesc(userId);
			return new GameStats(userId, scoreCard.getScore(),
					badgeCards.stream().map(BadgeCard::getBadge)
							.collect(Collectors.toList()));
		}
	}

	private List<BadgeCard> processForBadges(final Long _userId, final Long _attemptId)
	{
		List<BadgeCard> badgeCards = new ArrayList<>();
		int totalScore = scoreCardRepository.getTotalScoreForUser(_userId);
		log.info("New score for user {} is {}", _userId, totalScore);
		List<ScoreCard> scoreCardList =
				scoreCardRepository.findByUserIdOrderByScoreTimestampDesc(_userId);
		List<BadgeCard> badgeCardList =
				badgeCardRepository.findByUserIdOrderByBadgeTimestampDesc(_userId);

		// Badges depending on score
		checkAndGiveBadgeBasedOnScore(badgeCardList,
				Badge.BRONZE_MULTIPLICATOR, totalScore, 100, _userId)
				.ifPresent(badgeCards::add);
		checkAndGiveBadgeBasedOnScore(badgeCardList,
				Badge.SILVER_MULTIPLICATOR, totalScore, 250, _userId)
				.ifPresent(badgeCards::add);
		checkAndGiveBadgeBasedOnScore(badgeCardList,
				Badge.GOLD_MULTIPLICATOR, totalScore, 500, _userId)
				.ifPresent(badgeCards::add);

		// First won badge
		if(scoreCardList.size() == 1 &&
				!containsBadge(badgeCardList, Badge.FIRST_WON)) {
			BadgeCard firstWonBadge = giveBadgeToUser(Badge.FIRST_WON, _userId);
			badgeCards.add(firstWonBadge);
		}

		return badgeCards;
	}

	/**
	 * Checks if the passed list of badges includes the one
	 * being checked
	 */
	private boolean containsBadge(List<BadgeCard> _badgeCardList, Badge _badge)
	{
		return _badgeCardList.stream().anyMatch(b -> b.getBadge().equals(_badge));
	}

	/**
	 * Convenience method to check the current score against
	 * the different thresholds to gain badges.
	 * It also assigns badge to user if the conditions are met.
	 */
	private Optional<BadgeCard> checkAndGiveBadgeBasedOnScore(final List<BadgeCard> _badgeCardList,
															  final Badge _badge,
															  final int _score,
															  final int _scoreThreshold,
															  final Long _userId)
	{
		if (_score >= _scoreThreshold && !containsBadge(_badgeCardList, _badge)) {
			return Optional.of(giveBadgeToUser(_badge, _userId));
		}
		
		return Optional.empty();
	}

	/**
	 * Assigns a new badge to the given user
	 */
	private BadgeCard giveBadgeToUser(Badge _badge, Long _userId)
	{
		BadgeCard badgeCard = new BadgeCard(_userId, _badge);
		badgeCardRepository.save(badgeCard);
		log.info("User with id {} won a new badge: {}", _userId, _badge);

		return badgeCard;
	}

	/**
	 * Gets the game statistics for a given user
	 * @param userId the user
	 * @return the total statistics for that user
	 */
	@Override
	public GameStats retrieveStatsForUser(final Long userId)
	{
		int score = scoreCardRepository.getTotalScoreForUser(userId);
		List<BadgeCard> badgeCards = badgeCardRepository.findByUserIdOrderByBadgeTimestampDesc(userId);

		return new GameStats(userId, score, badgeCards.stream()
			.map(BadgeCard::getBadge).collect(Collectors.toList()));
	}
}
