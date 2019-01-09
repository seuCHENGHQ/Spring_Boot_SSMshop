package com.chq.ssmshop.exception;

public class ProductCategoryOperationException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ProductCategoryOperationException(String errMsg) {
		super(errMsg);
	}
}
