package com.example.multiplication.domain;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@EqualsAndHashCode
@RequiredArgsConstructor
@ToString
@AllArgsConstructor
public final class Multiplication
{
	@Setter
	private Long id;

	private final int factorA;

	private final int factorB;

	public Multiplication(){

		this(null, 0, 0);
	}
}
