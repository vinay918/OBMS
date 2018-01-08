package com.sdb;

import java.util.Comparator;

/**
 * Comparator that compares portfolios according to manager type and name ordering
 */

public class ManagerComparator implements Comparator<Portfolio> {

	@Override
	public int compare(Portfolio o1, Portfolio o2) {
		if(o2==null){
			return 0;
		}
		String managerType1=o1.getManager().getType();
		String managerType2=o2.getManager().getType();
		if(managerType1.trim().equals("J") && managerType2.trim().equals("E")){
			return 1;
		}
		else if(managerType2.trim().equals("J") && managerType1.trim().equals("E")){
			return -1;
		}
		else{
			return o1.getManagerName().compareTo(o2.getManagerName());
	}

	}
	
}
