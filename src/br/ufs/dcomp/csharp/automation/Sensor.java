package br.ufs.dcomp.csharp.automation;

import br.ufs.dcomp.csharp.automation.utils.SensorFunction;
import br.ufs.dcomp.csharp.communication.ArduinoMessageConsumer;

public interface Sensor extends ArduinoMessageConsumer{
	
	Object sense(SensorFunction function, int param);
}
