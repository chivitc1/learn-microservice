package com.example.multiplication.infrastracture.dao;

import com.example.multiplication.domain.User;
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
public class UserDao implements UserRepository
{
	private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	private final DataSource dataSource;

	@Autowired
	public UserDao(NamedParameterJdbcTemplate _namedParameterJdbcTemplate, DataSource _dataSource)
	{
		namedParameterJdbcTemplate = _namedParameterJdbcTemplate;
		dataSource = _dataSource;
	}

	@Override
	public Optional<User> findByAlias(@NonNull String alias)
	{
		String sql = "SELECT id, alias FROM user WHERE alias = :alias";
		Map<String, Object> params = new HashMap<>();
		params.put("alias", alias);

		List<User> users = namedParameterJdbcTemplate.query(sql, params, new UserRowMapper());
		if (users.size() > 0) {
			return Optional.of(users.get(0));
		}
		return Optional.empty();
	}

	@Override
	public User save(@NonNull User _user)
	{
		Map<String, Object> params = new HashMap<>();
		if (_user.getId() != null) {
			String sqlUpdate = "UPDATE user SET alias = :alias WHERE id = :id";
			params.put("alias", _user.getAlias());
			params.put("id", _user.getId());
			namedParameterJdbcTemplate.update(sqlUpdate, params);

			return _user;
		}

		params.put("alias", _user.getAlias());

		SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
				.withTableName("user")
				.usingColumns(new String[] {"alias"})
				.usingGeneratedKeyColumns(new String[]{"id"});
		Long newId = simpleJdbcInsert.executeAndReturnKey(params).longValue();
		_user.setId(newId);

		return _user;
	}

	private static final class UserRowMapper implements RowMapper<User> {

		@Override
		public User mapRow(ResultSet rs, int rowNum) throws SQLException
		{
			Long userId = rs.getLong("id");
			String alias = rs.getString("alias");

			return new User(userId, alias);
		}
	}
}
