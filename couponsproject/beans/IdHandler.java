package com.abigail.couponsproject.beans;

import com.abigail.couponsproject.DB.DAO.IdHandlerDao;
import com.abigail.couponsproject.DB.DBDAO.IdHandlerDbDao;
import com.abigail.couponsproject.exceptions.CouponSystemException;

public class IdHandler {

	private static IdHandlerDao idHandlerDao = new IdHandlerDbDao();

	public static long getNextCompanyId() throws CouponSystemException {
		return idHandlerDao.getLastCompanyId();
	}// getLastCompanyId

	public static long getNextCouponyId() throws CouponSystemException {
		return idHandlerDao.getLastCouponId();
	}// getLastCouponyId

	public static long getNextCustomerId() throws CouponSystemException {
		return idHandlerDao.getLastCustomerId();
	}// getLastCustomerId

}// IdHandler
