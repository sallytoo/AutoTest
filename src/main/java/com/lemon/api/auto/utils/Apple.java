package com.lemon.api.auto.utils;

public class Apple {
	
	private int price;
	private String size;
	private int num;
	
	
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	@Override
	public String toString() {
		return "Apple [price=" + price + ", size=" + size + ", num=" + num + "]";
	}
	
	

}
