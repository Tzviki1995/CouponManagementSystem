package com.abigail.couponsproject.DB.DBDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import com.abigail.couponsproject.DB.ConnectionPool;
import com.abigail.couponsproject.DB.DAO.CustomerDao;
import com.abigail.couponsproject.beans.Coupon;
import com.abigail.couponsproject.beans.CouponType;
import com.abigail.couponsproject.beans.Customer;
import com.abigail.couponsproject.exceptions.CouponSystemException;
import com.abigail.couponsproject.exceptions.ExceptionType;

public class CustomerDbDao implements CustomerDao {

	private ConnectionPool pool;

	public CustomerDbDao() {
		pool = ConnectionPool.getInstance();
	}// c-tor

	@Override
	public void createCustomer(Customer customer) throws CouponSystemException {
		Connection conn = null;

		try {
			conn = pool.getConnection();
			PreparedStatement stmt = conn.prepareStatement(
					"insert into Customers (CUSTOMER_ID, CUSTOMER_NAME, PASSWORD) " + "values (?, ?, ?)");
			stmt.setLong(1, customer.getCustomerId());
			stmt.setString(2, customer.getCustomerName());
			stmt.setString(3, customer.getPassword());

			stmt.execute();
		} catch (InterruptedException | SQLException e) {
			throw new CouponSystemException(e, ExceptionType.CUSTOMER_CREATION_FAILED,
					"Oopsie daisy! Something has occured while creating customer " + customer.getCustomerName()
							+ ". Please try again");
		} finally {
			try {
				pool.returnConnection(conn);
			} catch (Exception e) {
				throw new CouponSystemException(e, ExceptionType.GENERAL_ERROR,
						"Merlin's pants!! Voldemort has cursed our program and there is an unexplained error. If you're a muggle, you should probably run.");
			}
		}
	}// createCustomer

	@Override
	public void removeCustomer(Customer customer) throws CouponSystemException {
		Connection conn = null;

		try {
			conn = pool.getConnection();
			String query = "delete from customers where CUSTOMER_ID = ?; delete from Customers_Coupons where CUSTOMER_ID = ?";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setLong(1, customer.getCustomerId());
			stmt.setLong(2, customer.getCustomerId());

			stmt.execute();
		} catch (InterruptedException | SQLException e) {
			throw new CouponSystemException(e, ExceptionType.CUSTOMER_REMOVAL_FAILED,
					"Oopsie daisy! Something has occured while removing customer " + customer.getCustomerName()
							+ ". Please try again");
		} finally {
			try {
				pool.returnConnection(conn);
			} catch (Exception e) {
				throw new CouponSystemException(e, ExceptionType.GENERAL_ERROR,
						"Merlin's pants!! Voldemort has cursed our program and there is an unexplained error. If you're a muggle, you should probably run.");
			}
		}
	}// removeCustomer

	@Override
	public void updateCustomer(Customer customer) throws CouponSystemException {
		Connection conn = null;

		try {
			conn = pool.getConnection();
			PreparedStatement stmt = conn.prepareStatement(
					"update Customers set CUSTOMER_NAME = ?, PASSWORD = ? " + "where CUSTOMER_ID = ?");
			stmt.setString(1, customer.getCustomerName());
			stmt.setString(2, customer.getPassword());
			stmt.setLong(3, customer.getCustomerId());

			stmt.execute();
		} catch (InterruptedException | SQLException e) {
			throw new CouponSystemException(e, ExceptionType.CUSTOMER_CREATION_FAILED,
					"Oopsie daisy! Something has occured while updating customer " + customer.getCustomerName()
							+ "'s details. Please try again");
		} finally {
			try {
				pool.returnConnection(conn);
			} catch (Exception e) {
				throw new CouponSystemException(e, ExceptionType.GENERAL_ERROR,
						"Merlin's pants!! Voldemort has cursed our program and there is an unexplained error. If you're a muggle, you should probably run.");
			}
		}
	}// updateCustomer

	@Override
	public Customer getCustomer(long customerId) throws CouponSystemException {
		Connection conn = null;
		Customer customer = null;

		try {
			conn = pool.getConnection();
			PreparedStatement stmt = conn.prepareStatement("select * from Customers where CUSTOMER_ID = ?");
			stmt.setLong(1, customerId);
			ResultSet customerRow = stmt.executeQuery();
			customerRow.next();

			customer = new Customer(customerRow.getLong("CUSTOMER_ID"), customerRow.getString("CUSTOMER_NAME"),
					customerRow.getString("PASSWORD"));

		} catch (InterruptedException | SQLException e) {
			throw new CouponSystemException(e, ExceptionType.CUSTOMER_NOT_FOUND,
					"Oopsie daisy! We're unable to find a customer with the ID " + customerId
							+ ". Please make sure that the customer" + " is not hiding from the police and try again");
		} finally {
			try {
				pool.returnConnection(conn);
			} catch (Exception e) {
				throw new CouponSystemException(e, ExceptionType.GENERAL_ERROR,
						"Merlin's pants!! Voldemort has cursed our program and there is an unexplained error. If you're a muggle, you should probably run.");
			}
		}

		return customer;
	}// getCustomer

