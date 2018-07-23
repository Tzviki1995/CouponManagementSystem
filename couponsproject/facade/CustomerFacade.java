package com.abigail.couponsproject.facade;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import com.abigail.couponsproject.DB.DAO.CouponDao;
import com.abigail.couponsproject.DB.DAO.CustomerDao;
import com.abigail.couponsproject.beans.Coupon;
import com.abigail.couponsproject.beans.CouponType;
import com.abigail.couponsproject.beans.Customer;
import com.abigail.couponsproject.exceptions.CouponSystemException;
import com.abigail.couponsproject.exceptions.ExceptionType;

public class CustomerFacade implements CouponClientFacade {

	private CustomerDao customerDao;
	private CouponDao couponDao;
	private Customer customer;

	public CustomerFacade(Customer customer, CustomerDao customerDao, CouponDao couponDao) {
		this.customerDao = customerDao;
		this.couponDao = couponDao;
		this.customer = customer;
	}// c-tor

	public void purchaseCoupon(Coupon coupon) throws CouponSystemException {
		Coupon sameCouponFromDb = couponDao.getCoupon(coupon.getCouponId());
		Date today = new Date(System.currentTimeMillis());

		if (sameCouponFromDb.getAmount() <= 0) {
			throw new CouponSystemException(ExceptionType.COUPON_OUT_OF_STOCK,
					"Aplogies, it seems like this coupon is out of stock. Better luck next time!");

		} else if (sameCouponFromDb.getEndDate().before(today)) {
			throw new CouponSystemException(ExceptionType.COUPON_EXPIRED,
					"We're sorry, but the coupon you've tried to purchase has expired. Better luck next time!");
		}
		for (Coupon c : customerDao.getCoupons(customer.getCustomerId())) {
			if (c.getCouponId() == coupon.getCouponId()) {
				throw new CouponSystemException(ExceptionType.ILLEGAL_PURCHASE,
						"Hey! Leave some coupons for the rest of us! A coupon can not be purchased twice.");
			}
		}

		customerDao.purchaseCoupon(customer, coupon);
		couponDao.subtractFromStock(sameCouponFromDb);
	}// purchaseCoupon

	public void deleteCooupon(Coupon coupon) throws CouponSystemException {
		customerDao.removeCouponFromCustomer(customer, coupon);
		couponDao.addToStock(coupon);
	}// deleteCoupon

	public Collection<Coupon> getAllPurchasedCoupons() throws CouponSystemException {
		return customerDao.getCoupons(customer.getCustomerId());
	}// getAllPurchasedCoupons

	public Collection<Coupon> getAllPurchasedCouponsByType(CouponType type) throws CouponSystemException {
		Collection<Coupon> coupons = getAllPurchasedCoupons();
		Collection<Coupon> couponsByType = new ArrayList<>();

		for (Coupon coupon : coupons) {
			if (coupon.getType() == type) {
				couponsByType.add(coupon);
			}
		}
		return couponsByType;
	}// getAllPurchasedCouponsByType

	public Collection<Coupon> getAllPurchasedCouponsByPrice(double price) throws CouponSystemException {
		Collection<Coupon> coupons = getAllPurchasedCoupons();
		Collection<Coupon> couponsByPrice = new ArrayList<>();

		for (Coupon coupon : coupons) {
			if (coupon.getPrice() <= price) {
				couponsByPrice.add(coupon);
			}
		}

		return couponsByPrice;
	}// getAllPurchasedCouponsByPrice

	@Override
	public CouponClientFacade login(String username, String password, ClientType clientType)
			throws CouponSystemException {

		if (!clientType.equals(ClientType.CUSTOMER)) {
			throw new CouponSystemException(ExceptionType.LOGIN_FAILED, "Uh oh! Failed to login as customer " + username
					+ ". Please make sure you're logging into the right workspace ad try again.");
		}
		if (!customerDao.login(username, password)) {
			throw new CouponSystemException(ExceptionType.LOGIN_FAILED,
					"Aw man! Failed to log in. Please check your username and password and try again.");
		}
		return this;
	}// login

}// CustomerFacade
