package com.sdb;

public class Address {
	
	private String street;
	private String city;
	private String state;
	private String zipcode;
	private String country;
	
	
	
	public String getStreet() {
		return this.street;
	}



	public String getCity() {
		return this.city;
	}



	public String getState() {
		return this.state;
	}



	public String getZipcode() {
		return this.zipcode;
	}



	public String getCountry() {
		return this.country;
	}

	public String toString(){ 
		String output;
		output=this.street+", "+this.city+", "+this.state+", "+this.zipcode+", "+this.country;
		return output;
		
	}


	public Address(String street, String city, String state, String zipcode, String country) {
		
		super();
		this.street = street;
		this.city = city;
		this.state = state;
		this.zipcode = zipcode;
		this.country = country;
	}
	
	
	

}
