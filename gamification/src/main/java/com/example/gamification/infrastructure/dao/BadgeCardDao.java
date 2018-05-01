package com.example.gamification.infrastructure.dao;

import com.example.gamification.domain.Badge;
import com.example.gamification.domain.BadgeCard;
import com.example.gamification.repository.BadgeCardRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
public class BadgeCardDao implements BadgeCardRepository
{
	private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	private final DataSource dataSource;

	@Autowired
	public BadgeCardDao(NamedParameterJdbcTemplate _namedParameterJdbcTemplate,
						DataSource _dataSource)
	{
		namedParameterJdbcTemplate = _namedParameterJdbcTemplate;
		dataSource = _dataSource;
	}

	/**
	 * Retrieves all BadgeCards for a given user.
	 * @param userId the id of the user to look for BadgeCards
	 * @return the list of BadgeCards, sorted by most recent.
	 */
	@Override
	public List<BadgeCard> findByUserIdOrderByBadgeTimestampDesc(Long userId)
	{
		String sql = "SELECT id, user_id, badge_timestamp, badge " +
				"FROM badge_card WHERE user_id = :userId " +
				"ORDER BY badge_timestamp DESC";
		Map<String, Object> params = new HashMap<>();
		params.put("userId", userId);

		return namedParameterJdbcTemplate.query(sql, params, new BadgeCardRowMapper());
	}

	@Override
	public BadgeCard save(BadgeCard _badgeCard)
	{
		Map<String, Object> params = new HashMap<>();
		if (_badgeCard.getId() != null) {
			String sqlUpdate = "UPDATE badge_card SET user_id = :user_id, " +
					"badge_timestamp = :badge_timestamp, badge = :badge " +
					"WHERE id = :id";
			params.put("user_id", _badgeCard.getUserId());
			params.put("badge_timestamp", _badgeCard.getBadgeTimestamp());
			params.put("badge", _badgeCard.getBadge().toString());
			params.put("id", _badgeCard.getId());
			namedParameterJdbcTemplate.update(sqlUpdate, params);

			return _badgeCard;
		}

		params.put("user_id", _badgeCard.getUserId());
		params.put("badge_timestamp", _badgeCard.getBadgeTimestamp());
		params.put("badge", _badgeCard.getBadge().toString());

		SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
				.withTableName("badge_card")
				.usingColumns(new String[] {"user_id", "badge_timestamp", "badge" })
				.usingGeneratedKeyColumns(new String[]{"id"});
		Long newId = simpleJdbcInsert.executeAndReturnKey(params).longValue();
		_badgeCard.setId(newId);

		return _badgeCard;
	}

	private class BadgeCardRowMapper implements RowMapper<BadgeCard> {

		@Override
		public BadgeCard mapRow(ResultSet rs, int rowNum) throws SQLException
		{
			Long badgeId = rs.getLong("id");
			Long userId = rs.getLong("user_id");
			Timestamp badgeTimestamp = rs.getTimestamp("badge_timestamp");
			String badge = rs.getString("badge");
			BadgeCard badgeCard = new BadgeCard(badgeId, userId, badgeTimestamp, Badge.valueOf(badge));
			return badgeCard;
		}
	}
}
