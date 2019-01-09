package com.chq.ssmshop.exception;

public class ProductOperationException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public ProductOperationException(String errMsg) {
		super(errMsg);
	}

}
