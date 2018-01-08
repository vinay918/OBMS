package com.sdb; //DO NOT CHANGE THIS
import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This is a collection of utility methods that define a general API for
 * interacting with the database supporting this application.
 *
 */
public class PortfolioData {
//	The following two are helper methods to run queries and return a list of strings/integers from column of interest
	public static ArrayList<Integer> runQuery(PreparedStatement ps, String column){
		Logger log=Logger.getLogger("Database Connection");
		Connection conn=DatabaseAbstraction.connect();
		ResultSet rs = null;
		ArrayList<Integer> output = new ArrayList<Integer>();
		
		try {
			rs = ps.executeQuery();
			while(rs.next()) {
			int ID = rs.getInt(column);
			output.add(ID);
			}
			rs.close();
		} catch (SQLException e) {
			log.log(Level.SEVERE, "SQL exception line 32");
		}
		DatabaseAbstraction.disconnect(conn, rs, ps);
		return output;
	}
	
	public static ArrayList<String> runQueryString(PreparedStatement ps, String column){
		Logger log=Logger.getLogger("Database Connection");
		Connection conn=DatabaseAbstraction.connect();
		ResultSet rs = null;
		ArrayList<String> output = new ArrayList<String>();

		try {
			rs = ps.executeQuery();
			while(rs.next()) {
			String ID = rs.getString(column);
			output.add(ID);
			}
			rs.close();
		} catch (SQLException e) {
			log.log(Level.SEVERE, "SQL exception line 52");
		}
		DatabaseAbstraction.disconnect(conn, rs, ps);
		return output;
	}
	
	/**
	 * Method that removes every person record from the database
	 */
	
	public static void removeAllPersons() {
		Connection conn=DatabaseAbstraction.connect();
		Logger log=Logger.getLogger("Database Connection");
		String query="SELECT * FROM Person;";
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(query);
		} catch (SQLException e) {
			log.log(Level.SEVERE, "SQL exception line 69");
		}
		ArrayList<String> personCode = runQueryString(ps,"personCode");
			for(String a:personCode){
				removePerson(a);
			}
		DatabaseAbstraction.disconnect(conn, ps);
		
	}
	
//	Method to remove emails
	public static void removeEmail(int emailId){
		Logger log=Logger.getLogger("Database Connection");
		Connection conn=DatabaseAbstraction.connect();
		String query="DELETE FROM Email WHERE emailId=?;";
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(query);
			ps.setInt(1,emailId);
			ps.executeUpdate();
		} catch (SQLException e) {
			log.log(Level.SEVERE, "SQL exception line 90");
		}
		DatabaseAbstraction.disconnect(conn, ps);
	}
