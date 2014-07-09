package br.ufs.dcomp.csharp.automation;

import br.ufs.dcomp.csharp.communication.ArduinoMessageConsumer;
import br.ufs.dcomp.csharp.communication.ArduinoMessenger;

/**
 * 
 * @author gabriel
 *
 */
public abstract class Device {

	private String id;
	private String displayName;
	private ArduinoMessenger serialmsg;
	private RoomContainer room;

	public Device(String nodeId, String name, ArduinoMessenger messenger) {
		id = nodeId;
		displayName = name;
		serialmsg = messenger;
	}

	public Device(String nodeId, String name, ArduinoMessenger messenger,
			RoomContainer room) {
		this(nodeId, name, messenger);
		this.room = room;
		this.room.addDevice(this);
	}

	public void setRoom(RoomContainer room) {
		this.room = room;
		this.room.addDevice(this);
	}

	// Action command. Do not wait any reply from the Arduino Platform
	protected boolean deliverCommand(String message) {
		return serialmsg.sendMessage(message, null);
	}

	// Request command. Wait a reply from the Arduino Platform
	protected boolean deliverCommand(String message,
			ArduinoMessageConsumer consumer) {
		return serialmsg.sendMessage(message, consumer);
	}

	public String getId() {
		return this.id;
	}

	public String getDisplayName() {
		return this.displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public RoomContainer getRoom() {
		return room;
	}
}
