package com.example.multiplication.infrastracture.dao;

import com.example.multiplication.domain.Multiplication;
import com.example.multiplication.domain.MultiplicationResultAttempt;
import com.example.multiplication.domain.User;
import com.example.multiplication.repository.MultiplicationRepository;
import com.example.multiplication.repository.MultiplicationResultAttemptRepository;
import com.example.multiplication.repository.UserRepository;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class MultiplicationResultAttemptDao implements MultiplicationResultAttemptRepository
{
	private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	private final UserRepository userRepository;
	private final MultiplicationRepository multiplicationRepository;
	private final DataSource dataSource;

	@Autowired
	public MultiplicationResultAttemptDao(NamedParameterJdbcTemplate _namedParameterJdbcTemplate,
										  UserRepository _userRepository,
										  MultiplicationRepository _multiplicationRepository,
										  DataSource _dataSource)
	{
		namedParameterJdbcTemplate = _namedParameterJdbcTemplate;
		userRepository = _userRepository;
		multiplicationRepository = _multiplicationRepository;
		dataSource = _dataSource;
	}

	@Override
	public List<MultiplicationResultAttempt> findTop5ByUserAliasOrderByIdDesc(@NonNull String userAlias)
	{
		String sql = "SELECT s.id, s.user_id, s.multiplication_id, s.result_attempt, s.correct, " +
				"t.alias, p.factor_a, p.factor_b " +
				"FROM multiplication_result_attempt s " +
				"INNER JOIN user t ON s.user_id = t.id " +
				"INNER JOIN multiplication p ON s.multiplication_id = p.id " +
				"WHERE t.alias = :alias " +
				"ORDER BY s.id LIMIT 5";
		Map<String, Object> params = new HashMap<>();
		params.put("alias", userAlias);
		return namedParameterJdbcTemplate.query(sql, params, new MultiplicationResultAttemptRowMapper());
	}

	/**
	 * This method not taking care of transaction => other service using this method must use @Transaction
	 * @param _resultId
	 * @return
	 */
	@Override
	public Optional<MultiplicationResultAttempt> getById(Long _resultId)
	{
		String sql = "SELECT s.id, s.user_id, s.multiplication_id, s.result_attempt, s.correct " +
				"t.alias, p.factor_a, p.factor_b " +
				"FROM multiplication_result_attempt s " +
				"INNER JOIN user t ON s.user_id = t.id" +
				"WHERE s.id = :id";
		Map<String, Object> params = new HashMap<>();
		params.put("id", _resultId);
		List<MultiplicationResultAttempt> list =
				namedParameterJdbcTemplate.query(sql, params, new MultiplicationResultAttemptRowMapper());
		if (list.size() > 0) {
			return Optional.of(list.get(0));
		}
		return Optional.empty();
	}

	@Override
	public MultiplicationResultAttempt save(MultiplicationResultAttempt _resultAttempt)
	{
		Map<String, Object> params = new HashMap<>();
		if (_resultAttempt.getId() != null) {
			User user = userRepository.save(_resultAttempt.getUser());
			Multiplication multiplication =
					multiplicationRepository.save(_resultAttempt.getMultiplication());
			String sqlUpdate = "UPDATE multiplication_result_attempt SET user_id = :userId, " +
					"multiplication_id = :multiplicationId, result_attempt = :resultAttempt, " +
					"correct = :correct " +
					"WHERE id = :id";
			params.put("userId", _resultAttempt.getId());
			params.put("multiplicationId", multiplication.getId());
			params.put("resultAttempt", _resultAttempt.getResultAttempt());
			params.put("correct", _resultAttempt.isCorrect());
			params.put("id", _resultAttempt.getId());
			namedParameterJdbcTemplate.update(sqlUpdate, params);
			_resultAttempt.setUser(user);
			_resultAttempt.setMultiplication(multiplication);
			return _resultAttempt;
		}

		User user = userRepository.save(_resultAttempt.getUser());
		Multiplication multiplication =
				multiplicationRepository.save(_resultAttempt.getMultiplication());

		params.put("user_id", user.getId());
		params.put("multiplication_id", multiplication.getId());
		params.put("result_attempt", _resultAttempt.getResultAttempt());
		params.put("correct", _resultAttempt.isCorrect());

		SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(dataSource);
		Long newId = jdbcInsert.withTableName("multiplication_result_attempt")
			.usingColumns("user_id", "multiplication_id", "result_attempt", "correct")

				.usingGeneratedKeyColumns(new String[]{"id"})
		.executeAndReturnKey(params).longValue();

		_resultAttempt.setId(newId);
		_resultAttempt.setUser(user);
		_resultAttempt.setMultiplication(multiplication);

		return _resultAttempt;
	}

	private static class MultiplicationResultAttemptRowMapper implements RowMapper<MultiplicationResultAttempt> {

		@Override
		public MultiplicationResultAttempt mapRow(ResultSet rs, int rowNum) throws SQLException
		{
			Long id = rs.getLong("id");
			Long userId = rs.getLong("user_id");
			Long multiplicationId = rs.getLong("multiplication_id");
			Integer resultAttempt = rs.getInt("result_attempt");
			Boolean correct = rs.getBoolean("correct");
			String alias = rs.getString("alias");
			Integer factorA = rs.getInt("factor_a");
			Integer factorB = rs.getInt("factor_b");

			User user = new User(userId, alias);
			Multiplication multiplication = new Multiplication(multiplicationId, factorA, factorB);
			MultiplicationResultAttempt attempt = new MultiplicationResultAttempt(id, user, multiplication, resultAttempt, correct);
			return attempt;
		}
	}
}
