package com.sdb;
public class PrivateInvestments extends Asset { //private investment class
	private double quarterlyDividend;
	private double baseRateOfReturn;
	private double omega;
	private double value;
	private String type="PrivateInvestment";
	
	public PrivateInvestments(String code, String label, double amountOwned,double quarterlyDividend, double baseRateOfReturn, double omegaMeasure, double totalValue) {
		super(code, label, amountOwned);
		this.quarterlyDividend = quarterlyDividend;
		this.baseRateOfReturn = baseRateOfReturn;
		this.omega = omegaMeasure;
		this.value = totalValue;
		
	}

	public double getQuarterlyDividend() {
		return quarterlyDividend;
	}
	
	@Override
	public double getBaseRateOfReturn() {
		return baseRateOfReturn;
	}
	
	@Override
	public String getType(){
		return this.type;
	}
	
	public double getOmega() {
		return omega;
	}

	public double getValue() {
		return value;
	}

	@Override
	public double getRisk() {
		double expVal = (-100000/this.getValue());
		return this.getOmega()+Math.exp(expVal);
	}

	@Override
	public double getReturnRate() {
		return this.getAnnualReturn()/this.getWeightedValue();
	}
	
	@Override
	public double getWeightedValue() {
		return (this.getAmountOwned())*this.getValue();	
	}
	
	@Override
	public double getAnnualReturn(){
		return (this.getAmountOwned()*this.getValue()*this.getBaseRateOfReturn())+(this.getQuarterlyDividend()*4*(this.getAmountOwned()));
	}
	
//	copy constructor
	public Asset copyAsset(double v) {				 
		PrivateInvestments q = new PrivateInvestments(this.getCode(), this.getLabel(), v,
								this.getQuarterlyDividend(),this.getBaseRateOfReturn(), this.getOmega(), 
								this.getValue());
		return q;
	}



}
