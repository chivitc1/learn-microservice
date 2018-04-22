package com.example.multiplication.controller;

import com.example.multiplication.domain.MultiplicationResultAttempt;
import com.example.multiplication.service.MultiplicationService;
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
	ResponseEntity<MultiplicationResultAttempt> postResult(
			@RequestBody MultiplicationResultAttempt _resultAttempt) {

		boolean isCorrect = multiplicationService.checkAttempt(_resultAttempt);
		MultiplicationResultAttempt attemptCopy =
				new MultiplicationResultAttempt(_resultAttempt.getUser(),
						_resultAttempt.getMultiplication(),
						_resultAttempt.getResultAttempt(),
						isCorrect);
		return ResponseEntity.ok(attemptCopy);
	}
}
