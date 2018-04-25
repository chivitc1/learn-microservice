package com.example.gamification.controller;

import com.example.gamification.domain.Badge;
import com.example.gamification.domain.GameStats;
import com.example.gamification.service.GameService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@RunWith(SpringRunner.class)
@WebMvcTest(UserStatsController.class)
public class UserStatsControllerTest
{
	@MockBean
	private GameService gameService;

	@Autowired
	private MockMvc mockMvc;

	private JacksonTester<GameStats> gameStatsJacksonTester;

	@Before
	public void setUp() throws Exception
	{
		JacksonTester.initFields(this, new ObjectMapper());
	}

	@Test
	public void getStatsForUserTest() throws Exception
	{
		// given
		Long userId = 1L;
		GameStats gameStats = new GameStats(userId, 100, Arrays.asList(Badge.FIRST_WON));
		given(gameService.retrieveStatsForUser(userId))
				.willReturn(gameStats);

		// when
		MockHttpServletResponse response = mockMvc.perform(
				get("/stats").param("userId", userId.toString())
					.accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();

		// that
		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
		assertThat(response.getContentAsString()).isEqualTo(
				gameStatsJacksonTester.write(gameStats).getJson()
		);
	}

}