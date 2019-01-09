package com.chq.ssmshop.exception;

public class LocalAuthOperationException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8872409287705210540L;

	public LocalAuthOperationException(String errMsg) {
		super(errMsg);
	}
}
