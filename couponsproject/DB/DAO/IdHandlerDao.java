package com.abigail.couponsproject.DB.DAO;

import com.abigail.couponsproject.exceptions.CouponSystemException;

public interface IdHandlerDao {

	long getLastCompanyId() throws CouponSystemException;

	long incrementLastCompanyId() throws CouponSystemException;

	long getLastCouponId() throws CouponSystemException;

	long incrementLastCouponId() throws CouponSystemException;

	long getLastCustomerId() throws CouponSystemException;

	long incrementLastCustomerId() throws CouponSystemException;

}
