package com.google.code.jskills.utils;

public enum VerificationMailEnum {

	DONE(0), ERRORUUID(1), ENDATTEMPTSCOUNT(2), ENDDATE(3);

	private final int value;

	private VerificationMailEnum(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}
}
