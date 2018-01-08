package com.sdb;

import java.util.Comparator;

/**
 * Comparator that compares portfolios according to total value
 */

public class ValueComparator implements Comparator<Portfolio> {

	@Override
	public int compare(Portfolio o1, Portfolio o2) {
		while(o2!=null){
		double v1=o1.getTotalValue();
		double v2=o2.getTotalValue();
			if(v1>=v2){
				return -1;
			}
			else{
				return 1;
			}
		}
		return 0;
	}

}
