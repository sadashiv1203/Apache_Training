package apache.beam.demo.entities;

import java.io.Serializable;

public class Customer implements Serializable
{
	String id;
	String name;
	
	public Customer(String id, String name) {
		
		this.id = id;
		this.name = name;
	}
	
	public Customer() {
		
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	

}
