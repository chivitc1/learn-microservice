package com.example.multiplication.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@EqualsAndHashCode
@RequiredArgsConstructor
public final class Multiplication
{
	private final int factorA;
	private final int factorB;

	private int result;

	public Multiplication(){
		this(0, 0);
	}

	@Override
	public String toString() {
		return "Multiplication{" +
				"factorA=" + factorA +
				", factorB=" + factorB +
				", result(A*B)=" + result +
				'}';
	}
}
