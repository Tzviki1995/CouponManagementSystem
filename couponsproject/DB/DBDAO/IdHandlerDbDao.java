package com.abigail.couponsproject.DB.DBDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.abigail.couponsproject.DB.ConnectionPool;
import com.abigail.couponsproject.DB.DAO.IdHandlerDao;
import com.abigail.couponsproject.exceptions.CouponSystemException;
import com.abigail.couponsproject.exceptions.ExceptionType;

public class IdHandlerDbDao implements IdHandlerDao {

	private ConnectionPool pool = null;

	public IdHandlerDbDao() {
		pool = ConnectionPool.getInstance();
	}// c-tor

	@Override
	public long getLastCompanyId() throws CouponSystemException {

		Connection conn = null;
		Long lastCompanyId = 0L;

		try {
			conn = pool.getConnection();
			PreparedStatement stmt = conn.prepareStatement("select * from Id_Counter where ID_HOLDER = 'Company'");
			ResultSet result = stmt.executeQuery();
			result.next();
			lastCompanyId = result.getLong("LAST_ID");
		} catch (InterruptedException | SQLException e) {
			throw new CouponSystemException(e, ExceptionType.GENERAL_ERROR,
					"Merlin's pants!! Voldemort has cursed our program and there is an unexplained error. If you're a muggle, you should probably run.");
		} finally {
			try {
				pool.returnConnection(conn);
			} catch (Exception e) {
				throw new CouponSystemException(e, ExceptionType.GENERAL_ERROR,
						"Merlin's pants!! Voldemort has cursed our program and there is an unexplained error. If you're a muggle, you should probably run.");
			}
		}
		return lastCompanyId;
	}// getLastCompanyId

	@Override
	public long incrementLastCompanyId() throws CouponSystemException {
		// TODO Auto-generated method stub
		return 0;
	}// incrementLastCompanyId

	@Override
	public long getLastCouponId() throws CouponSystemException {

		Connection conn = null;
		Long lastCouponId = 0L;

		try {
			conn = pool.getConnection();
			PreparedStatement stmt = conn.prepareStatement("select * from Id_Counter where ID_HOLDER = 'Coupon'");
			ResultSet result = stmt.executeQuery();
			result.next();
			lastCouponId = result.getLong("LAST_ID");
		} catch (InterruptedException | SQLException e) {
			throw new CouponSystemException(e, ExceptionType.GENERAL_ERROR,
					"Merlin's pants!! Voldemort has cursed our program and there is an unexplained error. If you're a muggle, you should probably run.");
		} finally {
			try {
				pool.returnConnection(conn);
			} catch (Exception e) {
				throw new CouponSystemException(e, ExceptionType.GENERAL_ERROR,
						"Merlin's pants!! Voldemort has cursed our program and there is an unexplained error. If you're a muggle, you should probably run.");
			}
		}
		return lastCouponId;
	}// getLastCouponId

	@Override
	public long incrementLastCouponId() throws CouponSystemException {
		// TODO Auto-generated method stub
		return 0;
	}// incrementLastCouponId

	@Override
	public long getLastCustomerId() throws CouponSystemException {

		Connection conn = null;
		Long lastCustomerId = 0L;

		try {
			conn = pool.getConnection();
			PreparedStatement stmt = conn.prepareStatement("select * from Id_Counter where ID_HOLDER = 'Customer'");
			ResultSet result = stmt.executeQuery();
			result.next();
			lastCustomerId = result.getLong("LAST_ID");
		} catch (InterruptedException | SQLException e) {
			throw new CouponSystemException(e, ExceptionType.GENERAL_ERROR,
					"Merlin's pants!! Voldemort has cursed our program and there is an unexplained error. If you're a muggle, you should probably run.");
		} finally {
			try {
				pool.returnConnection(conn);
			} catch (Exception e) {
				throw new CouponSystemException(e, ExceptionType.GENERAL_ERROR,
						"Merlin's pants!! Voldemort has cursed our program and there is an unexplained error. If you're a muggle, you should probably run.");
			}
		}
		return lastCustomerId;
	}// getLastCustomerId

	@Override
	public long incrementLastCustomerId() throws CouponSystemException {
		// TODO Auto-generated method stub
		return 0;
	}// incrementLastCustomerId

}
