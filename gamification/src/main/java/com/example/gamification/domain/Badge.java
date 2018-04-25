package com.example.gamification.domain;

/**
 * Possible badges in the game
 */
public enum Badge
{
	// Badges depending on score
	BRONZE_MULTIPLICATOR("BRONZE_MULTIPLICATOR"),
	SILVER_MULTIPLICATOR("SILVER_MULTIPLICATOR"),
	GOLD_MULTIPLICATOR("GOLD_MULTIPLICATOR"),

	// Other badges won for different conditions
	FIRST_ATTEMPT("FIRST_ATTEMPT"),
	FIRST_WON("FIRST_WON"),
	LUCKY_MULTIPLICATION("LUCKY_MULTIPLICATION");

	private final String text;

	Badge(final String _text) { this.text = _text; }

	public String value() {
		return this.text;
	}

	@Override
	public String toString() { return this.text; }

}
