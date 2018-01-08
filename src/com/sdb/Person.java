package com.sdb;
import java.util.List;

public class Person implements FinancialSystem{     //person class
	

	private String code;
	private String firstName;
	private String lastName;
	private Address address;
	private List<String> email;
	
	
	public Person(String code, String lastName, String firstName,Address address, List<String> email) {
		super();
		this.email=email;
		this.lastName = lastName;
		this.firstName = firstName;
		this.address = address;
		this.code = code;
	}



	public String getLastName() {
		return lastName;
	}



	public String getFirstName() {
		return firstName;
	}

	public String getType(){
		String s=null;
		return s;
	}
	

	public List<String> getEmail() {
		return this.email;
	}



	public Address getAddress() {
		return this.address;
	}

	public String getCode() {
		return this.code;
	}
	
//	formatted name to make outputs easier
    public String getFormattedName(){
    	return this.getLastName()+", "+this.getFirstName();
    }
	public String toString(){
		String text;
		text=this.firstName+" "+this.lastName;
		return text;
	}
	
	public void setCode(String code){
		this.code = code;
	}

	
}