	@Override
	public Customer getCustomerByName(String name) throws CouponSystemException {
		Connection conn = null;
		Customer customer = null;

		try {
			conn = pool.getConnection();
			PreparedStatement stmt = conn.prepareStatement("select * from Customers where CUSTOMER_NAME = ?");
			stmt.setString(1, name);
			ResultSet customerRow = stmt.executeQuery();
			customerRow.next();

			customer = new Customer(customerRow.getLong("CUSTOMER_ID"), customerRow.getString("CUSTOMER_NAME"),
					customerRow.getString("PASSWORD"));

		} catch (InterruptedException | SQLException e) {
			throw new CouponSystemException(e, ExceptionType.CUSTOMER_NOT_FOUND,
					"Oopsie daisy! We're unable to find a customer called " + name
							+ ". Please make sure that the customer" + " is not hiding from the police and try again");
		} finally {
			try {
				pool.returnConnection(conn);
			} catch (Exception e) {
				throw new CouponSystemException(e, ExceptionType.GENERAL_ERROR,
						"Merlin's pants!! Voldemort has cursed our program and there is an unexplained error. If you're a muggle, you should probably run.");
			}
		}

		return customer;
	}// getCustomerByName

	@Override
	public Collection<Customer> getAllCustomers() throws CouponSystemException {
		Connection conn = null;
		Collection<Customer> customers = new ArrayList<>();
		ResultSet rows = null;

		try {
			conn = pool.getConnection();
			PreparedStatement stmt = conn.prepareStatement("select * from Customers");
			rows = stmt.executeQuery();

			while (rows.next()) {
				customers.add(new Customer(rows.getLong("CUSTOMER_ID"), rows.getString("CUSTOMER_NAME"),
						rows.getString("PASSWORD")));
			}
		} catch (InterruptedException | SQLException e) {
			throw new CouponSystemException(e, ExceptionType.CUSTOMER_NOT_FOUND,
					"Oh no! We're unable to find customers. You may be out of business, but it is also likely that an error has occured. Please try again");
		} finally {
			try {
				pool.returnConnection(conn);
			} catch (Exception e) {
				throw new CouponSystemException(e, ExceptionType.GENERAL_ERROR,
						"Merlin's pants!! Voldemort has cursed our program and there is an unexplained error. If you're a muggle, you should probably run.");
			}
		}

		return customers;
	}// getAllCustomers

	@Override
	public Collection<Coupon> getCoupons(long customerId) throws CouponSystemException {
		Connection conn = null;
		Collection<Coupon> coupons = new ArrayList<>();
		ResultSet rows = null;

		try {
			conn = pool.getConnection();
			PreparedStatement stmt = conn.prepareStatement("select * " + "from Coupons c join Customers_Coupons cc "
					+ "on c.COUPON_ID = cc.COUPON_ID " + "where cc.CUSTOMER_ID = ?");
			stmt.setLong(1, customerId);
			rows = stmt.executeQuery();

			while (rows.next()) {
				coupons.add(new Coupon(rows.getLong("COUPON_ID"), rows.getString("TITLE"),
						new Date(rows.getDate("START_DATE").getTime()), new Date(rows.getDate("END_DATE").getTime()),
						rows.getInt("AMOUNT"), CouponType.valueOf(rows.getString("TYPE")), rows.getString("MESSAGE"),
						rows.getDouble("PRICE"), rows.getString("IMAGE")));
			}
		} catch (InterruptedException | SQLException e) {
			throw new CouponSystemException(e, ExceptionType.COUPON_NOT_AVAILABLE,
					"Aww man, looks like the dingo ate your coupons! Please try again later");
		} finally {
			try {
				pool.returnConnection(conn);
			} catch (Exception e) {
				throw new CouponSystemException(e, ExceptionType.GENERAL_ERROR,
						"Merlin's pants!! Voldemort has cursed our program and there is an unexplained error. If you're a muggle, you should probably run.");
			}
		}

		return coupons;
	}// getCoupons

