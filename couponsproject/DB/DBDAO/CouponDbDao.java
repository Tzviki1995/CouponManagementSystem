package com.abigail.couponsproject.DB.DBDAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import com.abigail.couponsproject.DB.ConnectionPool;
import com.abigail.couponsproject.DB.DAO.CouponDao;
import com.abigail.couponsproject.beans.Company;
import com.abigail.couponsproject.beans.Coupon;
import com.abigail.couponsproject.beans.CouponType;
import com.abigail.couponsproject.exceptions.CouponSystemException;
import com.abigail.couponsproject.exceptions.ExceptionType;

public class CouponDbDao implements CouponDao {

	private ConnectionPool pool;

	public CouponDbDao() {
		pool = ConnectionPool.getInstance();
	}// c-tor

	@Override
	public void createCoupon(Coupon coupon) throws CouponSystemException {
		Connection conn = null;

		try {
			conn = pool.getConnection();
			PreparedStatement stmt = conn
					.prepareStatement("insert into Coupons (COUPON_ID, TITLE, START_DATE, END_DATE, "
							+ "AMOUNT, TYPE, MESSAGE, PRICE, IMAGE) " + "values (?, ?, ?, ?, ?, ?, ?, ?, ?)");
			stmt.setLong(1, coupon.getCouponId());
			stmt.setString(2, coupon.getTitle());
			stmt.setDate(3, new Date(coupon.getStartDate().getTime()));
			stmt.setDate(4, new Date(coupon.getEndDate().getTime()));
			stmt.setInt(5, coupon.getAmount());
			stmt.setString(6, coupon.getType().name());
			stmt.setString(7, coupon.getMessage());
			stmt.setDouble(8, coupon.getPrice());
			stmt.setString(9, coupon.getImage());

			stmt.execute();
                //TZVIA :should be Catch Exception e, you are not covering all cases
		} catch (InterruptedException | SQLException e) {
			throw new CouponSystemException(e, ExceptionType.COUPON_CREATION_FAILED,
					"Sorry there! An error has occured while creating your coupon. Please try again");
		} finally {
			try {//move this try catch to the connection pool
				pool.returnConnection(conn);
			} catch (Exception e) {
				throw new CouponSystemException(e, ExceptionType.GENERAL_ERROR,
						"Merlin's pants!! Voldemort has cursed our program and there is an unexplained error. If you're a muggle, you should probably run.");
			}
		}
	}// createCoupon

	@Override
	public void removeCoupon(Coupon coupon) throws CouponSystemException {
		Connection conn = null;

		try {
			conn = pool.getConnection();
			String query = "delete from Customers_Coupons where COUPON_ID = ?; delete from Companies_coupons where COUPON_ID = ?; delete from Coupons where COUPON_ID = ?";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setLong(1, coupon.getCouponId());
			stmt.setLong(2, coupon.getCouponId());
			stmt.setLong(3, coupon.getCouponId());

			stmt.execute();

		} catch (InterruptedException | SQLException e) {
			throw new CouponSystemException(e, ExceptionType.COUPON_REMOVAL_FAILED,
					"Oopsies! An error has occured while removing your coupon. Please try again");
		} finally {
			try {
				pool.returnConnection(conn);
			} catch (Exception e) {
				throw new CouponSystemException(e, ExceptionType.GENERAL_ERROR,
						"Merlin's pants!! Voldemort has cursed our program and there is an unexplained error. If you're a muggle, you should probably run.");

			}
		}
	}// removeCoupon

	@Override
	public void updateCoupon(Coupon coupon) throws CouponSystemException {
		Connection conn = null;

		try {
			conn = pool.getConnection();
			PreparedStatement stmt = conn.prepareStatement("update Coupons "
					+ "set TITLE = ?, START_DATE = ?, END_DATE = ?, AMOUNT = ?, TYPE = ?, MESSAGE = ?, PRICE = ?, IMAGE = ? "
					+ "where COUPON_ID = ?");
			stmt.setString(1, coupon.getTitle());
			stmt.setDate(2, new Date(coupon.getStartDate().getTime()));
			stmt.setDate(3, new Date(coupon.getEndDate().getTime()));
			stmt.setInt(4, coupon.getAmount());
			stmt.setString(5, coupon.getType().name());
			stmt.setString(6, coupon.getMessage());
			stmt.setDouble(7, coupon.getPrice());
			stmt.setString(8, coupon.getImage());
			stmt.setLong(9, coupon.getCouponId());

			stmt.execute();

		} catch (InterruptedException | SQLException e) {
			throw new CouponSystemException(e, ExceptionType.COUPON_UPDATE_FAILED,
					"Oh blimey! Something must have went wrong while updating your coupons details. Please try again");
		} finally {
			try {
				pool.returnConnection(conn);
			} catch (Exception e) {
				throw new CouponSystemException(e, ExceptionType.GENERAL_ERROR,
						"Merlin's pants!! Voldemort has cursed our program and there is an unexplained error. If you're a muggle, you should probably run.");
			}
		}
	}// updateCoupon

