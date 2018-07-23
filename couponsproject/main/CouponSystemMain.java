package com.abigail.couponsproject.main;

import java.util.Calendar;
import java.util.Collection;
import java.util.GregorianCalendar;

import com.abigail.couponsproject.DB.DAO.CompanyDao;
import com.abigail.couponsproject.DB.DAO.CouponDao;
import com.abigail.couponsproject.DB.DAO.CustomerDao;
import com.abigail.couponsproject.DB.DAO.IdHandlerDao;
import com.abigail.couponsproject.DB.DBDAO.CompanyDbDao;
import com.abigail.couponsproject.DB.DBDAO.CouponDbDao;
import com.abigail.couponsproject.DB.DBDAO.CustomerDbDao;
import com.abigail.couponsproject.DB.DBDAO.IdHandlerDbDao;
import com.abigail.couponsproject.beans.Company;
import com.abigail.couponsproject.beans.Coupon;
import com.abigail.couponsproject.beans.CouponType;
import com.abigail.couponsproject.beans.Customer;
import com.abigail.couponsproject.exceptions.CouponSystemException;
import com.abigail.couponsproject.facade.AdminFacade;
import com.abigail.couponsproject.facade.CompanyFacade;
import com.abigail.couponsproject.facade.CustomerFacade;
import com.abigail.couponsproject.utils.DailyCouponExpirationTask;

public class CouponSystemMain {
	@SuppressWarnings("unused")
	public static void main(String[] args) {

		CompanyDao companyDao = new CompanyDbDao();
		CustomerDao customerDao = new CustomerDbDao();
		CouponDao couponDao = new CouponDbDao();
		IdHandlerDao idHandler = new IdHandlerDbDao();

		AdminFacade admin = new AdminFacade(companyDao, customerDao, couponDao);

		Company rubin = new Company(1, "Rubin LTD", "rubinushnush", "rubin@gmail.com");
		CompanyFacade rubinFacade = new CompanyFacade(rubin, couponDao, companyDao, customerDao);

		Company igor = new Company(2, "Igor", "I love coffee", "igorthehuman@gmail.com");
		CompanyFacade igorFacade = new CompanyFacade(igor, couponDao, companyDao, customerDao);

		Company meir = new Company(3, "Meir Nizri Software", "26", "meirnizri@hello.co.za");
		CompanyFacade meirFacade = new CompanyFacade(meir, couponDao, companyDao, customerDao);

		Company satan = new Company(4, "Satan", "play that funky music white boy", "satan.666@evil.com");
		CompanyFacade satanFacade = new CompanyFacade(satan, couponDao, companyDao, customerDao);

		Company me = new Company(5, "Avigail LTD", "kittens", "me@gmail.com");
		CompanyFacade myFacade = new CompanyFacade(me, couponDao, companyDao, customerDao);

		Coupon rubinsCoupon = new Coupon(7, "20% discount on Vacation to China",
				new GregorianCalendar(2018, Calendar.JANUARY, 7).getTime(),
				new GregorianCalendar(2018, Calendar.MARCH, 10).getTime(), 100, CouponType.TRAVELING,
				"We will give you the best price plus you get to eat chocolate", 2000, "some chinese ppl");

		Coupon rubinsOtherCoupon = new Coupon(1, "I am Groot", new GregorianCalendar(2018, Calendar.JUNE, 6).getTime(),
				new GregorianCalendar(2018, Calendar.AUGUST, 19).getTime(), 44, CouponType.OTHER, "I AM GROOT", 432,
				"Groot");

		Coupon expiringToday = new Coupon(2, "HAPPY BIRTHDAY TO YOU",
				new GregorianCalendar(1995, Calendar.JUNE, 28).getTime(),
				new GregorianCalendar(2018, Calendar.JUNE, 28).getTime(), 120, CouponType.MUSIC,
				"You belong in the zoo", 10000, "blowing out candles");

		Coupon myCoupon = new Coupon(3, "Voldemort's back", new GregorianCalendar(2015, Calendar.MARCH, 3).getTime(),
				new GregorianCalendar(2019, Calendar.MARCH, 2).getTime(), 10, CouponType.HEALTH,
				"The dark lord is back to be a bad guy cause he's a bad guy oh no what will we do now", 666,
				"Image of scary dude without a nose");

		Coupon satansCoupon = new Coupon(4, "Wehre is my mind",
				new GregorianCalendar(2017, Calendar.MARCH, 4).getTime(),
				new GregorianCalendar(2019, Calendar.MARCH, 22).getTime(), 10, CouponType.ELECTRICITY,
				"With your feet in the air and your head on the ground - Try this trick and spin it, yeah", 4343434,
				"Picture of pixies in a fight club");

		Coupon myOtherCoupon = new Coupon(5, "Win our marmite eating contest and fly to London!",
				new GregorianCalendar(2017, Calendar.MARCH, 4).getTime(),
				new GregorianCalendar(2019, Calendar.MARCH, 22).getTime(), 10, CouponType.FOOD,
				"Come to our wonderful restaurant, where only mermite is served as food and beverages, and eat all you can. We promise to provide medica care, but only the greediest marmite gobblers will win the prize",
				654, "Marmite and Sons");

		Coupon meirsCoupon = new Coupon(6, "Calculate your taxes wrong and go to jail",
				new GregorianCalendar(2017, Calendar.MARCH, 4).getTime(),
				new GregorianCalendar(2019, Calendar.MARCH, 22).getTime(), 10, CouponType.OTHER,
				"We have the best software to calculate everything wrong and confuse everyone! You're welcome to go to prison with us, we have free cigs",
				1, "me in jail");

		Customer myFace = new Customer(1, "My face", "your face");
		CustomerFacade faceFacade = new CustomerFacade(myFace, customerDao, couponDao);

		Customer steppie = new Customer(2, "David", "I am steppie");
		CustomerFacade steppieFacade = new CustomerFacade(steppie, customerDao, couponDao);

		try {

			// igorFacade.createCoupon(expiringToday);
			// rubinFacade.createCoupon(rubinsCoupon);
			//
			// System.out.println("ya bish");

			Collection<Coupon> coupons = couponDao.getAllCoupons();
			for (Coupon coupon : coupons) {
				System.out.println("ID: " + coupon.getCouponId() + ", End date: " + coupon.getEndDate());
			}
			System.out.println("--------------------------------------------------");

			DailyCouponExpirationTask task = new DailyCouponExpirationTask(couponDao, companyDao, customerDao);
			task.start();

			coupons = couponDao.getAllCoupons();
			for (Coupon coupon : coupons) {
				System.out.println("ID: " + coupon.getCouponId() + ", End date: " + coupon.getEndDate());
			}
			System.out.println("--------------------------------------------------");

			// System.out.println(customerDao.getCustomerByName("David"));

		} catch (CouponSystemException e) {
			System.out.println(e.getMessage());
		}

		// TODO: finish couponn system singleton
		// TODO: check login methods

	}// main

}// CouponSystemMain
