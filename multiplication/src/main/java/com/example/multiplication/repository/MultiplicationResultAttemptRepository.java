package com.example.multiplication.repository;

import com.example.multiplication.domain.MultiplicationResultAttempt;

import java.util.List;
import java.util.Optional;

public interface MultiplicationResultAttemptRepository
{
	/**
	 * @return the latest 5 attempts for a given user,
	identified by their alias.
	 */
	List<MultiplicationResultAttempt> findTop5ByUserAliasOrderByIdDesc(String userAlias);

	Optional<MultiplicationResultAttempt> getById(Long _resultId);

	MultiplicationResultAttempt save(MultiplicationResultAttempt _resultAttempt);
}
