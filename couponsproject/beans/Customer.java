package com.abigail.couponsproject.beans;

import java.util.ArrayList;
import java.util.Collection;

public class Customer {
	private long customerId;
	private String customerName;
	private String password;
	private Collection<Coupon> coupons;

	public Customer(long customerId, String customerName, String password) {
		this.customerId = customerId;
		this.customerName = customerName;
		this.password = password;
		this.coupons = new ArrayList<>();
	}// c-tor

	public long getCustomerId() {
		return customerId;
	}// getCustomerId

	public String getCustomerName() {
		return customerName;
	}// getCustomerName

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}// setCustomerName

	public String getPassword() {
		return password;
	}// getPassword

	public void setPassword(String password) {
		this.password = password;
	}// setPassword

	public Collection<Coupon> getCoupons() {
		return coupons;
	}// getCoupons

	public void setCoupons(Collection<Coupon> coupons) {
		this.coupons = coupons;
	}// setCoupons

	@Override
	public String toString() {
		return customerId + "	" + customerName + "	" + coupons.size() + " valid coupons";
	}// toString

}// Customer
