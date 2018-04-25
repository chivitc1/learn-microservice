package com.example.gamification.service;

import com.example.gamification.domain.LeaderBoardRow;
import com.example.gamification.repository.ScoreCardRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class LeaderBoardServiceImpl implements LeaderBoardService
{
	@Autowired
	private ScoreCardRepository scoreCardRepository;

	public LeaderBoardServiceImpl(final ScoreCardRepository _scoreCardRepository)
	{
		this.scoreCardRepository = _scoreCardRepository;
	}

	/**
	 * Retrieves the current leader board with the top score
	 * users
	 * @return the users with the highest score
	 */
	@Override
	public List<LeaderBoardRow> getCurrentLeaderBoard()
	{
		return scoreCardRepository.findFirst10();
	}
}
