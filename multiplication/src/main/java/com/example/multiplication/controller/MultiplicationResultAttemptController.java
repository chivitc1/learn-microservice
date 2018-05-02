package com.example.multiplication.controller;

import com.example.multiplication.domain.MultiplicationResultAttempt;
import com.example.multiplication.service.MultiplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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

		MultiplicationResultAttempt attemptChecked = multiplicationService.checkAttempt(_resultAttempt);
		return ResponseEntity.ok(attemptChecked);
	}

	@GetMapping
	ResponseEntity<List<MultiplicationResultAttempt>>
		getStatistics(@RequestParam("alias") String alias) {

		List<MultiplicationResultAttempt> list = multiplicationService.getStatsForUser(alias);
		if (list.size() > 0) {
			return ResponseEntity.ok(list);
		}
		return new ResponseEntity<List<MultiplicationResultAttempt>>(HttpStatus.NOT_FOUND);
	}

	@GetMapping("/{resultId}")
	public ResponseEntity<MultiplicationResultAttempt> getResultById(@PathVariable("resultId") Long _resultId) {
		if (multiplicationService.getMultiplicationResult(_resultId).isPresent()) {
			return ResponseEntity.ok(multiplicationService.getMultiplicationResult(_resultId).get());
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
}
