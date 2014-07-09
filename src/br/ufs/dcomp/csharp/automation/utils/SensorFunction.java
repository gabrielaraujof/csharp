package br.ufs.dcomp.csharp.automation.utils;

public enum SensorFunction {
	GET_STATE(2), GET_VALUE(3);

	private final int code;

	SensorFunction(int c) {
		this.code = c;
	}

	public int getValue() {
		return this.code;
	}
}
