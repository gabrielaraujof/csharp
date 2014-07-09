package br.ufs.dcomp.csharp.automation.devices;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingDeque;

import br.ufs.dcomp.csharp.automation.Actuator;
import br.ufs.dcomp.csharp.automation.Device;
import br.ufs.dcomp.csharp.automation.RoomContainer;
import br.ufs.dcomp.csharp.automation.Sensor;
import br.ufs.dcomp.csharp.automation.utils.ActuatorFunction;
import br.ufs.dcomp.csharp.automation.utils.SensorFunction;
import br.ufs.dcomp.csharp.communication.ArduinoMessenger;

/**
 * 
 * @author gabriel
 *
 */
public class Lamp extends Device implements Actuator, Sensor {

	private Queue<String> messageBuffer;

	public Lamp(String nodeId, String name, RoomContainer room,
			ArduinoMessenger messenger) {
		super(nodeId, name, messenger, room);
		this.messageBuffer = new LinkedBlockingDeque<String>();
	}

	@Override
	public void act(ActuatorFunction function, int param) {
		String message;

		switch (function) {
		case SWITCH:
			if (param != Actuator.ON && param != Actuator.OFF)
				throw new IllegalArgumentException(
						"Only Actuator.ON and Actuator.OFF values are allowed.");
			// TODO Must ensure in advance that room and device's code has only
			// two
			// characters length. Otherwise, block the request.
			message = String.format("%s%s%s%s", this.getRoom().getId(),
					this.getId(), ActuatorFunction.SWITCH.getValue(), param);
			deliverCommand(message);
			break;
		case DIMMER:
			if (param < 0 && param >= 256)
				throw new IllegalArgumentException(
						"Only a value betweeb 0 and 255 is allowed.");
			// TODO Must ensure in advance that room and device's code has only
			// two
			// characters length. Otherwise, block the request.
			message = String.format("%s%s%s%s", this.getRoom().getId(),
					this.getId(), ActuatorFunction.DIMMER.getValue(), param);
			deliverCommand(message);
			break;
		default:
			throw new UnsupportedOperationException(
					"Unrecognized function on this device.");
		}
	}

	@Override
	public Object sense(SensorFunction function, int param) {
		String message;

		switch (function) {
		case GET_STATE:
			// TODO Must ensure in advance that room and device's code has only
			// two
			// characters length. Otherwise, block the request.
			message = String.format("%s%s%s", this.getRoom().getId(),
					this.getId(), SensorFunction.GET_STATE.getValue());
			deliverCommand(message, this);
			break;
		case GET_VALUE:
			// TODO Must ensure in advance that room and device's code has only
			// two
			// characters length. Otherwise, block the request.
			message = String.format("%s%s%s", this.getRoom().getId(),
					this.getId(), SensorFunction.GET_VALUE.getValue());
			deliverCommand(message, this);
			break;
		default:
			throw new UnsupportedOperationException(
					"Unrecognized function on this device.");
		}

		try {
			while (messageBuffer.isEmpty()) {
				synchronized (messageBuffer) {
					messageBuffer.wait();
				}
			}
			synchronized (messageBuffer) {
				// May decode the reply before returning it
				// return decodeMessage(messageBuffer.poll());
				return messageBuffer.poll();
			}
		} catch (InterruptedException e) {
			System.err.println(e.getMessage());
			return null;
		}
	}

	@Override
	public void handleMessage(String message) {
		synchronized (messageBuffer) {
			messageBuffer.add(message);
			notifyAll();
		}
	}
}
