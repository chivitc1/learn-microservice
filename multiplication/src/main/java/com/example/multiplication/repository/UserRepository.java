package com.example.multiplication.repository;

import com.example.multiplication.domain.User;

import java.util.Optional;

public interface UserRepository
{
	Optional<User> findByAlias(final String alias);
	User save(User _user);
}
