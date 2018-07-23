package com.abigail.couponsproject.main;

import com.abigail.couponsproject.DB.ConnectionPool;
import com.abigail.couponsproject.DB.DAO.CompanyDao;
import com.abigail.couponsproject.DB.DAO.CouponDao;
import com.abigail.couponsproject.DB.DAO.CustomerDao;
import com.abigail.couponsproject.DB.DBDAO.CompanyDbDao;
import com.abigail.couponsproject.DB.DBDAO.CouponDbDao;
import com.abigail.couponsproject.DB.DBDAO.CustomerDbDao;
import com.abigail.couponsproject.beans.Company;
import com.abigail.couponsproject.beans.Customer;
import com.abigail.couponsproject.exceptions.CouponSystemException;
import com.abigail.couponsproject.exceptions.ExceptionType;
import com.abigail.couponsproject.facade.AdminFacade;
import com.abigail.couponsproject.facade.ClientType;
import com.abigail.couponsproject.facade.CompanyFacade;
import com.abigail.couponsproject.facade.CouponClientFacade;
import com.abigail.couponsproject.facade.CustomerFacade;
import com.abigail.couponsproject.utils.DailyCouponExpirationTask;

public class CouponSystem {

	private static final CouponSystem INSTANCE = new CouponSystem();
	private CouponDao couponDao;
	private CustomerDao customerDao;
	private CompanyDao companyDao;
	private DailyCouponExpirationTask task;

	private CouponSystem() {
		ConnectionPool.getInstance();

		couponDao = new CouponDbDao();
		customerDao = new CustomerDbDao();
		companyDao = new CompanyDbDao();

		task = new DailyCouponExpirationTask(couponDao, companyDao, customerDao);
		task.start();
	}// c-tor

	public static CouponSystem getInstance() {
		return INSTANCE;
	}// getInstance

	public CouponClientFacade login(String username, String password, ClientType clientType)
			throws CouponSystemException {

		switch (clientType) {
		case ADMIN:
			AdminFacade admin = new AdminFacade(companyDao, customerDao, couponDao);
			return admin.login(username, password, clientType);

		case COMPANY:
			Company company = companyDao.getCompanyByName(username);
			CompanyFacade companyFacade = new CompanyFacade(company, couponDao, companyDao, customerDao);
			return companyFacade.login(username, password, clientType);

		case CUSTOMER:
			Customer customer = customerDao.getCustomerByName(username);
			CustomerFacade customerFacade = new CustomerFacade(customer, customerDao, couponDao);
			return customerFacade.login(username, password, clientType);

		default:
			throw new CouponSystemException(ExceptionType.LOGIN_FAILED,
					"Oops! An error has caused your login to fail. Please check the details you've entered and try again.");
		}
	}// login

	public void shutdown() {
		if (task.isRunning()) {
			task.stopTask();
		}

		ConnectionPool.closeAllConnections();

	}// shutdown

}// CouponSystem
