package com.example.multiplication.controller;

import com.example.multiplication.domain.Multiplication;
import com.example.multiplication.domain.MultiplicationResultAttempt;
import com.example.multiplication.domain.User;
import com.example.multiplication.service.MultiplicationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.util.Lists;
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

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@RunWith(SpringRunner.class)
@WebMvcTest(MultiplicationResultAttemptController.class)
public class MultiplicationResultAttemptControllerTest
{
	@MockBean
	private MultiplicationService multiplicationService;

	@Autowired
	private MockMvc mockMvc;

	private JacksonTester<MultiplicationResultAttempt> jsonAttemptResult;

	private JacksonTester<List<MultiplicationResultAttempt>> jsonResultAttemptList;

	@Before
	public void setUp() throws Exception
	{
		JacksonTester.initFields(this, new ObjectMapper());
	}

	@Test
	public void postResultReturnCorrect() throws Exception
	{
		genericParameterizedTest(true);
	}

	@Test
	public void postResultReturnNotCorrect() throws Exception
	{
		genericParameterizedTest(false);
	}

	private void genericParameterizedTest(final boolean _correct) throws Exception
	{
		User user = new User("chinv");
		Multiplication multiplication = new Multiplication(50, 60);
		int resultAttempt = 3000;
		given(multiplicationService.checkAttempt(any(MultiplicationResultAttempt.class)))
				.willReturn(new MultiplicationResultAttempt(null, user, multiplication, resultAttempt, _correct));


		MultiplicationResultAttempt attempt =
				new MultiplicationResultAttempt(null, user, multiplication, resultAttempt, _correct);

		// when
		MockHttpServletResponse response = mockMvc.perform(
				post("/results")
						.contentType(MediaType.APPLICATION_JSON)
						.content(jsonAttemptResult.write(attempt).getJson()))
				.andReturn().getResponse();

		// verify
		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
		assertThat(response.getContentAsString())
				.isEqualTo(
						jsonAttemptResult.write(new MultiplicationResultAttempt(null, attempt.getUser(),
								attempt.getMultiplication(),
								attempt.getResultAttempt(),
								_correct))
								.getJson());
	}

	@Test
	public void getUserStats() throws Exception {
		// given
		User user = new User("chinv");
		Multiplication multiplication = new Multiplication
				(50, 70);
		MultiplicationResultAttempt attempt = new
				MultiplicationResultAttempt(null,
				user, multiplication, 3500, true);
		List<MultiplicationResultAttempt> recentAttempts =
				Lists.newArrayList(attempt, attempt);
		given(multiplicationService.getStatsForUser("chinv"))
				.willReturn(recentAttempts);

		// when
		MockHttpServletResponse response = mockMvc.perform(
				get("/results").param("alias", "chinv")
			).andReturn().getResponse();

		// verify
		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
		assertThat(response.getContentAsString())
				.isEqualTo(jsonResultAttemptList.write(recentAttempts).getJson());

	}

	@Test
	public void getResultByIdTest() throws Exception {
		// given
		Long resultId = 1L;
		Multiplication multiplication = new Multiplication(30, 40);
		User user = new User("chinv");
		Optional<MultiplicationResultAttempt> resultAttempt =
				Optional.of(new MultiplicationResultAttempt(null,
						user, multiplication, 121, false));
		assertThat(resultAttempt.isPresent()).isTrue();
		given(multiplicationService.getMultiplicationResult(resultId))
				.willReturn(resultAttempt);
		// when
		MockHttpServletResponse response = mockMvc.perform(
				get("/results/" + resultId)
					.accept(MediaType.APPLICATION_JSON))
				.andReturn().getResponse();

		// then
		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
		assertThat(response.getContentAsString())
				.isEqualTo(
						jsonAttemptResult.write(resultAttempt.get()).getJson()
				);
	}
}