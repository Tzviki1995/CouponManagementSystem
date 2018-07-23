package com.abigail.couponsproject.facade;

import com.abigail.couponsproject.exceptions.CouponSystemException;

public interface CouponClientFacade {
	
	public CouponClientFacade login(String username, String password, ClientType clientType) throws CouponSystemException;

}//ICoupnClientFacade
