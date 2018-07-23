package com.abigail.couponsproject.exceptions;

public class CouponSystemException extends Exception {

	private static final long serialVersionUID = 1L;
	ExceptionType customException;
	String message;
	Exception exception;

	public CouponSystemException(ExceptionType customException, String message) {
		super(message);
		this.customException = customException;
		this.message = message;
	}// c-tor

	public CouponSystemException(Exception exception, ExceptionType customException, String message) {
		this(customException, message);
		this.exception = exception;
	}// c-tor

}// ApplicationException
