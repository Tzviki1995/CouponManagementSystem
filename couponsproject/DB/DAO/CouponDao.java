package com.abigail.couponsproject.DB.DAO;

import java.util.Collection;

import com.abigail.couponsproject.beans.Company;
import com.abigail.couponsproject.beans.Coupon;
import com.abigail.couponsproject.beans.CouponType;
import com.abigail.couponsproject.exceptions.CouponSystemException;

public interface CouponDao {
	void createCoupon(Coupon coupon) throws CouponSystemException;

	void removeCoupon(Coupon coupon) throws CouponSystemException;

	void updateCoupon(Coupon coupon) throws CouponSystemException;

	void addCouponToCompany(Company company, Coupon coupon) throws CouponSystemException;

	void removeCouponFromCompany(Company company, Coupon coupon) throws CouponSystemException;

	void subtractFromStock(Coupon coupon) throws CouponSystemException;

	Coupon getCoupon(long couponId) throws CouponSystemException;

	Collection<Long> getIdOfCouponsCustomers(Coupon coupon) throws CouponSystemException;

	Collection<Coupon> getAllCoupons() throws CouponSystemException;

	Collection<Coupon> getCouponByType(CouponType couponType) throws CouponSystemException;

	void addToStock(Coupon coupon) throws CouponSystemException;

	long getCompanyId(Coupon coupon) throws CouponSystemException;
}
