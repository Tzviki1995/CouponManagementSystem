package com.abigail.couponsproject.DB.DBDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import com.abigail.couponsproject.DB.ConnectionPool;
import com.abigail.couponsproject.DB.DAO.CompanyDao;
import com.abigail.couponsproject.beans.Company;
import com.abigail.couponsproject.beans.Coupon;
import com.abigail.couponsproject.beans.CouponType;
import com.abigail.couponsproject.exceptions.CouponSystemException;
import com.abigail.couponsproject.exceptions.ExceptionType;

public class CompanyDbDao implements CompanyDao {
	private ConnectionPool pool;

	public CompanyDbDao() {
		pool = ConnectionPool.getInstance();
	}// c-tor

	@Override
	public void createCompany(Company company) throws CouponSystemException {

		Connection conn = null;

		try {
			conn = pool.getConnection();
			PreparedStatement stmt = conn.prepareStatement(
					"insert into Companies (COMPANY_ID, COMPANY_NAME, PASSWORD, EMAIL) " + "values (?, ?, ?, ?)");
			stmt.setLong(1, company.getCompanyId());
			stmt.setString(2, company.getCompanyName());
			stmt.setString(3, company.getPassword());
			stmt.setString(4, company.getEmail());
			stmt.execute();

		} catch (InterruptedException | SQLException e) {
			throw new CouponSystemException(e, ExceptionType.COMPANY_CREATION_FAILED,
					"Oopsie daisy! an error has occured while creating " + company.getCompanyName()
							+ ", please try again later");
		} finally {
			try {
				pool.returnConnection(conn);
			} catch (Exception e) {
				throw new CouponSystemException(e, ExceptionType.GENERAL_ERROR,
						"Merlin's pants!! Voldemort has cursed our program and there is an unexplained error. If you're a muggle, you should probably run.");
			}
		}
	}// createCompany

	@Override
	public void removeCompany(Company company) throws CouponSystemException {
		Connection conn = null;

		try {
			conn = pool.getConnection();
			PreparedStatement stmt = conn.prepareStatement("delete from Companies where COMPANY_ID = ?");
			stmt.setLong(1, company.getCompanyId());

			stmt.execute();
		} catch (InterruptedException | SQLException e) {
			throw new CouponSystemException(e, ExceptionType.COMPANY_REMOVAL_FAILED,
					"Oopsie daisy! Something has occured while removing company " + company.getCompanyName()
							+ ", please try again later");
		} finally {
			try {
				pool.returnConnection(conn);
			} catch (Exception e) {
				throw new CouponSystemException(e, ExceptionType.GENERAL_ERROR,
						"Merlin's pants!! Voldemort has cursed our program and there is an unexplained error. If you're a muggle, you should probably run.");
			}
		}
	}// removeCompany

	@Override
	public void updateCompany(Company company) throws CouponSystemException {
		Connection conn = null;

		try {
			conn = pool.getConnection();
			PreparedStatement stmt = conn
					.prepareStatement("update Companies " + "set PASSWORD = ?, EMAIL = ? " + "where COMPANY_ID = ?");
			stmt.setString(1, company.getPassword());
			stmt.setString(2, company.getEmail());
			stmt.setLong(3, company.getCompanyId());

			stmt.execute();
		} catch (InterruptedException | SQLException e) {
			throw new CouponSystemException(e, ExceptionType.COMAPNY_UPDATE_FAILED,
					"Oh blimey! We're unable to update company " + company.getCompanyName()
							+ "'s detailes at the moment. please try again later");
		} finally {
			try {
				pool.returnConnection(conn);
			} catch (Exception e) {
				throw new CouponSystemException(e, ExceptionType.GENERAL_ERROR,
						"Merlin's pants!! Voldemort has cursed our program and there is an unexplained error. If you're a muggle, you should probably run.");
			}
		}
	}// updateCompany

	@Override
	public Company getCompany(long companyId) throws CouponSystemException {
		Connection conn = null;
		Company company = null;

		try {
			conn = pool.getConnection();
			PreparedStatement stmt = conn.prepareStatement("select * from Companies where COMPANY_ID = ?");
			stmt.setLong(1, companyId);
			ResultSet result = stmt.executeQuery();

			result.next();

			company = new Company(result.getLong("COMPANY_ID"), result.getString("COMPANY_NAME"),
					result.getString("PASSWORD"), result.getString("EMAIL"));

		} catch (SQLException | InterruptedException e) {
			throw new CouponSystemException(e, ExceptionType.COMPANY_NOT_FOUND,
					"Very sorry, we are! Been unable to find your company, we have. May the force be with you next time");
		} finally {
			try {
				pool.returnConnection(conn);
			} catch (Exception e) {
				throw new CouponSystemException(e, ExceptionType.GENERAL_ERROR,
						"Merlin's pants!! Voldemort has cursed our program and there is an unexplained error. If you're a muggle, you should probably run.");
			}
		}
		return company;
	}// getCompany

