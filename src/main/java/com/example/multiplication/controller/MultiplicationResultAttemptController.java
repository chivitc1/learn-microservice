package com.example.multiplication.controller;

import com.example.multiplication.domain.MultiplicationResultAttempt;
import com.example.multiplication.service.MultiplicationService;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/results")
public final class MultiplicationResultAttemptController
{
	private final MultiplicationService multiplicationService;

	@Autowired
	MultiplicationResultAttemptController(final
										  MultiplicationService multiplicationService) {
		this.multiplicationService = multiplicationService;
	}

	// TODO: implement POST
	@PostMapping
	ResponseEntity<ResultResponse> postResult(
			@RequestBody MultiplicationResultAttempt _resultAttempt) {
		return ResponseEntity.ok(
				new ResultResponse(multiplicationService.checkAttempt(_resultAttempt))
		);
	}

	@RequiredArgsConstructor
	@NoArgsConstructor(force = true)
	@Getter
	public static final class ResultResponse {
		private final boolean correct;
	}
}
