package com.sdb;
import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class FileIn {
// This class is to read in data files and return an array list of strings 
	public static ArrayList<String> input(String s){
		ArrayList<String> input = new ArrayList<String>();
		String fileName="data/"+s;
		Scanner sc = null;
		
		try {
		  sc = new Scanner(new File(fileName));
		} catch(Exception e) {
		  throw new RuntimeException(e);
		}
		
		
        //add data file items to list
		while(sc.hasNext()){
		input.add(sc.nextLine());  
		} 
		  
		//close scanner
		sc.close();
		
		return input;
		
	}
	
//	polymorphic methods to delimit strings
	public static String[] stringDel(String s,String del, int index){
		String[] output = s.split(del,index);
		return output;
	}
	public static String[] stringDel(String s,String del){
		String[] output = s.split(del);
		return output;
	}
	
	
}
