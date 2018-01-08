package com.sdb;
import java.util.ArrayList;
/**
 * This is the main class to generate
 * portfolio reports
 */

public class PortfolioReport {
	
	
	/**
	 * Main method that calls the getPortfolios method in the DatabaseRetriever class to return an arraylist
	 * of portfolios. The portfolios are then added to three different instantiations of the Sorted List ADT
	 * (PortfolioList) which are all then passed to the FileOut.writeSummary method to output the information
	 * to the console
	 * @param args
	 */
	public static void main(String[] args){
		ArrayList<Portfolio> ports=DatabaseRetriever.getPortfolios(); //retrieve portfolios
		PortfolioList<Portfolio> portName=new PortfolioList<Portfolio>(new NameComparator());
		PortfolioList<Portfolio> portValue=new PortfolioList<Portfolio>(new ValueComparator());
		PortfolioList<Portfolio> portManager=new PortfolioList<Portfolio>(new ManagerComparator());
		for(Portfolio p : ports){
			portName.add(p);
			portValue.add(p);
			portManager.add(p);
		}
		

		FileOut.writeSummary(portName,"NAMES");
		FileOut.writeSummary(portValue,"VALUE");
		FileOut.writeSummary(portManager,"MANAGER TYPE");

	}
}
