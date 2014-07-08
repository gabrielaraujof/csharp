package br.ufs.dcomp.csharp.communication;

public interface ArduinoMessageConsumer {
	public void handleMessage(String message);
}
