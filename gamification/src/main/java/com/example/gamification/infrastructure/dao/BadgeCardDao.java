package com.example.gamification.infrastructure.dao;

import com.example.gamification.domain.Badge;
import com.example.gamification.domain.BadgeCard;
import com.example.gamification.repository.BadgeCardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

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

	@Autowired
	public BadgeCardDao(NamedParameterJdbcTemplate _namedParameterJdbcTemplate)
	{
		namedParameterJdbcTemplate = _namedParameterJdbcTemplate;
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
	public void save(BadgeCard _badgeCard)
	{
		String sql = "INSERT INTO badge_card(user_id, badge_timestamp, badge) values(:userId, :badgeTimestamp, :badge)";
		Map<String, Object> params = new HashMap<>();
		params.put("userId", _badgeCard.getUserId());
		params.put("badgeTimestamp", _badgeCard.getBadgeTimestamp());
		params.put("badge", _badgeCard.getBadge());
		namedParameterJdbcTemplate.update(sql, params);
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
