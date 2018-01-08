package com.sdb;
import java.util.ArrayList;

/**
 * Class that handles writing to the console
 */

public class FileOut {

	/**
	 * This method only handles writing the summaries of portfolios as specified in Assignment 7. It takes in 
	 * a PortfolioList and title of the summary, to print the output accordingly
	 */
	public static void writeSummary(PortfolioList<Portfolio> input, String title){
		System.out.printf("Portfolio Summary Report of Order By %s \n \n",title);
		System.out.println("=======================================================================");
		System.out.printf("%-20s %-20s %-20s %-20s %-20s %-20s %-20s %-20s\n\n", "Portfolio","Owner","Manager","Fees","Commissions","Weighted Risk","Return","Total");
		for (int i = 0; i <input.getSize(); i++) {
			Portfolio data = input.getElementAtIndex(i);
			System.out.printf("%-20s %-20s %-20s $ %-20.2f $ %-20.2f %-20.3f $ %-20.2f $ %-10.2f\n\n",data.getCode(),data.getOwnerName(),data.getManagerName()+" ("+data.getManager().getType()+")",data.getFees(),data.getCommisions(),data.getWeightedRisk(),data.getAnnualReturn(),data.getTotalValue());
		}
		
		double fees=0;
		double commissions=0;
		double returns=0;
		double total=0;
		for(Portfolio p:input){
			fees+=p.getFees();
			commissions+=p.getCommisions();
			returns+=p.getAnnualReturn();
			total+=p.getTotalValue();
					
		}
	    String s="Fees";
	    String d="Commissions";
	    String f="Annual Returns";
	    String g="Total Value";
	    
		System.out.printf("Totals \n");
		System.out.printf("%-18s %30.2f \n ",s,fees);
		System.out.printf("%-20s %30.2f \n ",d,commissions);
		System.out.printf("%-20s %30.2f \n ",f,returns);
		System.out.printf("%-20s %30.2f \n\n\n ",g,total);
	}
	
	/**
	 * This method takes in an arraylist of portfolios and writes the detailed reports to the
	 * console.
	 */
	public static <T extends Portfolio> void write(ArrayList<T> input){
		//Sorting by Alphabetical order starts here
		T temp;
		for(int a = 0; a< input.size();a++){
			for(int c = a + 1; c <input.size();c++){
				if(input.get(a).getOwnerName().compareTo(input.get(c).getOwnerName())>0){
					temp = input.get(a);
					input.set(a, input.get(c));
					input.set(c,temp);
				}
			}
		}
		//Out put starts here
		System.out.printf("Portfolio Summary Report \n \n");
		System.out.println("=======================================================================");
		System.out.printf("%-20s %-20s %-20s %-20s %-20s %-20s %-20s %-20s\n\n", "Portfolio","Owner","Manager","Fees","Commissions","Weighted Risk","Return","Total");
		for (int i = 0; i <input.size(); i++) {
			Portfolio data = input.get(i);
			System.out.printf("%-20s %-20s %-20s $ %-20.2f $ %-20.2f %-20.3f $ %-20.2f $ %-10.2f\n\n",data.getCode(),data.getOwnerName(),data.getManager()+" ("+data.getManager().getType()+")",data.getFees(),data.getCommisions(),data.getWeightedRisk(),data.getAnnualReturn(),data.getTotalValue());
		}
		
		double fees=0;
		double commissions=0;
		double returns=0;
		double total=0;
		for(Portfolio p:input){
			fees+=p.getFees();
			commissions+=p.getCommisions();
			returns+=p.getAnnualReturn();
			total+=p.getTotalValue();
					
		}
	    String s="Fees";
	    String d="Commissions";
	    String f="Annual Returns";
	    String g="Total Value";
	    
		System.out.printf("Totals \n");
		System.out.printf("%-18s %30.2f \n ",s,fees);
		System.out.printf("%-20s %30.2f \n ",d,commissions);
		System.out.printf("%-20s %30.2f \n ",f,returns);
		System.out.printf("%-20s %30.2f \n\n\n ",g,total);
		
		
		
		System.out.println("Portfolio Details");
		System.out.println("=======================================================================");
		for (int i = 0; i < input.size(); i++) {
			Portfolio data = input.get(i);
			double totalAnnualReturn = 0;
			double totalValue = 0;
			System.out.println("Portfolio " + data.getCode());
			System.out.println("------------------------");
			System.out.printf("Owner: %-20s\n",data.getOwner());
			System.out.printf("Manager: %-20s\n",data.getManager());
			System.out.printf("Beneficiary: %-20s\n",data.getBeneficiaryName());
			System.out.printf("Assets\n");
			System.out.printf("%-20s %-20s %-20s %-20s %-20s %-20s\n","Code","Asset","Return Rate","Risk","Annual Return","Value");
			for(int j = 0;j < data.getAssets().size();j++){
				Asset ast = data.getAssets().get(j);
				System.out.printf("%-20s %-20s %5.3f%% %18.3f%20s %-1.2f%10s %.2f\n",ast.getCode(),ast.getLabel(),ast.getReturnRate()*100,ast.getRisk(),"$",ast.getAnnualReturn(),"$",ast.getWeightedValue());
				totalAnnualReturn += ast.getAnnualReturn();
				totalValue += ast.getWeightedValue();
			}
			System.out.printf("%120s","-----------------------------------------------------------------------------\n");
			System.out.printf("%48s %18.3f %23.2f %20.2f","Totals",data.getWeightedRisk(),totalAnnualReturn,totalValue);
			System.out.println("\n");
		}
	}
}
