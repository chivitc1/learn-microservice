package com.example.multiplication.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@EqualsAndHashCode
@RequiredArgsConstructor
@ToString
public final class Multiplication
{
	private final int factorA;
	private final int factorB;

	public Multiplication(){

		this(0, 0);
	}
}
