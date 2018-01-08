package com.sdb;
public class StocksAccount extends Asset {  //stock class
	private double quarterlyDividend;
	private double baseRateOfReturn;
	private double beta;
	private String symbol;
	private double sharePrice;
	private String type="Stock";
	
	public StocksAccount(String code,String label,double amountOwned,double quarterlyDividend,double baseRateOfReturn,double betaMeasure,String stockSymbol,double sharePrice) {
		super(code, label, amountOwned);
		//System.out.println(amountOwned);
		this.quarterlyDividend = quarterlyDividend;
		this.baseRateOfReturn = baseRateOfReturn;
		this.symbol = stockSymbol;
		this.beta = betaMeasure;
		this.sharePrice=sharePrice;
	}

	public double getQuarterlyDividend() {
		return quarterlyDividend;
	}

	@Override
	public double getBaseRateOfReturn() {
		return baseRateOfReturn;
	}

	public double getBeta() {
		return beta;
	}

	public String getSymbol() {
		return symbol;
	}

	public double getSharePrice() {
		return sharePrice;
	}

	@Override
	public double getRisk() {
		return this.getBeta();
	}

	@Override
	public double getReturnRate() {
		return this.getAnnualReturn()/this.getWeightedValue();
	}

	@Override
	public double getWeightedValue() {
		return this.getAmountOwned()*this.getSharePrice();
	}
	
	@Override
	public double getAnnualReturn(){
		return (this.getWeightedValue()*this.getBaseRateOfReturn())+(this.getQuarterlyDividend()*4*this.getAmountOwned());
	}

	@Override
	public String getType(){
		return this.type;
	}

//	copy constructor
	public Asset copyAsset(double v) {
		StocksAccount q = new StocksAccount(this.getCode(), this.getLabel(),v,this.getQuarterlyDividend(),
							this.getBaseRateOfReturn(),this.getBeta(),this.getSymbol(),this.getSharePrice());
		return q;
	}

}
