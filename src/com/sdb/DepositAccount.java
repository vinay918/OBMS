package com.sdb;
public class DepositAccount extends Asset {    //deposit account class
	private double apr;
	private String type="DepositAccount";
	

	public DepositAccount(String code, String label, double amountOwned,double apr) {
		super(code, label, amountOwned);
		this.apr = apr;
		
	}

	public double getApr() {
		return this.apr;
	}

	@Override
	public double getRisk() {
		return 0;
	}
	
	@Override
	public String getType(){
		return this.type;
	}

	@Override
	public double getWeightedValue() {
		return this.getAmountOwned();
	}

	@Override
	public double getAnnualReturn(){
		return this.getWeightedValue()*this.getReturnRate();
	}
	
	@Override
	public double getReturnRate() {
		// TODO Auto-generated method stub
		return  Math.exp(this.getApr())-1;
	}
	
//	copy constructor
	public Asset copyAsset(double v) {
		DepositAccount q = new DepositAccount(this.getCode(), this.getLabel(),v,this.getApr());
		return q;
	}






}
