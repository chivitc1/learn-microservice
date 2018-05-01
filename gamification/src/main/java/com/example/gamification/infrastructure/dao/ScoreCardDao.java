package com.example.gamification.infrastructure.dao;

import com.example.gamification.domain.LeaderBoardRow;
import com.example.gamification.domain.ScoreCard;
import com.example.gamification.repository.ScoreCardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ScoreCardDao implements ScoreCardRepository
{
	private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	private final JdbcTemplate jdbcTemplate;

	@Autowired
	public ScoreCardDao(NamedParameterJdbcTemplate _namedParameterJdbcTemplate,
						JdbcTemplate _jdbcTemplate)
	{
		namedParameterJdbcTemplate = _namedParameterJdbcTemplate;
		jdbcTemplate = _jdbcTemplate;
	}


	@Override
	public Integer getTotalScoreForUser(Long userId)
	{
		String sql = "SELECT SUM(s.score) FROM score_card s " +
				"WHERE s.user_id = :userId GROUP BY s.user_id";
		Map<String, Object> params = new HashMap<>();
		params.put("userId", userId);

		return namedParameterJdbcTemplate.queryForObject(sql, params, Integer.class);
	}

	@Override
	public List<LeaderBoardRow> findFirst10()
	{
		String sql = "SELECT s.user_id, SUM(s.score) total_score FROM " +
				"score_card s GROUP BY s.user_id ORDER BY SUM(s.score) DESC " +
				"LIMIT 10";

		return jdbcTemplate.query(sql, new LeaderBoardRowMapper());
	}

	@Override
	public List<ScoreCard> findByUserIdOrderByScoreTimestampDesc(Long userId)
	{
		String sql = "SELECT id, user_id, attempt_id, score_timestamp, score " +
				"FROM score_card WHERE user_id = :userId " +
				"ORDER BY score_timestamp DESC";
		Map<String, Object> params = new HashMap<>();
		params.put("userId", userId);

		return namedParameterJdbcTemplate.queryForList(sql, params, ScoreCard.class);
	}

	@Override
	public void save(ScoreCard _scoreCard)
	{
		String sql = "INSERT INTO score_card(user_id, attempt_id, score_timestamp, score) " +
				"VALUES(:userId, :attemptId, :scoreTimestamp, :score)";
		Map<String, Object> params = new HashMap<>();
		params.put("userId", _scoreCard.getUserId());
		params.put("attempId", _scoreCard.getAttemptId());
		params.put("scoreTimestamp", _scoreCard.getScoreTimestamp());
		params.put("score", _scoreCard.getScore());
		namedParameterJdbcTemplate.update(sql, params);
	}

	private class LeaderBoardRowMapper implements RowMapper<LeaderBoardRow> {

		@Override
		public LeaderBoardRow mapRow(ResultSet rs, int rowNum) throws SQLException
		{
			Long userId = rs.getLong("user_id");
			Integer totalScore = rs.getInt("total_score");
			LeaderBoardRow row = new LeaderBoardRow(userId, totalScore);
			return row;
		}
	}
}
