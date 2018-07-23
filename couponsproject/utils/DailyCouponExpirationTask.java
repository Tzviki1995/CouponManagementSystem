package com.abigail.couponsproject.utils;

import java.util.Collection;
import java.util.Date;

import com.abigail.couponsproject.DB.DAO.CompanyDao;
import com.abigail.couponsproject.DB.DAO.CouponDao;
import com.abigail.couponsproject.DB.DAO.CustomerDao;
import com.abigail.couponsproject.beans.Company;
import com.abigail.couponsproject.beans.Coupon;
import com.abigail.couponsproject.beans.Customer;
import com.abigail.couponsproject.exceptions.CouponSystemException;

public class DailyCouponExpirationTask extends Thread {

	private CouponDao couponDao;
	private CompanyDao companyDao;
	private CustomerDao customerDao;
	private boolean running;

	public DailyCouponExpirationTask(CouponDao couponDao, CompanyDao companyDao, CustomerDao customerDao) {
		this.couponDao = couponDao;
		this.companyDao = companyDao;
		this.customerDao = customerDao;
		running = false;
	}// c-tor

	@Override
	public void run() {

		running = true;

		try {
			Collection<Coupon> coupons = couponDao.getAllCoupons();
			Date today = new Date();

			while (running) {

				for (Coupon coupon : coupons) {

					if (coupon.getEndDate().before(today)) {

						Collection<Long> customerIds = couponDao.getIdOfCouponsCustomers(coupon);

						for (Long customerId : customerIds) {
							Customer customer = customerDao.getCustomer(customerId);
							customerDao.removeCouponFromCustomer(customer, coupon);
						}

						Company couponsCompany = companyDao.getCompany(couponDao.getCompanyId(coupon));
						couponDao.removeCouponFromCompany(couponsCompany, coupon);

						couponDao.removeCoupon(coupon);
					}
				}

				sleep(24 * 60 * 60 * 1000);
			}

		} catch (CouponSystemException | InterruptedException e) {
			e.printStackTrace();
		}
	}// run

	public boolean isRunning() {
		return running;
	}// isRunning

	public void stopTask() {
		running = false;
	}// stopTask

}// DailyCouponExpirationTask
