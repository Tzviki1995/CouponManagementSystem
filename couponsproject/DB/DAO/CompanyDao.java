package com.abigail.couponsproject.DB.DAO;

import java.util.Collection;

import com.abigail.couponsproject.beans.Company;
import com.abigail.couponsproject.beans.Coupon;
import com.abigail.couponsproject.exceptions.CouponSystemException;

public interface CompanyDao {
	void createCompany(Company company) throws CouponSystemException;

	void removeCompany(Company company) throws CouponSystemException;

	void updateCompany(Company company) throws CouponSystemException;

	Company getCompany(long companyId) throws CouponSystemException;

	Company getCompanyByName(String companyName) throws CouponSystemException;

	Collection<Company> getAllCompanies() throws CouponSystemException;

	Collection<Coupon> getCoupons(long companyId) throws CouponSystemException;

	boolean login(String companyName, String password) throws CouponSystemException;

}// ICompanyDAO