	@Override
	public Company getCompanyByName(String name) throws CouponSystemException {
		Connection conn = null;
		Company company = null;

		try {
			conn = pool.getConnection();
			PreparedStatement stmt = conn.prepareStatement("select * from Companies where COMPANY_NAME = ?");
			stmt.setString(1, name);
			ResultSet result = stmt.executeQuery();

			result.next();

			company = new Company(result.getLong("COMPANY_ID"), result.getString("COMPANY_NAME"),
					result.getString("PASSWORD"), result.getString("EMAIL"));

		} catch (SQLException | InterruptedException e) {
			throw new CouponSystemException(e, ExceptionType.COMPANY_NOT_FOUND,
					"Very sorry, we are! Been unable to find your company, we have. May the force be with you next time");
		} finally {
			try {
				pool.returnConnection(conn);
			} catch (Exception e) {
				throw new CouponSystemException(e, ExceptionType.GENERAL_ERROR,
						"Merlin's pants!! Voldemort has cursed our program and there is an unexplained error. If you're a muggle, you should probably run.");
			}
		}
		return company;
	}// getCustomerByName

	@Override
	public Collection<Company> getAllCompanies() throws CouponSystemException {
		Connection conn = null;
		Collection<Company> companies = new ArrayList<>();
		ResultSet companyRow = null;

		try {
			conn = pool.getConnection();
			PreparedStatement stmt = conn.prepareStatement("select * from Companies");
			companyRow = stmt.executeQuery();

			while (companyRow.next()) {
				companies.add(new Company(companyRow.getLong("COMPANY_ID"), companyRow.getString("COMPANY_NAME"),
						companyRow.getString("PASSWORD"), companyRow.getString("EMAIL")));
			}
		} catch (InterruptedException | SQLException e) {
			throw new CouponSystemException(e, ExceptionType.COMPANY_NOT_FOUND,
					"Woops! An error has occured and the companeis can not be found. Please try again later, we're sure that they will come back soon");
		} finally {
			try {
				pool.returnConnection(conn);
			} catch (Exception e) {
				throw new CouponSystemException(e, ExceptionType.GENERAL_ERROR,
						"Merlin's pants!! Voldemort has cursed our program and there is an unexplained error. If you're a muggle, you should probably run.");
			}
		}

		return companies;
	}// getAllCompanies

	@Override
	public Collection<Coupon> getCoupons(long companyId) throws CouponSystemException {
		Connection conn = null;
		Collection<Coupon> coupons = new ArrayList<>();
		ResultSet couponRow = null;

		try {
			conn = pool.getConnection();
			PreparedStatement stmt = conn.prepareStatement(
					"select * " + "from Companies_Coupons cc join Coupons c on cc.COUPON_ID = c.COUPON_ID "
							+ "where COMPANY_ID = ?");
			stmt.setLong(1, companyId);
			couponRow = stmt.executeQuery();

			while (couponRow.next()) {
				coupons.add(new Coupon(couponRow.getLong("COUPON_ID"), couponRow.getString("TITLE"),
						new Date(couponRow.getDate("START_DATE").getTime()),
						new Date(couponRow.getDate("END_DATE").getTime()), couponRow.getInt("AMOUNT"),
						CouponType.valueOf(couponRow.getString("TYPE")), couponRow.getString("MESSAGE"),
						couponRow.getDouble("PRICE"), couponRow.getString("IMAGE")));
			}
		} catch (InterruptedException | SQLException e) {
			throw new CouponSystemException(e, ExceptionType.COUPON_NOT_AVAILABLE,
					"Bilmey! We're unable to find the coupons you've requested. Please try again later");
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
	public boolean login(String companyName, String password) throws CouponSystemException {
		Connection conn = null;
		boolean loginSuccessful = false;

		try {
			conn = pool.getConnection();
			PreparedStatement stmt = conn
					.prepareStatement("select * from Companies where COMPANY_NAME = ? and PASSWORD = ?");
			stmt.setString(1, companyName);
			stmt.setString(2, password);
			loginSuccessful = stmt.executeQuery().next();

		} catch (InterruptedException | SQLException e) {
			throw new CouponSystemException(e, ExceptionType.LOGIN_FAILED,
					"Hello. Our name is Inigo Montoya. Please come up with the correct username and password, or prepare to die. Just kidding (or not)! Please try again");
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

}// CompanyDBDAO
