package com.example.gamification.infrastructure.dao;

import com.example.gamification.domain.LeaderBoardRow;
import com.example.gamification.domain.ScoreCard;
import com.example.gamification.repository.ScoreCardRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@Slf4j
public class ScoreCardDao implements ScoreCardRepository
{
	private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	private final JdbcTemplate jdbcTemplate;
	private final DataSource dataSource;

	@Autowired
	public ScoreCardDao(NamedParameterJdbcTemplate _namedParameterJdbcTemplate,
						JdbcTemplate _jdbcTemplate,
						DataSource _dataSource)
	{
		namedParameterJdbcTemplate = _namedParameterJdbcTemplate;
		jdbcTemplate = _jdbcTemplate;
		dataSource = _dataSource;
	}


	@Override
	public Integer getTotalScoreForUser(Long userId)
	{
		String sql = "SELECT SUM(s.score) FROM score_card s " +
				"WHERE s.user_id = :userId GROUP BY s.user_id";
		Map<String, Object> params = new HashMap<>();
		params.put("userId", userId);
		Integer score;
		try {
			score = namedParameterJdbcTemplate.queryForObject(sql, params, Integer.class);
			return score;
		} catch (EmptyResultDataAccessException _e) {
			return 0;
		}
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

		List<ScoreCard> list = namedParameterJdbcTemplate.query(sql, params, new ScoreCardRowMapper());
		return list;
	}

	@Override
	public ScoreCard save(ScoreCard _scoreCard)
	{
		Map<String, Object> params = new HashMap<>();
		if (_scoreCard.getId() != null) {
			String sqlUpdate = "UPDATE score_card SET user_id = :user_id, attempt_id = :attempt_id," +
					" score_timestamp = :score_timestamp, score = :score " +
					"WHERE id = :id";
			params.put("user_id", _scoreCard.getUserId());
			params.put("attempt_id", _scoreCard.getAttemptId());
			params.put("score_timestamp", _scoreCard.getScoreTimestamp());
			params.put("score", _scoreCard.getScore());
			params.put("id", _scoreCard.getId());
			namedParameterJdbcTemplate.update(sqlUpdate, params);

			return _scoreCard;
		}

		params.put("user_id", _scoreCard.getUserId());
		params.put("attempt_id", _scoreCard.getAttemptId());
		params.put("score_timestamp", _scoreCard.getScoreTimestamp());
		params.put("score", _scoreCard.getScore());

		SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
				.withTableName("score_card")
				.usingColumns(new String[] {"user_id", "attempt_id", "score_timestamp", "score"})
				.usingGeneratedKeyColumns(new String[]{"id"});
		Long newId = simpleJdbcInsert.executeAndReturnKey(params).longValue();
		_scoreCard.setId(newId);

		return _scoreCard;
	}

	private static final class LeaderBoardRowMapper implements RowMapper<LeaderBoardRow> {

		@Override
		public LeaderBoardRow mapRow(ResultSet rs, int rowNum) throws SQLException
		{
			Long userId = rs.getLong("user_id");
			Integer totalScore = rs.getInt("total_score");
			LeaderBoardRow row = new LeaderBoardRow(userId, totalScore);
			return row;
		}
	}

	private static final class ScoreCardRowMapper implements RowMapper<ScoreCard> {

		@Override
		public ScoreCard mapRow(ResultSet rs, int rowNum) throws SQLException
		{
			Long id = rs.getLong("id");
			Long userId = rs.getLong("user_id");
			Long attemptId = rs.getLong("attempt_id");
			Timestamp scoreTimestamp = rs.getTimestamp("score_timestamp");
			Integer score = rs.getInt("score");
			ScoreCard row = new ScoreCard(id, userId, attemptId, scoreTimestamp, score);
			return row;
		}
	}
}