	@Override
	public void addCouponToCompany(Company company, Coupon coupon) throws CouponSystemException {
		Connection conn = null;

		try {
			conn = pool.getConnection();
			PreparedStatement stmt = conn
					.prepareStatement("insert into Companies_Coupons (COMPANY_ID, COUPON_ID) values (?, ?)");
			stmt.setLong(1, company.getCompanyId());
			stmt.setLong(2, coupon.getCouponId());

			stmt.execute();

		} catch (InterruptedException | SQLException e) {
			throw new CouponSystemException(e, ExceptionType.COUPON_CREATION_FAILED,
					"Oh blimey! Something must have went wrong while adding your coupons. Please try again");
		} finally {
			try {
				pool.returnConnection(conn);
			} catch (Exception e) {
				throw new CouponSystemException(e, ExceptionType.GENERAL_ERROR,
						"Merlin's pants!! Voldemort has cursed our program and there is an unexplained error. If you're a muggle, you should probably run.");
			}
		}
	}// addCouponToCompany

	@Override
	public void removeCouponFromCompany(Company company, Coupon coupon) throws CouponSystemException {
		Connection conn = null;

		try {
			conn = pool.getConnection();
			PreparedStatement stmt = conn
					.prepareStatement("delete from Companies_Coupons where COMPANY_ID = ? and COUPON_ID = ?");
			stmt.setLong(1, company.getCompanyId());
			stmt.setLong(2, coupon.getCouponId());

			stmt.execute();
		} catch (InterruptedException | SQLException e) {
			throw new CouponSystemException(e, ExceptionType.COUPON_CREATION_FAILED,
					"Oh no! An error has occured during our attempt to remove your coupon. Our bad! Please try again");
		} finally {
			try {
				pool.returnConnection(conn);
			} catch (Exception e) {
				throw new CouponSystemException(e, ExceptionType.GENERAL_ERROR,
						"Merlin's pants!! Voldemort has cursed our program and there is an unexplained error. If you're a muggle, you should probably run.");
			}
		}
	}// removeCouponFromCompany

	@Override
	public void subtractFromStock(Coupon coupon) throws CouponSystemException {
		Connection conn = null;
		Coupon couponFromDb = null;

		try {
			conn = pool.getConnection();
			couponFromDb = getCoupon(coupon.getCouponId());

			PreparedStatement stmt = conn.prepareStatement("update Coupons set AMOUNT = ? where COUPON_ID = ?");
			stmt.setInt(1, couponFromDb.getAmount() - 1);
			stmt.setLong(2, coupon.getCouponId());

			stmt.execute();

		} catch (InterruptedException | SQLException e) {
			throw new CouponSystemException(e, ExceptionType.COUPON_OUT_OF_STOCK,
					"Sorry, too late! The coupon \"" + coupon.getTitle()
							+ "\" is out of stock. But no worries! There are lots of other awesome coupons out there");
		} finally {
			try {
				pool.returnConnection(conn);
			} catch (Exception e) {
				throw new CouponSystemException(e, ExceptionType.GENERAL_ERROR,
						"Merlin's pants!! Voldemort has cursed our program and there is an unexplained error. If you're a muggle, you should probably run.");
			}
		}
	}// subtractFromStock

	@Override
	public void addToStock(Coupon coupon) throws CouponSystemException {
		Coupon newCoupon = getCoupon(coupon.getCouponId());
		newCoupon.setAmount(newCoupon.getAmount() + 1);
		updateCoupon(newCoupon);

	}// addToStock

	@Override
	public Coupon getCoupon(long couponId) throws CouponSystemException {

		Connection conn = null;
		Coupon coupon = null;

		try {
			conn = pool.getConnection();
			PreparedStatement stmt = conn.prepareStatement("select * from Coupons where COUPON_ID = ?");
			stmt.setLong(1, couponId);
			ResultSet couponRow = stmt.executeQuery();
			couponRow.next();

			coupon = new Coupon(couponRow.getLong("COUPON_ID"), couponRow.getString("TITLE"),
					couponRow.getDate("START_DATE"), couponRow.getDate("END_DATE"), couponRow.getInt("AMOUNT"),
					CouponType.valueOf(couponRow.getString("TYPE")), couponRow.getString("MESSAGE"),
					couponRow.getFloat("PRICE"), couponRow.getString("IMAGE"));

		} catch (InterruptedException | SQLException e) {
			throw new CouponSystemException(e, ExceptionType.COUPON_NOT_AVAILABLE,
					"Oh no! The coupon is not available or could not be found. Please make sure the details are correct and try again");
		} finally {
			try {
				pool.returnConnection(conn);
			} catch (Exception e) {
				throw new CouponSystemException(e, ExceptionType.GENERAL_ERROR,
						"Merlin's pants!! Voldemort has cursed our program and there is an unexplained error. If you're a muggle, you should probably run.");
			}
		}
		return coupon;
	}// getCoupon

