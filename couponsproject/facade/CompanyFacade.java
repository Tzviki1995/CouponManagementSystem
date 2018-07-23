package com.abigail.couponsproject.facade;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import com.abigail.couponsproject.DB.DAO.CompanyDao;
import com.abigail.couponsproject.DB.DAO.CouponDao;
import com.abigail.couponsproject.DB.DAO.CustomerDao;
import com.abigail.couponsproject.beans.Company;
import com.abigail.couponsproject.beans.Coupon;
import com.abigail.couponsproject.beans.CouponType;
import com.abigail.couponsproject.beans.Customer;
import com.abigail.couponsproject.exceptions.CouponSystemException;
import com.abigail.couponsproject.exceptions.ExceptionType;

public class CompanyFacade implements CouponClientFacade {
	private CouponDao couponDao;
	private CompanyDao companyDao;
	private CustomerDao customerDao;
	private Company company;

	public CompanyFacade(Company company, CouponDao couponDao, CompanyDao companyDao, CustomerDao customerDao) {
		this.couponDao = couponDao;
		this.companyDao = companyDao;
		this.customerDao = customerDao;
		this.company = company;
	}// c-tor

	public void createCoupon(Coupon coupon) throws CouponSystemException {
		Collection<Coupon> coupons = getAllCoupons();
		for (Coupon c : coupons) {
			if (c.getTitle().equals(coupon.getTitle())) {
				throw new CouponSystemException(ExceptionType.ILLEGAL_COUPON_CREATION,
						"DUDEEEE you already have a coupon with the same title, you can't do that!");
			}
		}

		couponDao.createCoupon(coupon);
		couponDao.addCouponToCompany(company, coupon);
	}// createCoupon

	public void removeCoupon(Coupon coupon) throws CouponSystemException {
		Collection<Customer> customers = customerDao.getCustomersByCoupon(coupon);

		if (customers != null) {
			for (Customer customer : customers) {
				customerDao.removeCouponFromCustomer(customer, coupon);
			}
		}

		couponDao.removeCouponFromCompany(company, coupon);
		couponDao.removeCoupon(coupon);
	}// removeCoupon

	public void updateCoupon(Coupon coupon) throws CouponSystemException {

		if (couponDao.getCoupon(coupon.getCouponId()) == null) {
			throw new CouponSystemException(ExceptionType.ILLEGAL_COUPON_UPDATE,
					"Oops! You've tried to update a coupon that doesen't exist or to update the coupon's ID. Please try again with a legal update.");
		}

		couponDao.updateCoupon(coupon);
	}// updateCoupon

	public Coupon getCoupon(long couponId) throws CouponSystemException {
		return couponDao.getCoupon(couponId);
	}// getCoupon

	public Collection<Coupon> getAllCoupons() throws CouponSystemException {
		return companyDao.getCoupons(company.getCompanyId());
	}// getAllCoupons

	public Collection<Coupon> getCouponByType(CouponType couponType) throws CouponSystemException {
		Collection<Coupon> coupons = new ArrayList<>();
		Collection<Coupon> everyonesCoupons = couponDao.getCouponByType(couponType);

		for (Coupon coupon : everyonesCoupons) {
			if (couponDao.getCompanyId(coupon) == company.getCompanyId()) {
				coupons.add(coupon);
			}
		}

		return coupons;
	}// getCouponByType

	public Collection<Coupon> getCouponsInBudget(double budget) throws CouponSystemException {
		Collection<Coupon> couponsInBudget = new ArrayList<>();
		Collection<Coupon> allCoupons = companyDao.getCoupons(company.getCompanyId());

		for (Coupon coupon : allCoupons) {
			if (coupon.getPrice() <= budget) {
				couponsInBudget.add(coupon);
			}
		}

		return couponsInBudget;
	}// getCouponsInBudget

	public Collection<Coupon> getCouponsBeforeDate(Date date) throws CouponSystemException {
		Collection<Coupon> couponsBeforeDate = new ArrayList<>();
		Collection<Coupon> allCoupons = companyDao.getCoupons(company.getCompanyId());

		for (Coupon coupon : allCoupons) {
			if (coupon.getEndDate().compareTo(date) <= 0) {
				couponsBeforeDate.add(coupon);
			}
		}
		return couponsBeforeDate;
	}// getCouponsBeforeDate

	@Override
	public CouponClientFacade login(String username, String password, ClientType clientType)
			throws CouponSystemException {
		if (!clientType.equals(ClientType.COMPANY)) {
			throw new CouponSystemException(ExceptionType.LOGIN_FAILED, "Uh oh! Failed to login as company " + username
					+ ". Please make sure you're logging into the right workspace ad try again.");
		}
		if (!companyDao.login(username, password)) {
			throw new CouponSystemException(ExceptionType.LOGIN_FAILED,
					"Aw man! Failed to log in. Please check your username and password and try again.");
		}
		return this;
	}// login

}// companyFacade