	@Override
	public void purchaseCoupon(Customer customer, Coupon coupon) throws CouponSystemException {
		Connection conn = null;

		try {
			conn = pool.getConnection();
			PreparedStatement stmt = conn
					.prepareStatement("insert into Customers_coupons (CUSTOMER_ID, COUPON_ID) values (?,?)");
			stmt.setLong(1, customer.getCustomerId());
			stmt.setLong(2, coupon.getCouponId());

			stmt.execute();
		} catch (InterruptedException | SQLException e) {
			throw new CouponSystemException(e, ExceptionType.GENERAL_ERROR,
					"Oopsie daisy! An error has occured while purchasing this coupon. Please try again when you're done crying");
		} finally {
			try {
				pool.returnConnection(conn);
			} catch (Exception e) {
				throw new CouponSystemException(e, ExceptionType.GENERAL_ERROR,
						"Merlin's pants!! Voldemort has cursed our program and there is an unexplained error. If you're a muggle, you should probably run.");
			}
		}
	}// purchaseCoupon

	@Override
	public void removeCouponFromCustomer(Customer customer, Coupon coupon) throws CouponSystemException {
		Connection conn = null;

		try {
			conn = pool.getConnection();
			PreparedStatement stmt = conn
					.prepareStatement("delete Customers_Coupons where CUSTOMER_ID = ? and COUPON_ID = ?");
			stmt.setLong(1, customer.getCustomerId());
			stmt.setLong(2, coupon.getCouponId());

			stmt.execute();
		} catch (InterruptedException | SQLException e) {
			throw new CouponSystemException(e, ExceptionType.CUSTOMER_CREATION_FAILED,
					"Oopsie daisy! We're unable to free customer " + customer.getCustomerName() + " from the coupon \""
							+ coupon.getTitle() + "\". Please try again");
		} finally {
			try {
				pool.returnConnection(conn);
			} catch (Exception e) {
				throw new CouponSystemException(e, ExceptionType.GENERAL_ERROR,
						"Merlin's pants!! Voldemort has cursed our program and there is an unexplained error. If you're a muggle, you should probably run.");
			}
		}
	}// removeCouponFromCustomer

	@Override
	public Collection<Customer> getCustomersByCoupon(Coupon coupon) throws CouponSystemException {
		Collection<Customer> customers = new ArrayList<>();
		Connection conn = null;
		ResultSet rows = null;

		try {
			conn = pool.getConnection();
			PreparedStatement stmt = conn.prepareStatement(
					"select * from Customers_Coupons cc join Customers c on cc.CUSTOMER_ID = c.CUSTOMER_ID where COUPON_ID = ?");
			stmt.setLong(1, coupon.getCouponId());

			rows = stmt.executeQuery();

			while (rows.next()) {
				customers.add(new Customer(rows.getLong("CUSTOMER_ID"), rows.getString("CUSTOMER_NAME"),
						rows.getString("PASSWORD")));
			}
		} catch (Exception e) {
			throw new CouponSystemException(e, ExceptionType.CUSTOMER_NOT_FOUND,
					"Oops! Something must have went wrong. Please try again later");
		} finally {
			try {
				pool.returnConnection(conn);
			} catch (Exception e) {
				throw new CouponSystemException(e, ExceptionType.GENERAL_ERROR,
						"Merlin's pants!! Voldemort has cursed our program and there is an unexplained error. If you're a muggle, you should probably run.");
			}
		}
		return customers;
	}// getCustomersByCoupon

	@Override
	public boolean login(String customerName, String password) throws CouponSystemException {
		Connection conn = null;
		boolean loginSuccessful = false;

		try {
			conn = pool.getConnection();
			PreparedStatement stmt = conn
					.prepareStatement("select * from Customers where CUSTOMER_NAME = ?, PASSWORD = ?");
			stmt.setString(1, customerName);
			stmt.setString(2, password);
			loginSuccessful = stmt.executeQuery().next();

		} catch (InterruptedException | SQLException e) {
			throw new CouponSystemException(e, ExceptionType.LOGIN_FAILED,
					"We're having some trouble loging you in. You've probably forgot your name again, or your password."
							+ " Chill a little with whatever you're taking and try again");
		} finally {
			try {
				pool.returnConnection(conn);
			} catch (Exception e) {
				throw new CouponSystemException(e, ExceptionType.GENERAL_ERROR,
						"Merlin's pants!! Voldemort has cursed our program and there is an unexplained error. If you're a muggle, you should probably run.");
			}
		}
		return loginSuccessful;
	}// login

}// CustomerDBDAO
