package com.sdb;
import java.util.List;
public class Broker extends Person{

	
private String type;

private String secIdentifier;




public Broker(String code, String lastName, String firstName, Address address, List<String> email, String type,String secIdentifier) {
	super(code, lastName, firstName, address, email);
	this.type = type;
	this.secIdentifier=secIdentifier;
}


public String getSecIdentifier() {
	return this.secIdentifier;
}

public String getType() {
	return this.type;
}

	
}
