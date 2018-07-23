package com.abigail.couponsproject.facade;

import java.util.Collection;

import com.abigail.couponsproject.DB.DAO.CompanyDao;
import com.abigail.couponsproject.DB.DAO.CouponDao;
import com.abigail.couponsproject.DB.DAO.CustomerDao;
import com.abigail.couponsproject.beans.Company;
import com.abigail.couponsproject.beans.Coupon;
import com.abigail.couponsproject.beans.Customer;
import com.abigail.couponsproject.exceptions.CouponSystemException;
import com.abigail.couponsproject.exceptions.ExceptionType;

public class AdminFacade implements CouponClientFacade {

	private CompanyDao companyDao;
	private CustomerDao customerDao;
	private CouponDao couponDao;
	private static final String USERNAME = "admin";
	private static final String PASSWORD = "1234";

	public AdminFacade(CompanyDao companyDao, CustomerDao customerDao, CouponDao couponDao) {
		this.companyDao = companyDao;
		this.customerDao = customerDao;
		this.couponDao = couponDao;
	}// c-tor

	public void createCompany(Company company) throws CouponSystemException {
		Collection<Company> companies = getAllCompanies();
		for (Company c : companies) {
			if (c.getCompanyName().equals(company.getCompanyName())) {
				throw new CouponSystemException(ExceptionType.ILLEGAL_COMPANY_CREATION,
						"Too bad! The company name you've chosen is taken. Please come up with something more original when you're done crying");
			}
		}
		companyDao.createCompany(company);
	}// createCompany

	public void removeCompany(Company company) throws CouponSystemException {
		Collection<Coupon> coupons = company.getCoupons();

		for (Coupon coupon : coupons) {
			couponDao.removeCouponFromCompany(company, coupon);
			couponDao.removeCoupon(coupon);
		}
		companyDao.removeCompany(company);
	}// removeCompany

	public void updateCompany(Company company) throws CouponSystemException {
		Company compFromDb = companyDao.getCompany(company.getCompanyId());

		if (!company.getCompanyName().equals(compFromDb.getCompanyName())) {
			throw new CouponSystemException(ExceptionType.ILLEGAL_COMPANY_UPDATE,
					"Oops! Changing the company name is forbidden. They're stuck with the name for ever");
		}

		companyDao.updateCompany(company);
	}// updateCompany

	public Company getCompany(long companyId) throws CouponSystemException {
		return companyDao.getCompany(companyId);
	}// getCompany

	public Collection<Company> getAllCompanies() throws CouponSystemException {
		return companyDao.getAllCompanies();
	}// getAllCompanies

	public void createCustomer(Customer customer) throws CouponSystemException {
		Collection<Customer> customers = getAllCustomers();
		for (Customer c : customers) {
			if (c.getCustomerName() == customer.getCustomerName()) {
				throw new CouponSystemException(ExceptionType.ILLEGAL_CUSTOMER_CREATION,
						"Oops! This customer name is taken. Please choose a different name");
			}
		}
		customerDao.createCustomer(customer);
	}// createCustomer

	public void removeCustomer(Customer customer) throws CouponSystemException {
		Collection<Coupon> coupons = customerDao.getCoupons(customer.getCustomerId());

		if (coupons != null) {
			for (Coupon coupon : coupons) {
				customerDao.removeCouponFromCustomer(customer, coupon);
				couponDao.addToStock(coupon);
			}
		}

		customerDao.removeCustomer(customer);
	}// removeCustomer

	public void updateCustomer(Customer customer) throws CouponSystemException {
		Customer custFromDb = customerDao.getCustomer(customer.getCustomerId());

		if (!custFromDb.getCustomerName().equals(customer.getCustomerName())) {
			throw new CouponSystemException(ExceptionType.ILLEGAL_CUSTOMER_UPDATE,
					"Sorry dude! Changing the customer name is forbidden. They're stuck with the name for ever");
		}
		customerDao.updateCustomer(customer);
	}// updateCustomer

	public Customer getCustomer(long customerId) throws CouponSystemException {
		return customerDao.getCustomer(customerId);
	}// getCustomer

	public Collection<Customer> getAllCustomers() throws CouponSystemException {
		return customerDao.getAllCustomers();
	}// getAllCustomers

	@Override
	public CouponClientFacade login(String username, String password, ClientType clientType)
			throws CouponSystemException {

		if (!clientType.equals(ClientType.ADMIN)) {
			throw new CouponSystemException(ExceptionType.LOGIN_FAILED,
					"Uh oh! Failed to login as admin. Please make sure you're logging into the right workspace ad try again.");
		}
		if (!(username.equals(USERNAME) && password.equals(PASSWORD))) {
			throw new CouponSystemException(ExceptionType.LOGIN_FAILED,
					"Aw man! Failed to log in. Please check your username and password and try again.");
		}
		return this;
	}// login

}// AdminFacade
