package com.example.multiplication.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@EqualsAndHashCode
@RequiredArgsConstructor
@ToString
@Entity
public final class Multiplication
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "factor_a")
	private final int factorA;

	@Column(name = "factor_b")
	private final int factorB;

	public Multiplication(){

		this(0, 0);
	}
}
