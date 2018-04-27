package com.example.multiplication.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Store information to identify the user
 */
@Getter
@RequiredArgsConstructor
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "\"user\"")
public final class User
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private final String alias;

	protected User() {
		alias = null;
	}
}
