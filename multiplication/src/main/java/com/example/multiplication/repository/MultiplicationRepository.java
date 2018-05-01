package com.example.multiplication.repository;

import com.example.multiplication.domain.Multiplication;

import java.util.Optional;

public interface MultiplicationRepository
{
	Optional<Multiplication> findFirstByFactorAAndFactorB(int _factorA, int _factorB);

	Multiplication save(Multiplication _multiplication);
}
