package com.example.gamification.service;

import com.example.gamification.domain.Badge;
import com.example.gamification.domain.BadgeCard;
import com.example.gamification.domain.GameStats;
import com.example.gamification.domain.ScoreCard;
import com.example.gamification.repository.BadgeCardRepository;
import com.example.gamification.repository.ScoreCardRepository;
import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.mockito.BDDMockito.given;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class GameServiceImplTest
{
	private GameService gameService;

	@Mock
	private ScoreCardRepository scoreCardRepository;

	@Mock
	private BadgeCardRepository badgeCardRepository;

	@Before
	public void setUp() throws Exception
	{
		gameService = new GameServiceImpl(scoreCardRepository, badgeCardRepository);
	}

	@Test
	public void processFirstCorrectAttemptTest() throws Exception
	{
		// given
		Long userId = 1L;
		Long attemptId = 1L;
		int totalScore = 10;
		ScoreCard scoreCard = new ScoreCard(userId, attemptId);
		boolean correct = false;
		given(scoreCardRepository.getTotalScoreForUser(userId))
				.willReturn(totalScore);
		given(scoreCardRepository.findByUserIdOrderByScoreTimestampDesc(userId))
				.willReturn(Collections.singletonList(scoreCard));
		given(badgeCardRepository.findByUserIdOrderByBadgeTimestampDesc(userId))
				.willReturn(Collections.emptyList());
		// when
		GameStats gameStats = gameService.newAttemptForUser(userId, attemptId, correct);

		// then
		assertThat(gameStats.getScore()).isEqualTo(totalScore);
		assertThat(gameStats.getBadges()).containsOnly(Badge.FIRST_WON);
	}

	@Test
	public void processCorrectAttemptForBronzeBadge() throws Exception
	{
		// given
		Long userId = 1L;
		Long attemptId = 30L;
		int totalScore = 100; //total ten correct attempt
		BadgeCard firstWonBadge = new BadgeCard(userId, Badge.FIRST_WON);

		boolean correct = true;
		given(scoreCardRepository.getTotalScoreForUser(userId))
				.willReturn(totalScore);

		given(scoreCardRepository.findByUserIdOrderByScoreTimestampDesc(userId))
				.willReturn(createNScoreCards(10, userId));

		given(badgeCardRepository.findByUserIdOrderByBadgeTimestampDesc(userId))
				.willReturn(Collections.singletonList(firstWonBadge));
		// when
		GameStats gameStats = gameService.newAttemptForUser(userId, attemptId, correct);

		// then
		assertThat(gameStats.getScore()).isEqualTo(ScoreCard.DEFAULT_SCORE);
		assertThat(gameStats.getBadges()).containsOnly(Badge.BRONZE_MULTIPLICATOR);
	}

	private List<ScoreCard> createNScoreCards(int _numberOfScoreCards
				, Long _userId)
	{
		return IntStream.range(0, _numberOfScoreCards)
				.mapToObj(i -> new ScoreCard(_userId, (long)i))
				.collect(Collectors.toList());
	}

}