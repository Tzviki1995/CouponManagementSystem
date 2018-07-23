package com.abigail.couponsproject.beans;

import java.util.ArrayList;
import java.util.Collection;

public class Company {

	private long companyId;
	private String companyName;
	private String password;
	private String email;
	private Collection<Coupon> coupons;

	public Company(long companyId, String companyName, String password, String email) {
		this.companyId = companyId;
		this.companyName = companyName;
		this.password = password;
		this.email = email;
		coupons = new ArrayList<>();
	}// c-tor

	public long getCompanyId() {
		return companyId;
	}// getCompanyId

	public String getCompanyName() {
		return companyName;
	}// getCompanyName

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}// setCompanyName

	public String getPassword() {
		return password;
	}// getPassword

	public void setPassword(String password) {
		this.password = password;
	}// setPassword

	public String getEmail() {
		return email;
	}// getEmail

	public void setEmail(String email) {
		this.email = email;
	}// setEmail

	public Collection<Coupon> getCoupons() {
		return coupons;
	}// getCoupons

	public void setCoupons(Collection<Coupon> coupons) {
		this.coupons = coupons;
	}// setCoupons

	@Override
	public String toString() {
		return companyId + "	" + companyName + "	" + email;
	}// toString

}// Company
