package br.ufs.dcomp.csharp.automation.utils;

public enum ActuatorFunction {
	SWITCH(0), DIMMER(1);

	private final int code;

	ActuatorFunction(int c) {
		this.code = c;
	}

	public int getValue() {
		return this.code;
	}
}
