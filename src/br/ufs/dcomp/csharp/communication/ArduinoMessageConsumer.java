package br.ufs.dcomp.csharp.communication;

public interface ArduinoMessageConsumer {
	void handleMessage(String message);
}
