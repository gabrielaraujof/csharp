package br.ufs.dcomp.csharp.automation;

import br.ufs.dcomp.csharp.communication.ArduinoMessageConsumer;
import br.ufs.dcomp.csharp.communication.ArduinoMessenger;

/**
 * 
 * @author gabriel
 *
 */
public abstract class Node implements ArduinoMessageConsumer {

	private String id;
	private String displayName;

	private ArduinoMessenger serialmsg;

	public Node(String nodeId, String name, ArduinoMessenger messenger) {
		id = nodeId;
		displayName = name;
		serialmsg = messenger;
	}

	protected boolean deliverCommand(String message, boolean hasReply) {
		if (hasReply)
			return serialmsg.sendMessage(message, this);
		else
			return serialmsg.sendMessage(message);
	}
	
	public String getId(){
		return this.id;
	}
	
	public String getDisplayName(){
		return this.displayName;
	}
}
