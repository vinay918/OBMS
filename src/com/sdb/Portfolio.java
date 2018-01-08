package com.sdb;
import java.util.ArrayList;

public class Portfolio implements FinancialSystem{    //portfolio class
	private String code;
	private Person owner;
	private Person manager;
	private Person beneficiary=null;
	private ArrayList<Asset> assets;

	public Portfolio(String code,Person owner, Person manager, Person beneficiary, ArrayList<Asset> assets) {
		super();
		this.code=code;
		this.owner = owner;
		this.manager = manager;
		this.beneficiary = beneficiary;
		this.assets = assets;
	}

	public String getCode(){
		return code;
	}
	public Person getOwner() {
		return owner;
	}

	public Person getManager() {
		return manager;
	}

	public Person getBeneficiary() {
		return beneficiary;
	}
	
	public String getOwnerName() {
		return owner.getFormattedName();
	}

	public String getManagerName() {
		return manager.getFormattedName();
	}

	public String getBeneficiaryName() {
		if(this.beneficiary!=null){
		return beneficiary.getFormattedName();
		}
		else{
			return "None";
		}
	}

	//return commission according to asset list
	public double getCommisions() {
		double comm;
		if(this.getManager().getType() != null && this.getManager().getType().equals("E")){
			comm=this.getAnnualReturn()*0.05;
		}
		else{
			comm=this.getAnnualReturn()*0.02;
		}
		return comm;
	}

	//fees depends on asset list
	public double getFees() {
		double fees;
		if(this.getManager().getType() != null && this.getManager().getType().equals("E")){
			fees=this.getAssets().size()*10;
		}
		else{
			fees=this.getAssets().size()*50;
		}
		return fees;
	}

	
	//annual return depends on asset list
	public double getAnnualReturn() {
		double annualReturn=0;
		
		for(Asset x:this.getAssets()){
			//System.out.println("The asset " + x.getCode() + " has " + x.getAnnualReturn());
			annualReturn=annualReturn+x.getAnnualReturn();	
		}
		return annualReturn; 
	}
	
	public double getTotalValue(){
		double val=0;
	    for(Asset y:this.getAssets()){
	    	val=val+y.getWeightedValue();
	    }
	    return val;
	}
	
	public double getWeightedRisk() {
		double risk=0;
		for(Asset x:this.getAssets()){
			risk=risk+((x.getWeightedValue()/this.getTotalValue())*x.getRisk());
		}
		return risk;
	}

	public ArrayList<Asset> getAssets() {
		return assets;
	}
	
	
	
	
}
