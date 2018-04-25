package com.example.gamification.service;

import com.example.gamification.domain.Badge;
import com.example.gamification.domain.BadgeCard;
import com.example.gamification.domain.GameStats;
import com.example.gamification.domain.LeaderBoardRow;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class LeaderBoardServiceImplTest
{
	private LeaderBoardService leaderBoardService;

	@Mock
	private ScoreCardRepository scoreCardRepository;

	@Before
	public void setUp() throws Exception
	{
		leaderBoardService = new LeaderBoardServiceImpl(scoreCardRepository);
	}

	@Test
	public void getCurrentLeaderBoard() throws Exception
	{
		// given
		LeaderBoardRow leaderBoardRow1 = new LeaderBoardRow(1L, 500L);
		List<LeaderBoardRow> expectedLeaderBoardRowList = Collections.singletonList(leaderBoardRow1);

		boolean correct = true;
		given(scoreCardRepository.findFirst10())
				.willReturn(expectedLeaderBoardRowList);

		// when
		List<LeaderBoardRow> leaderBoardRowListResult = leaderBoardService.getCurrentLeaderBoard();

		// then
		assertThat(leaderBoardRowListResult).isEqualTo(expectedLeaderBoardRowList);
	}

}