package br.ufs.dcomp.csharp.automation.devices;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingDeque;

import br.ufs.dcomp.csharp.automation.Node;
import br.ufs.dcomp.csharp.automation.RoomContainer;
import br.ufs.dcomp.csharp.communication.ArduinoMessenger;

/**
 * 
 * @author gabriel
 *
 */
public abstract class Lamp extends Node {

	private RoomContainer room;

	private Queue<String> messageBuffer;

	private final int GET_STATE = 2;

	public enum Switch {
		ON(1), OFF(0);

		private final int code;

		Switch(int c) {
			this.code = c;
		}

		public int getValue() {
			return code;
		}
	}

	public Lamp(String nodeId, String name, RoomContainer room,
			ArduinoMessenger messenger) {
		super(nodeId, name, messenger);
		this.room = room;
		this.messageBuffer = new LinkedBlockingDeque<String>();
	}

	@Override
	public void handleMessage(String message) {
		synchronized (messageBuffer) {
			messageBuffer.add(message);
			notifyAll();
		}
	}

	public void turn(Switch state) {
		// Cria string usando o protocolo
		String message = room.getId() + this.getId() + state.getValue();
		deliverCommand(message, false);
	}

	public boolean isOn() throws InterruptedException {

		// Cria string usando o protocolo
		String message = room.getId() + this.getId() + GET_STATE;
		deliverCommand(message, true);

		while (messageBuffer.isEmpty()) {
			synchronized (messageBuffer) {
				messageBuffer.wait();
			}
		}

		synchronized (messageBuffer) {
			return decodeMessage(messageBuffer.poll());
		}
	}

	protected boolean decodeMessage(String message) {
		// TODO Auto-generated method stub
		System.out.println(message);
		return false;
	}

}
