package com.example.multiplication.domain;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Store information to identify the user
 */
@RequiredArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public final class User
{
	@Setter
	private Long id;

	private final String alias;

	protected User() {
		id = null;
		alias = null;
	}
}
