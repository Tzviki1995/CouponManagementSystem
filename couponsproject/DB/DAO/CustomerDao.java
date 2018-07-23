package com.abigail.couponsproject.DB.DAO;

import java.util.Collection;

import com.abigail.couponsproject.beans.Coupon;
import com.abigail.couponsproject.beans.Customer;
import com.abigail.couponsproject.exceptions.CouponSystemException;

public interface CustomerDao {
	void createCustomer(Customer customer) throws CouponSystemException;

	void removeCustomer(Customer customer) throws CouponSystemException;

	void updateCustomer(Customer customer) throws CouponSystemException;

	Customer getCustomer(long customerId) throws CouponSystemException;

	Customer getCustomerByName(String name) throws CouponSystemException;

	Collection<Customer> getAllCustomers() throws CouponSystemException;

	Collection<Coupon> getCoupons(long customerId) throws CouponSystemException;

	boolean login(String cusomerName, String password) throws CouponSystemException;

	void purchaseCoupon(Customer customer, Coupon coupon) throws CouponSystemException;

	void removeCouponFromCustomer(Customer customer, Coupon coupon) throws CouponSystemException;

	Collection<Customer> getCustomersByCoupon(Coupon coupon) throws CouponSystemException;

	// TODO: add extra methods on dbdao to interfaces
}
