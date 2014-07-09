package br.ufs.dcomp.csharp.automation;

import br.ufs.dcomp.csharp.automation.utils.ActuatorFunction;

public interface Actuator {
	
	public static final int ON = 1;
	public static final int OFF = 0;
	
	void act(ActuatorFunction function, int param);
}
