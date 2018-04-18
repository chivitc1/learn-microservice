package com.example.multiplication.domain;

import lombok.Getter;

@Getter
public class Multiplication
{
	private int factorA;
	private int factorB;

	private int result;

	public Multiplication(int _factorA, int _factorB){
		factorA = _factorA;
		factorB = _factorB;
		result = factorA * factorB;
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
