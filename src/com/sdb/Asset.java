package com.sdb;
public abstract class Asset implements FinancialSystem{
	
//	abstract class for assets
	private String code;
	private String label;
	private double amountOwned=0;
	
	public Asset(String code, String label, double amountOwned) {
		this.code = code;
		this.label = label;
		this.amountOwned=amountOwned;
	}

	public String getCode() {
		return code;
	}

	public String getLabel() {
		return label;
	}
	
	public double getAmountOwned() {
		return amountOwned;
	}
	
	public String toString() {
		return this.getLabel();
	}
	
//	methods that need to be implemented:
	public abstract String getType();
	
	public abstract double getRisk();
	
	public abstract double getReturnRate();
	
	public double getBaseRateOfReturn(){
		return 0;
	}
	
	public abstract double getAnnualReturn();
	
	public abstract double getWeightedValue();

//	copy constructor
	public Asset copyAsset(double s) {
		return null;
	}




}
