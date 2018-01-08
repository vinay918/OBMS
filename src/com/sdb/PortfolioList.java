package com.sdb;

import java.util.Comparator;
import java.util.Iterator;

/**
 * This is the List ADT which is specifically designed to maintain ordering given a specific comparator
 **/


@SuppressWarnings("unchecked")
public class PortfolioList<T> implements Iterable<T> {
	
	private T[] underList;
	private int size;
	private Comparator<T> c;
	
	/**
	 * Method to implement the iterable interface
	 */
	@Override
	public Iterator<T> iterator() {
		return new Iterator<T>() {

			private int pos = 0;
			@Override
			public boolean hasNext() {
				return size>pos;
			}

			@Override
			public T next() {
				return underList[pos++];
			}
			
		};
	
		
	}
	
//	Method to return size of list
	public int getSize() {
		return this.size;
	}
	
//	Method to add object. The object gets added at the approptiate index with the help of the comparator
	public void add(T element){
		
//		Resize if necessary(Dynamic resizing)
		if(this.size == underList.length) {
			T tmp[] = (T[]) new Object[underList.length + 10];
			for(int i=0; i<underList.length; i++) {
				tmp[i] = underList[i];
			}
			underList = tmp;
		}
		
//		If-else block to determine if the item added is the first item or not
		if(this.size==0){
			underList[0]=element;
			this.size++;
		}
		else{
		int ind = 0;
		while(c.compare(element,underList[ind])>=0 && ind<this.size){
			ind++;
		}
//		Reposition elements and finally, add
		for(int i=size-1;i>=ind;i--){
			underList[i+1]=underList[i];
		}
		underList[ind]=element;
		this.size++;
		}
	}
	
//	Method to retrieve element at a given index
	public T getElementAtIndex(int index) {
		if(index < 0 || index >= size) 
			throw new IllegalArgumentException("index = "+index+" is out of bounds");
		else
			return this.underList[index];
	}
	
//	Method to remove element at a given index
	public void removeElementAtIndex(int index) {

		if(index < 0 || index >= size) 
			throw new IllegalArgumentException("index = "+index+" is out of bounds");
		
		for(int i=index; i<size-1; i++) {
			this.underList[i] = this.underList[i+1];
		}
		this.size--;
		
		if(size < underList.length - 10) {
			//resize
			T tmp[] = (T[]) new Object[this.underList.length - 10];
			for(int i=0; i<size; i++) {
				tmp[i] = this.underList[i];
			}
			this.underList = tmp;
		}
		
	}
	
//	Constructor which requires a comparator to be instantiated
	public PortfolioList(Comparator<T> c){
		this.underList = (T[]) new Object[10];
		this.size=0;
		this.c=c;
	}
	
//	toString method
	public String toString() {
		if(this.size == 0) {
			return "[empty]";
		}
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		for(int i=0; i<this.size-1; i++) {
			sb.append(this.underList[i]);
			sb.append(", ");
		}
		sb.append(this.underList[this.size-1]);
		sb.append("]");
		return sb.toString();
	}
	
	
	

}