	@Override
	public long getCompanyId(Coupon coupon) throws CouponSystemException {
		Connection conn = null;
		ResultSet resultSet = null;
		long companyId = 0;

		try {
			conn = pool.getConnection();
			PreparedStatement stmt = conn.prepareStatement("select * from Companies_Coupons where COUPON_ID = ?");
			stmt.setLong(1, coupon.getCouponId());
			resultSet = stmt.executeQuery();
			resultSet.next();

			companyId = resultSet.getLong("COMPANY_ID");

		} catch (InterruptedException | SQLException e) {
			throw new CouponSystemException(e, ExceptionType.COMPANY_NOT_FOUND,
					"Oops! Could not find the company that created this coupon. Please try again");
		} finally {
			try {
				pool.returnConnection(conn);
			} catch (Exception e) {
				throw new CouponSystemException(e, ExceptionType.GENERAL_ERROR,
						"Merlin's pants!! Voldemort has cursed our program and there is an unexplained error. If you're a muggle, you should probably run.");

			}
		}
		return companyId;
	}// getCompanyId

	@Override
	public Collection<Long> getIdOfCouponsCustomers(Coupon coupon) throws CouponSystemException {
		Collection<Long> customers = new ArrayList<>();
		Connection conn = null;
		ResultSet resultSet = null;

		try {
			conn = pool.getConnection();
			PreparedStatement stmt = conn
					.prepareStatement("select CUSTOMER_ID from Customers_Coupons where COUPON_ID = ?");
			stmt.setLong(1, coupon.getCouponId());
			resultSet = stmt.executeQuery();

			while (resultSet.next()) {
				customers.add(resultSet.getLong("CUSTOMER_ID"));
			}

		} catch (SQLException | InterruptedException e) {
			throw new CouponSystemException(e, ExceptionType.COUPON_NOT_AVAILABLE,
					"Oops! Something went wrong while checking for customers. Please try again");
		} finally {
			try {
				pool.returnConnection(conn);
			} catch (Exception e) {
				throw new CouponSystemException(e, ExceptionType.GENERAL_ERROR,
						"Merlin's pants!! Voldemort has cursed our program and there is an unexplained error. If you're a muggle, you should probably run.");
			}
		}

		return customers;
	}

	@Override
	public Collection<Coupon> getAllCoupons() throws CouponSystemException {

		Collection<Coupon> coupons = new ArrayList<>();
		Connection conn = null;
		ResultSet resultSet = null;

		try {
			conn = pool.getConnection();
			PreparedStatement stmt = conn.prepareStatement("select * from Coupons");
			resultSet = stmt.executeQuery();

			while (resultSet.next()) {
				coupons.add(new Coupon(resultSet.getLong("COUPON_ID"), resultSet.getString("TITLE"),
						resultSet.getDate("START_DATE"), resultSet.getDate("END_DATE"), resultSet.getInt("AMOUNT"),
						CouponType.valueOf(resultSet.getString("TYPE")), resultSet.getString("MESSAGE"),
						resultSet.getFloat("PRICE"), resultSet.getString("IMAGE")));
			}

		} catch (SQLException | InterruptedException e) {
			throw new CouponSystemException(e, ExceptionType.COUPON_NOT_AVAILABLE,
					"Oops! Something went wrong while loading your coupons. Please try again");
		} finally {
			try {
				pool.returnConnection(conn);
			} catch (Exception e) {
				throw new CouponSystemException(e, ExceptionType.GENERAL_ERROR,
						"Merlin's pants!! Voldemort has cursed our program and there is an unexplained error. If you're a muggle, you should probably run.");
			}
		}
		return coupons;
	}// getAllCoupons

	@Override
	public Collection<Coupon> getCouponByType(CouponType couponType) throws CouponSystemException {
		Collection<Coupon> coupons = new ArrayList<>();
		Connection conn = null;
		ResultSet resultset = null;

		try {
			conn = pool.getConnection();
			PreparedStatement stmt = conn.prepareStatement("select * from Coupons where TYPE = ?");
			stmt.setString(1, couponType.name());
			resultset = stmt.executeQuery();

			while (resultset.next()) {
				coupons.add(new Coupon(resultset.getLong("COUPON_ID"), resultset.getString("TITLE"),
						resultset.getDate("START_DATE"), resultset.getDate("END_DATE"), resultset.getInt("AMOUNT"),
						CouponType.valueOf(resultset.getString("TYPE")), resultset.getString("IMAGE"),
						resultset.getDouble("PRICE"), resultset.getString("IMAGE")));
			}
		} catch (InterruptedException | SQLException e) {
			throw new CouponSystemException(e, ExceptionType.COUPON_NOT_AVAILABLE,
					"Oopsie! We're unable to load the coupons you've requested. Please try again later");
		} finally {
			try {
				pool.returnConnection(conn);
			} catch (Exception e) {
				throw new CouponSystemException(e, ExceptionType.GENERAL_ERROR,
						"Merlin's pants!! Voldemort has cursed our program and there is an unexplained error. If you're a muggle, you should probably run.");
			}
		}

		return coupons;
	}// getCouponByType

}// CouponDBDAO
