package com.example.gamification.service;

import com.example.gamification.domain.Badge;
import com.example.gamification.domain.BadgeCard;
import com.example.gamification.domain.GameStats;
import com.example.gamification.domain.ScoreCard;
import com.example.gamification.repository.BadgeCardRepository;
import com.example.gamification.repository.ScoreCardRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

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
	public void testRetrieveStatsForUser() throws Exception {
		// given
		Long userId = 1L;
		Long attemptId = 10L;
		int totalScore = 10;
		List<ScoreCard> scoreCardList = createNScoreCards(1, userId);

		BadgeCard firstWonBadge = new BadgeCard(userId, Badge.FIRST_WON);
		given(scoreCardRepository.getTotalScoreForUser(userId))
				.willReturn(totalScore);
		given(scoreCardRepository.findByUserIdOrderByScoreTimestampDesc(userId))
				.willReturn(scoreCardList);
		given(badgeCardRepository.findByUserIdOrderByBadgeTimestampDesc(userId))
				.willReturn(Arrays.asList(firstWonBadge));
		// when
		GameStats gameStats = gameService.retrieveStatsForUser(userId);

		// then
		assertThat(gameStats.getBadges()).containsOnly(Badge.FIRST_WON);
		assertThat(gameStats.getScore()).isEqualTo(totalScore);
	}

	@Test
	public void processFirstCorrectAttemptTest() throws Exception
	{
		// given
		Long userId = 1L;
		Long attemptId = 1L;
		int totalScore = 10;
		ScoreCard scoreCard = new ScoreCard(userId, attemptId);
		boolean correct = true;
		given(scoreCardRepository.getTotalScoreForUser(userId))
				.willReturn(totalScore);
		given(scoreCardRepository.findByUserIdOrderByScoreTimestampDesc(userId))
				.willReturn(Collections.singletonList(scoreCard));
		given(badgeCardRepository.findByUserIdOrderByBadgeTimestampDesc(userId))
				.willReturn(Collections.emptyList());
		// when
		GameStats gameStats = gameService.newAttemptForUser(userId, attemptId, correct);

		// then
		assertThat(gameStats.getBadges()).containsOnly(Badge.FIRST_WON);
	}

	@Test
	public void processWrongAttemptTest() throws Exception
	{
		// given
		Long userId = 1L;
		Long attemptId = 10L;
		int totalScore = 10;
		List<ScoreCard> scoreCardList = createNScoreCards(1, userId);
		ScoreCard scoreCard = new ScoreCard(userId, attemptId);
		scoreCardList.add(scoreCard);

		BadgeCard firstWonBadge = new BadgeCard(userId, Badge.FIRST_WON);
		boolean correct = false;
		given(scoreCardRepository.getTotalScoreForUser(userId))
				.willReturn(totalScore);
		given(scoreCardRepository.findByUserIdOrderByScoreTimestampDesc(userId))
				.willReturn(scoreCardList);
		given(badgeCardRepository.findByUserIdOrderByBadgeTimestampDesc(userId))
				.willReturn(Arrays.asList(firstWonBadge));
		// when
		GameStats gameStats = gameService.newAttemptForUser(userId, attemptId, correct);

		// then
		assertThat(gameStats.getBadges()).containsOnly(Badge.FIRST_WON);
		assertThat(gameStats.getScore()).isEqualTo(totalScore);
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
		assertThat(gameStats.getBadges()).containsOnly(Badge.BRONZE_MULTIPLICATOR);
	}

	@Test
	public void processCorrectAttemptForSilverBadge() throws Exception
	{
		// given
		Long userId = 1L;
		Long attemptId = 30L;
		int totalScore = 250; //total 25 correct attempt
		BadgeCard firstWonBadge = new BadgeCard(userId, Badge.FIRST_WON);
		BadgeCard bronzeBadge = new BadgeCard(userId, Badge.BRONZE_MULTIPLICATOR);

		boolean correct = true;
		given(scoreCardRepository.getTotalScoreForUser(userId))
				.willReturn(totalScore);

		given(scoreCardRepository.findByUserIdOrderByScoreTimestampDesc(userId))
				.willReturn(createNScoreCards(25, userId));

		given(badgeCardRepository.findByUserIdOrderByBadgeTimestampDesc(userId))
				.willReturn(Arrays.asList(firstWonBadge, bronzeBadge));
		// when
		GameStats gameStats = gameService.newAttemptForUser(userId, attemptId, correct);

		// then
		assertThat(gameStats.getBadges()).containsOnly(Badge.SILVER_MULTIPLICATOR);
	}

	@Test
	public void processCorrectAttemptForGoldBadge() throws Exception
	{
		// given
		Long userId = 1L;
		Long attemptId = 30L;
		int totalScore = 500; //total 50 correct attempt
		BadgeCard firstWonBadge = new BadgeCard(userId, Badge.FIRST_WON);
		BadgeCard bronzeBadge = new BadgeCard(userId, Badge.BRONZE_MULTIPLICATOR);
		BadgeCard silverBadge = new BadgeCard(userId, Badge.SILVER_MULTIPLICATOR);

		boolean correct = true;
		given(scoreCardRepository.getTotalScoreForUser(userId))
				.willReturn(totalScore);

		given(scoreCardRepository.findByUserIdOrderByScoreTimestampDesc(userId))
				.willReturn(createNScoreCards(50, userId));

		given(badgeCardRepository.findByUserIdOrderByBadgeTimestampDesc(userId))
				.willReturn(Arrays.asList(firstWonBadge, bronzeBadge, silverBadge));
		// when
		GameStats gameStats = gameService.newAttemptForUser(userId, attemptId, correct);

		// then
		assertThat(gameStats.getBadges()).containsOnly(Badge.GOLD_MULTIPLICATOR);
	}

	@Test
	public void processCorrectAttemptForLuckyBadge() throws Exception
	{
		// given
		Long userId = 1L;
		Long attemptId = 30L;
		int totalScore = 10; //total 50 correct attempt
		BadgeCard firstWonBadge = new BadgeCard(userId, Badge.FIRST_WON);
		BadgeCard bronzeBadge = new BadgeCard(userId, Badge.LUCKY_MULTIPLICATION);

		boolean correct = false;
		given(scoreCardRepository.getTotalScoreForUser(userId))
				.willReturn(totalScore);

		given(scoreCardRepository.findByUserIdOrderByScoreTimestampDesc(userId))
				.willReturn(createNScoreCards(1, userId));

		given(badgeCardRepository.findByUserIdOrderByBadgeTimestampDesc(userId))
				.willReturn(Arrays.asList(firstWonBadge));
		// when
		GameStats gameStats = gameService.newAttemptForUser(userId, attemptId, correct);

		// then
		assertThat(gameStats.getBadges()).containsOnly(Badge.LUCKY_MULTIPLICATION);
	}

	private List<ScoreCard> createNScoreCards(int _numberOfScoreCards
				, Long _userId)
	{
		return IntStream.range(0, _numberOfScoreCards)
				.mapToObj(i -> new ScoreCard(_userId, (long)i))
				.collect(Collectors.toList());
	}
}