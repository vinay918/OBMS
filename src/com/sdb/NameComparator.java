package com.sdb;

import java.util.Comparator;

/**
 * Comparator that compares portfolios according to last name/first name ordering
 */

public class NameComparator implements Comparator<Portfolio>{
	@Override
	public int compare(Portfolio o1, Portfolio o2) {
		if(o2==null){
			return 0;
		}
		return o1.getOwnerName().compareTo(o2.getOwnerName());
	}

}