//	Method to remove addresses
	public static void removeAddress(int addressId){
		Logger log=Logger.getLogger("Database Connection");
		Connection conn=DatabaseAbstraction.connect();
		String query="DELETE FROM Address WHERE addressId=?;";
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(query);
			ps.setInt(1,addressId);
			ps.executeUpdate();
		} catch (SQLException e) {
			log.log(Level.SEVERE, "SQL exception line 40");
		}
		DatabaseAbstraction.disconnect(conn, ps);
	}
	/**
	 * Removes the person record from the database corresponding to the
	 * provided <code>personCode</code>. It runs appropriate deletions of portfolios and addresses before
	 * deleting the person 
	 * @param personCode
	 */
	
	public static void removePerson(String personCode) {
		Logger log=Logger.getLogger("Database Connection");
		Connection conn=DatabaseAbstraction.connect();
		
		String preQuery = "SELECT * FROM Email jp JOIN Person p on jp.personId=p.personId WHERE p.personCode= ?;";
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(preQuery);
			ps.setString(1,personCode);
		} catch (SQLException e1) {
			log.log(Level.SEVERE, "SQL exception line 125");
		}
		ArrayList<Integer> emailId = runQuery(ps,"emailId");
		for(Integer i : emailId){
			removeEmail(i);
		}
		
		preQuery = "SELECT * FROM Address jp JOIN Person p on jp.personId=p.personId WHERE "+
					"p.personCode=?;";
		try {
			ps = conn.prepareStatement(preQuery);
			ps.setString(1,personCode);
		} catch (SQLException e1) {
			log.log(Level.SEVERE, "SQL exception line 138");
		}
		ArrayList<Integer> addressId = runQuery(ps,"addressId");
		for(Integer i : addressId){
			removeAddress(i);
		}
		
		preQuery = "SELECT * FROM Portfolio jp JOIN Person p on jp.ownerId=p.personId WHERE p.personCode=?;";
		try {
			ps = conn.prepareStatement(preQuery);
			ps.setString(1,personCode);
		} catch (SQLException e1) {
			log.log(Level.SEVERE, "SQL exception line 150");
		}
		ArrayList<String> portfolioCode = runQueryString(ps,"portfolioCode");
		for(String i : portfolioCode){
			removePortfolio(i);
		}
		
		preQuery = "SELECT * FROM Portfolio jp JOIN Person p on jp.beneficiaryId=p.personId WHERE p.personCode=?;";
		try {
			ps = conn.prepareStatement(preQuery);
			ps.setString(1,personCode);
		} catch (SQLException e1) {
			log.log(Level.SEVERE, "SQL exception line 162");
		}
		portfolioCode = runQueryString(ps,"portfolioCode");
		for(String i : portfolioCode){
			removePortfolio(i);
		}
		
		preQuery = "SELECT * FROM Portfolio jp JOIN Person p on jp.managerId=p.personId WHERE p.personCode=?;";
		try {
			ps = conn.prepareStatement(preQuery);
			ps.setString(1,personCode);
		} catch (SQLException e1) {
			log.log(Level.SEVERE, "SQL exception line 174");
		}
		portfolioCode = runQueryString(ps,"portfolioCode");
		for(String i : portfolioCode){
			removePortfolio(i);
		}
		
		String query="DELETE FROM Person WHERE personCode=?;";
		ps = null;
		try {
			ps = conn.prepareStatement(query);
			ps.setString(1,personCode);
			ps.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			log.log(Level.SEVERE, "SQL exception line 189");		}
		DatabaseAbstraction.disconnect(conn, ps);
	}
	
	/**
	 * Method to add a person record to the database with the provided data. The
	 * <code>brokerType</code> will either be "E" or "J" (Expert or Junior) or 
	 * <code>null</code> if the person is not a broker. Also adds addresses.
	 * @param personCode
	 * @param firstName
	 * @param lastName
	 * @param street
	 * @param city
	 * @param state
	 * @param zip
	 * @param country
	 * @param brokerType
	 */
	public static void addPerson(String personCode, String firstName, String lastName, String street, String city, String state, String zip, String country, String brokerType, String secBrokerId) {
		Logger log=Logger.getLogger("Database Connection");
		Connection conn=DatabaseAbstraction.connect();
		String query = "INSERT INTO Person(lastName,firstName,personCode,secIden,brokerType) VALUE( ?,?,?,?,?);";
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(query);
			ps.setString(1,lastName);
			ps.setString(2,firstName);
			ps.setString(3,personCode);
			ps.setString(4,secBrokerId);
			ps.setString(5,brokerType);
			ps.executeUpdate();
		} catch (SQLException e) {
			log.log(Level.SEVERE, "SQL exception line 221");
		}
		
		String query1 = "INSERT INTO Country(name) VALUE(?);";
		try {
			ps=conn.prepareStatement(query1);
			ps.setString(1,country);
			ps.executeUpdate();
		} catch (SQLException e1) {
			log.log(Level.SEVERE, "SQL exception line 230");
		}
		String query2 = "INSERT INTO State(name) VALUE(?);";
		PreparedStatement ps2 = null;
		try {
			ps2 = conn.prepareStatement(query2);
			ps2.setString(1,state);
			ps2.executeUpdate();
		} catch (SQLException e) {
			log.log(Level.SEVERE, "SQL exception line 239");
		}
		
		query = "SELECT * FROM Person WHERE personCode=?;";
		try {
			ps = conn.prepareStatement(query);
			ps.setString(1, personCode);
		} catch (SQLException e1) {
			log.log(Level.SEVERE, "SQL exception line 247");
		}
		ArrayList<Integer> personId = runQuery(ps,"personId");
		
		query = "INSERT INTO Address(street,city,stateId,zipcode,countryId,personId) "+
				"VALUE( ?,?,(SELECT stateId FROM State where name = ? LIMIT 1),"+
		        "?,(SELECT countryId from Country where name = ? LIMIT 1),?);";
		try {
			ps = conn.prepareStatement(query);
			ps.setString(1, street);
			ps.setString(2, city);
			ps.setString(3, state);
			ps.setString(4, zip);
			ps.setString(5, country);
			ps.setInt(6, personId.get(0));
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		DatabaseAbstraction.disconnect(conn, ps);
		DatabaseAbstraction.disconnect(conn, ps2);
	}
	
	/**
	 * Adds an email record corresponding person record corresponding to the
	 * provided <code>personCode</code>
	 * @param personCode
	 * @param email
	 */
	public static void addEmail(String personCode, String email) {
		Logger log=Logger.getLogger("Database Connection");
		Connection conn=DatabaseAbstraction.connect();
		String query = "INSERT INTO Email(address,personId) VALUE( ?,(SELECT personId FROM "+
						"Person WHERE personCode=?));";
		PreparedStatement ps=null;
		try {
			ps = conn.prepareStatement(query);
			ps.setString(1,email);
			ps.setString(2,personCode);
			ps.executeUpdate();
		} catch (SQLException e) {
			log.log(Level.SEVERE, "SQL exception line 289");
		}
		DatabaseAbstraction.disconnect(conn, ps);
	}
	
	/*Method to remove an entry from the portfolio/asset join table*/
	public static void removeJoinedPort(int code){
		Logger log=Logger.getLogger("Database Connection");
		Connection conn=DatabaseAbstraction.connect();
		String query="DELETE FROM JoinedPort WHERE portfileId=?;";
		PreparedStatement ps = null;
		
		try {
			ps = conn.prepareStatement(query);
			ps.setInt(1,code);
			ps.executeUpdate();
		} catch (SQLException e) {
			log.log(Level.SEVERE, "SQL exception line 306");
		}
		DatabaseAbstraction.disconnect(conn, ps);
	}
	
	/**
	 * Removes all asset records from the database
	 */
	public static void removeAllAssets() {
		Connection conn=DatabaseAbstraction.connect();
		Logger log=Logger.getLogger("Database Connection");
		String query="SELECT * FROM Asset;";
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(query);
		} catch (SQLException e) {
			log.log(Level.SEVERE, "SQL exception line 321");
		}
		ArrayList<String> assetCode = runQueryString(ps,"assetCode");
			for(String a:assetCode){
				removeAsset(a);
			}
		DatabaseAbstraction.disconnect(conn, ps);
	}

	
	/**
	 * Removes the asset record from the database corresponding to the
	 * provided <code>assetCode</code>
	 * @param assetCode
	 */
	public static void removeAsset(String assetCode) {
		Logger log=Logger.getLogger("Database Connection");
		Connection conn=DatabaseAbstraction.connect();
		
		String preQuery = "SELECT * FROM JoinedPort jp JOIN Asset a on jp.assetId=a.assetId "+
						"WHERE a.assetCode=?;";
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(preQuery);
			ps.setString(1,assetCode);
		} catch (SQLException e1) {
			log.log(Level.SEVERE, "SQL exception line 348");
		}
		ArrayList<Integer> joinedPortId = runQuery(ps,"portfileId");
		if(joinedPortId.size()>0){
			for(Integer i : joinedPortId){
			removeJoinedPort(i);
			}
		}
		
		String query="DELETE FROM Asset WHERE assetCode=?";
		ps = null;
		
		try {
			ps = conn.prepareStatement(query);
			ps.setString(1,assetCode);
			ps.executeUpdate();
		} catch (SQLException e) {
			log.log(Level.SEVERE, "SQL exception line 365");
		}
		DatabaseAbstraction.disconnect(conn, ps);
		
	}
	
	/**
	 * Adds a deposit account asset record to the database with the
	 * provided data. 
	 * @param assetCode
	 * @param label
	 * @param apr
	 */
	public static void addDepositAccount(String assetCode, String label, double apr) {
		Logger log=Logger.getLogger("Database Connection");
		Connection conn=DatabaseAbstraction.connect();
		String query = "INSERT INTO Asset(type,assetCode,name,apr) VALUE(\"D\",?,?,?);";
		PreparedStatement ps=null;
		try {
			ps = conn.prepareStatement(query);
			ps.setString(1,assetCode);
			ps.setString(2,label);
			ps.setDouble(3,apr);
			ps.executeUpdate();
		} catch (SQLException e) {
			log.log(Level.SEVERE, "SQL exception line 390");
		}
		DatabaseAbstraction.disconnect(conn, ps);
	}
	
	/**
	 * Adds a private investment asset record to the database with the
	 * provided data.  The <code>baseRateOfReturn</code> is assumed to be on the
	 * scale [0, 1].
	 * @param assetCode
	 * @param label
	 * @param quarterlyDividend
	 * @param baseRateOfReturn
	 * @param baseOmega
	 * @param totalValue
	 */
	public static void addPrivateInvestment(String assetCode, String label, Double quarterlyDividend, 
			Double baseRateOfReturn, Double baseOmega, Double totalValue) {
		Logger log=Logger.getLogger("Database Connection");
		Connection conn=DatabaseAbstraction.connect();
		String query = "INSERT INTO Asset(type,assetCode,name,baseRateOfReturn,quarterlyDividend,omega,value)"+
						" VALUE(\"P\",?,?,?,?,?,?);";
		PreparedStatement ps=null;
		try {
			ps = conn.prepareStatement(query);
			ps.setString(1,assetCode);
			ps.setString(2,label);
			ps.setDouble(3,baseRateOfReturn);
			ps.setDouble(4,quarterlyDividend);
			ps.setDouble(5,baseOmega);
			ps.setDouble(6,totalValue);
			ps.executeUpdate();
		} catch (SQLException e) {
			log.log(Level.SEVERE, "SQL exception line 423");
		}
		DatabaseAbstraction.disconnect(conn, ps);
	}
	
	/**
	 * Adds a stock asset record to the database with the
	 * provided data.  The <code>baseRateOfReturn</code> is assumed to be on the 
	 * scale [0, 1].
	 * @param assetCode
	 * @param label
	 * @param quarterlyDividend
	 * @param baseRateOfReturn
	 * @param beta
	 * @param stockSymbol
	 * @param sharePrice
	 */
	public static void addStock(String assetCode, String label, Double quarterlyDividend, 
			Double baseRateOfReturn, Double beta, String stockSymbol, Double sharePrice) {
		Logger log=Logger.getLogger("Database Connection");
		Connection conn=DatabaseAbstraction.connect();
		String query = "INSERT INTO Asset(type,assetCode,name,baseRateOfReturn,quarterlyDividend,sharePrice,beta)"+
						" VALUE(\"S\",?,?,?,?,?,?);";
		PreparedStatement ps=null;
		try {
			ps = conn.prepareStatement(query);
			ps.setString(1,assetCode);
			ps.setString(2,label);
			ps.setDouble(3,baseRateOfReturn);
			ps.setDouble(4,quarterlyDividend);
			ps.setDouble(5,sharePrice);
			ps.setDouble(6,beta);
			ps.executeUpdate();
		} catch (SQLException e) {
			log.log(Level.SEVERE, "SQL exception line 457");
		}
		DatabaseAbstraction.disconnect(conn, ps);
	}

	/**
	 * Removes all portfolio records from the database
	 */
	public static void removeAllPortfolios() {
		Connection conn=DatabaseAbstraction.connect();
		String query="SELECT * FROM Portfolio;";
		Logger log = Logger.getLogger("Database Connection");
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(query);
		} catch (SQLException e) {
			log.log(Level.SEVERE, "SQL exception line 473");
		}
		ArrayList<String> portfolioCode = runQueryString(ps,"portfolioCode");
			for(String a:portfolioCode){
				removePortfolio(a);
			}
		DatabaseAbstraction.disconnect(conn, ps);
	
	}
	
	/**
	 * Removes the portfolio record from the database corresponding to the
	 * provided <code>portfolioCode</code>
	 * @param portfolioCode
	 */
	public static void removePortfolio(String portfolioCode) {
		Logger log=Logger.getLogger("Database Connection");
		Connection conn=DatabaseAbstraction.connect();
		
		String preQuery = "SELECT * FROM JoinedPort jp JOIN Portfolio p on jp.portfolioId=p.portfolioId "+
							"WHERE p.portfolioCode=?;";
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(preQuery);
			ps.setString(1,portfolioCode);
		} catch (SQLException e1) {
			log.log(Level.SEVERE, "SQL exception line 499");
		}
		ArrayList<Integer> joinedPortId = runQuery(ps,"portfileId");
		
		for(Integer i : joinedPortId){
			removeJoinedPort(i);
		}
		
		String query="DELETE FROM Portfolio WHERE portfolioCode=?;";
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement(query);
			ps.setString(1,portfolioCode);
			ps.executeUpdate();
		} catch (SQLException e) {
			log.log(Level.SEVERE, "SQL exception line 514");
		}
		DatabaseAbstraction.disconnect(conn, rs, ps);
	}
	
	/**
	 * Adds a portfolio records to the database with the given data.  If the portfolio has no
	 * beneficiary, the <code>beneficiaryCode</code> will be <code>null</code>
	 * @param portfolioCode
	 * @param ownerCode
	 * @param managerCode
	 * @param beneficiaryCode
	 */
	public static void addPortfolio(String portfolioCode, String ownerCode, String managerCode, String beneficiaryCode) {
		Logger log=Logger.getLogger("Database Connection");
		Connection conn=DatabaseAbstraction.connect();
		String query = "INSERT INTO Portfolio(portfolioCode,ownerId,managerId,beneficiaryId) VALUE(?,"+
						"(SELECT personId FROM Person where personCode = ? ),"+
						"(SELECT personId FROM Person where personCode =  ? ),"+
						"(SELECT personId FROM Person where personCode = ? ));";
		PreparedStatement ps=null;
		try {
			ps = conn.prepareStatement(query);
			ps.setString(1,portfolioCode);
			ps.setString(2,ownerCode);
			ps.setString(3,managerCode);
			ps.setString(4,beneficiaryCode);
			ps.executeUpdate();
		} catch (SQLException e) {
			log.log(Level.SEVERE, "SQL exception line 543");
		}
		DatabaseAbstraction.disconnect(conn, ps);
	}
	
	/**
	 * Associates the asset record corresponding to <code>assetCode</code> with the 
	 * portfolio corresponding to the provided <code>portfolioCode</code>.  The third 
	 * parameter, <code>value</code> is interpreted as a <i>balance</i>, <i>number of shares</i>
	 * or <i>stake percentage</i> (on the scale [0, 1]) depending on the type of asset the <code>assetCode</code> is
	 * associated with.  
	 * @param portfolioCode
	 * @param assetCode
	 * @param value
	 */
	public static void addAsset(String portfolioCode, String assetCode, double value) {
		Logger log=Logger.getLogger("Database Connection");
		Connection conn=DatabaseAbstraction.connect();
		String query = "INSERT INTO JoinedPort(portfolioId,assetId,amountOwned)"+
						" VALUE((SELECT portfolioId FROM Portfolio WHERE portfolioCode = ? LIMIT 1)"+
						",(SELECT assetId FROM Asset WHERE assetCode = ? LIMIT 1),?);";
		PreparedStatement ps=null;
		try {
			ps = conn.prepareStatement(query);
			ps.setString(1,portfolioCode);
			ps.setString(2,assetCode);
			ps.setDouble(3,value);
			ps.executeUpdate();
		} catch (SQLException e) {
			log.log(Level.SEVERE, "SQL exception line 572");
		}
		DatabaseAbstraction.disconnect(conn, ps);
	}
	
	
}


	

