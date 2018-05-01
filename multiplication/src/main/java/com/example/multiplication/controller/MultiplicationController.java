package com.example.multiplication.controller;

import com.example.multiplication.domain.Multiplication;
import com.example.multiplication.service.MultiplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/multiplications")
@CrossOrigin(origins = "http://localhost:9090")
public class MultiplicationController
{
	private final MultiplicationService multiplicationService;

	@Autowired
	public MultiplicationController(final MultiplicationService _multiplicationService) {
		this.multiplicationService = _multiplicationService;
	}

	@GetMapping("/random")
	public Multiplication getRandomMultiplication() {
		return multiplicationService.createRandomMultiplication();
	}

}
