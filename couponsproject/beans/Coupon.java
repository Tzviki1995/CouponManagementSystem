package com.abigail.couponsproject.beans;

import java.util.Date;

public class Coupon {

	private long couponId;
	private String title;
	private Date startDate;
	private Date endDate;
	private int amount;
	private CouponType type;
	private String message;
	private double price;
	private String image;

	public Coupon(long couponId, String title, Date startDate, Date endDate, int amount, CouponType type, String msg,
			double price, String img) {
		this.couponId = couponId;
		this.title = title;
		this.startDate = startDate;
		this.endDate = endDate;
		this.amount = amount;
		this.type = type;
		this.message = msg;
		this.price = price;
		this.image = img;
	}// c-tor

	public long getCouponId() {
		return couponId;
	}// getCouponId

	public String getTitle() {
		return title;
	}// getTitle

	public void setTitle(String title) {
		this.title = title;
	}// setTitle

	public Date getStartDate() {
		return startDate;
	}// getStatrtDate

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}// setStartDate

	public Date getEndDate() {
		return endDate;
	}// getEndDate

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}// setEndDate

	public int getAmount() {
		return amount;
	}// getAmount

	public void setAmount(int amount) {
		this.amount = amount;
	}// setAmount

	public CouponType getType() {
		return type;
	}// getType

	public void setType(CouponType type) {
		this.type = type;
	}// setType

	public String getMessage() {
		return message;
	}// getMessage

	public void setMessage(String message) {
		this.message = message;
	}// setMessage

	public double getPrice() {
		return price;
	}// getPrice

	public void setPrice(double price) {
		this.price = price;
	}// setPrice

	public String getImage() {
		return image;
	}// getImage

	public void setImage(String image) {
		this.image = image;
	}// setImage

	@Override
	public String toString() {
		return couponId + "	" + type + "	" + title + ": " + message + "\n	price: " + price + "	valid from: "
				+ startDate + "	to: " + endDate;
	}// toString

}// coupon
