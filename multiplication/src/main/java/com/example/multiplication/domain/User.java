package com.example.multiplication.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Store information to identify the user
 */
@Getter
@RequiredArgsConstructor
@ToString
@EqualsAndHashCode
@Entity
public final class User
{
	@Id
	@GeneratedValue
	@Column(name = "MULTIPLICATION_ID")
	private Long id;

	private final String alias;

	protected User() {
		alias = null;
	}
}
