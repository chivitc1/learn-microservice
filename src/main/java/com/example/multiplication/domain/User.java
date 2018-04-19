package com.example.multiplication.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * Store information to identify the user
 */
@Getter
@RequiredArgsConstructor
@ToString
@EqualsAndHashCode
public final class User
{
	private final String alias;

	protected User() {
		alias = null;
	}
}
