package br.ufs.dcomp.csharp.automation;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class RoomContainer{

	private String id;
	private String displayName;
	
	private List<Device> devices;
	
	public RoomContainer(String id, String name) {
		this.id = id;
		this.displayName = name;
		this.devices = new ArrayList<Device>();
	}
	
	public void addDevice(Device dev){
		this.devices.add(dev);
	}
	
	public void removeDevice(Device dev){
		this.devices.remove(dev);
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getId() {
		return id;
	}
	
	public ListIterator<Device> getDevices(){
		return this.devices.listIterator();
	}
}
