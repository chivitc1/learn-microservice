package com.example.multiplication.infrastracture.dao;

import com.example.multiplication.domain.Multiplication;
import com.example.multiplication.repository.MultiplicationRepository;
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
public class MultiplicationDao implements MultiplicationRepository
{
	private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	private final DataSource dataSource;

	@Autowired
	public MultiplicationDao(NamedParameterJdbcTemplate _namedParameterJdbcTemplate,
							 DataSource _dataSource)
	{
		namedParameterJdbcTemplate = _namedParameterJdbcTemplate;
		dataSource = _dataSource;
	}
	@Override
	public Optional<Multiplication> findFirstByFactorAAndFactorB(int _factorA, int _factorB)
	{
		String sql = "SELECT factor_a, factor_b FROM multiplication " +
				"WHERE factor_a = :factorA AND factor_b = :factorB LIMIT 1";
		Map<String, Object> params = new HashMap<>();
		params.put("factorA", _factorA);
		params.put("factorB", _factorB);
		List<Multiplication> list =
				namedParameterJdbcTemplate.query(sql, params, new MultiplicationRowMapper());
		if (list.size() > 0) {
			return Optional.of(list.get(0));
		}
		return Optional.empty();
	}

	@Override
	public Multiplication save(Multiplication _multiplication)
	{
		Map<String, Object> params = new HashMap<>();
		if (_multiplication.getId() != null) {
			String updateSql = "UPDATE multiplication SET factor_a = :factor_a, " +
					"factor_b = :factor_b WHERE id = :id";
			params.put("factor_a", _multiplication.getFactorA());
			params.put("factor_b", _multiplication.getFactorB());

			namedParameterJdbcTemplate.update(updateSql, params);

			return _multiplication;
		}

		params.put("factor_a", _multiplication.getFactorA());
		params.put("factor_b", _multiplication.getFactorB());

		SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(dataSource);
		jdbcInsert.withTableName("multiplication")
				.usingColumns(new String[] {"factor_a", "factor_b"})
				.usingGeneratedKeyColumns(new String[] {"id"});
		Long newId = jdbcInsert.executeAndReturnKey(params).longValue();
		_multiplication.setId(newId);
		return _multiplication;
	}

	private static final class MultiplicationRowMapper implements RowMapper<Multiplication> {

		@Override
		public Multiplication mapRow(ResultSet rs, int rowNum) throws SQLException
		{
			Long multiplicationId = rs.getLong("id");
			int factorA = rs.getInt("fator_a");
			int factorB = rs.getInt("factor_b");
			return new Multiplication(multiplicationId, factorA, factorB);
		}
	}
}
