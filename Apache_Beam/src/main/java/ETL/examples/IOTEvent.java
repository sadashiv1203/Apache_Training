package ETL.examples;

import java.io.Serializable;

public class IOTEvent implements Serializable
{
	private String devId;
	private String devName;
	
	//{ "devId": "A" ,"devName": "Sadashiv" }
	//{ "devId": "B" ,"devName": "Suryaji" }
	
    public IOTEvent() {
		
	}
	
	public IOTEvent(String devId, String devName) {
		this.devId = devId;
		this.devName = devName;
	}
	
	

	public String getDevId() {
		return devId;
	}
	public void setDevId(String devId) {
		this.devId = devId;
	}
	public String getDevName() {
		return devName;
	}
	public void setDevName(String devName) {
		this.devName = devName;
	}


}
