package com.sdb;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseRetriever {
	
//	method to retrieve portfolios from database by first getting all portfolioCodes from the database
	public static ArrayList<Portfolio> getPortfolios(){
		
		Logger log=Logger.getLogger("System Logger");
		Connection conn=DatabaseAbstraction.connect();
		ArrayList<Portfolio> portfolios = new ArrayList<>();
		ArrayList<Asset> as = new ArrayList<>();
		String query="SELECT portfolioCode FROM Portfolio;";
		PreparedStatement ps = null;
		ResultSet rs = null;
		Person owner=null;
		Person manager=null;
		Person beneficiary=null;
		ArrayList<String> portfolioCode = new ArrayList<String>();
		try {
			ps=conn.prepareStatement(query);
			portfolioCode=PortfolioData.runQueryString(ps, "portfolioCode");
		} catch (SQLException e) {
			log.log(Level.SEVERE, "SQL exception line 29");
		}
		
//		use portfolio codes to iterate over portfolios and add necessary components (persons and assets)
		for(String S:portfolioCode){ //loop
		as=null;
		query="SELECT * FROM Portfolio p JOIN JoinedPort jp ON p.portfolioId=jp.portfolioId"+
				" JOIN Asset a ON jp.assetId=a.assetId WHERE portfolioCode=?;";
		try {
			ps=conn.prepareStatement(query);
			ps.setString(1,S);
			rs = ps.executeQuery();
			as = getAssets(rs);
		} catch (SQLException e) {
			log.log(Level.SEVERE, "SQL exception line 44");
		}
		
		String query2="SELECT * FROM Person p JOIN Portfolio por ON p.personId=por.ownerId WHERE portfolioCode=?;";
		try {
			ps=conn.prepareStatement(query2);
			ps.setString(1,S);
			rs = ps.executeQuery();
			owner = getPerson(rs);
		} catch (SQLException e) {
			log.log(Level.SEVERE, "SQL exception line 54");
		}
		
		String query3="SELECT * FROM Person p JOIN Portfolio por ON p.personId=por.managerId WHERE portfolioCode=?;";
		try {
			ps=conn.prepareStatement(query3);
			ps.setString(1,S);
			rs = ps.executeQuery();
			manager = getPerson(rs);
		} catch (SQLException e) {
			log.log(Level.SEVERE, "SQL exception line 64");
		}
		
		String query4="SELECT * FROM Person p JOIN Portfolio por ON p.personId=por.beneficiaryId WHERE portfolioCode=?;";
		try {
			ps=conn.prepareStatement(query4);
			ps.setString(1,S);
			rs = ps.executeQuery();
			beneficiary = getPerson(rs);
		} catch (SQLException e) {
			log.log(Level.SEVERE, "SQL exception line 74");
		}
		
		Portfolio p = new Portfolio(S,owner,manager,beneficiary,as);
		portfolios.add(p);
		} //close loop
		
		return portfolios;
		
	}
	
//	Method to retrieve assets from database and return them in a list
	public static ArrayList<Asset> getAssets(ResultSet rs){
		Logger log=Logger.getLogger("System Logger");
		ArrayList<Asset> output = new ArrayList<Asset>();
		Asset as= null;
		try {
			while(rs.next()){
				as= null;
				if(rs.getString("type").equals("D")){
					as = new DepositAccount(rs.getString("assetCode"),rs.getString("name"),rs.getDouble("amountOwned"),rs.getDouble("apr"));
				}
				else if(rs.getString("type").equals("P")){
					as= new PrivateInvestments(rs.getString("assetCode"),rs.getString("name"),rs.getDouble("amountOwned"),
							rs.getDouble("quarterlyDividend"),rs.getDouble("baseRateOfReturn"),rs.getDouble("omega"),rs.getDouble("value"));
				}
				else{
					as = new StocksAccount(rs.getString("assetCode"),rs.getString("name"),rs.getDouble("amountOwned"),
							rs.getDouble("quarterlyDividend"),rs.getDouble("baseRateOfReturn"),rs.getDouble("beta")," ",rs.getDouble("sharePrice"));
				}
				output.add(as);
			}
		} catch (SQLException e) {
			log.log(Level.SEVERE, "SQL exception line 107");
		}
		return output;
	}
	
//	method to get persons and return them in a list
	public static Person getPerson(ResultSet rs){
		Logger log=Logger.getLogger("System Logger");
		Person output=null;
		try {
			while(rs.next()){
				if(rs.getString("brokerType") != null){
					output = new Broker(rs.getString("personCode"),rs.getString("lastName"),
							rs.getString("firstName"),getAddress(rs.getInt("personId")),
							getEmail(rs.getInt("personId")),rs.getString("brokerType"),rs.getString("secIden"));
				}
				else{
					output = new Person(rs.getString("personCode"),rs.getString("lastName"),
							rs.getString("firstName"),getAddress(rs.getInt("personId")),
							getEmail(rs.getInt("personId")));			
					}
			}
		} catch (SQLException e) {
			log.log(Level.SEVERE, "SQL exception line 130");
		}
		return output;
		
	}

//	Method to return addresses from database.
	public static Address getAddress(int personId){
		Connection conn=DatabaseAbstraction.connect();
		Logger log=Logger.getLogger("System Logger");
		ResultSet rs = null;
		PreparedStatement ps=null;
		Address a = null;
		String street=null;
		String city=null;
		String state=null;
		String zipcode=null;
		String country=null;

		String query="SELECT s.name AS stateName, c.name AS countryName, a.* FROM Person p JOIN Address a ON "+
					" p.personId=a.personId JOIN State s ON a.stateId=s.stateId JOIN Country c ON a.countryId=c.countryId"+
					" WHERE a.personId=?;";
		try {
			ps=conn.prepareStatement(query);
			ps.setInt(1,personId);
			rs = ps.executeQuery();
			if(rs.next()){
			street=rs.getString("street");
			city=rs.getString("city");
			state=rs.getString("stateName");
			zipcode=rs.getString("zipcode");
			country=rs.getString("countryName");
			}
			a= new Address(street,city,state,zipcode,country);
		} catch (SQLException e) {
			log.log(Level.SEVERE, "SQL exception line 165");
		}
		DatabaseAbstraction.disconnect(conn, rs, ps);

		return a;
		
	}
	
//	Method to retrieve emails from database and return them in a list
	public static ArrayList<String> getEmail(int personId){
		Connection conn=DatabaseAbstraction.connect();
		Logger log=Logger.getLogger("System Logger");
		ResultSet rs = null;
		PreparedStatement ps=null;
		ArrayList<String> out = new ArrayList<String>();
		String query="SELECT * FROM Person p JOIN Email e ON p.personId=e.personId WHERE p.personId=?;";
		try {
			ps=conn.prepareStatement(query);
			ps.setInt(1,personId);
			rs = ps.executeQuery();
			while(rs.next()){
			String email=rs.getString("address");
			out.add(email);
			}
		} catch (SQLException e) {
			log.log(Level.SEVERE, "SQL exception line 190");
		}
		DatabaseAbstraction.disconnect(conn, rs, ps);

		return out;
		
	}
}
