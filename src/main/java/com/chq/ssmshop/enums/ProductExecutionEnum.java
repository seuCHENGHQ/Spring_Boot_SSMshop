package com.chq.ssmshop.enums;

public enum ProductExecutionEnum {
	SUCCESS(0, "operation success"),UNSUCCESS(1,"operation unsuccess"),NULL_PRODUCT_NAME(2,"null product name"),
	NULL_PRODUCT_CATEGORY(3,"null product category"),NULL_SHOP(4,"null shop"),NULL_PRODUCT(5,"null product"),
	NULL_PRODUCT_THUMBNAIL(6,"null product thumbnail"),DELETE_ERROR(7,"delete error");

	private int state;
	private String stateInfo;

	private ProductExecutionEnum(int state, String stateInfo) {
		this.state = state;
		this.stateInfo = stateInfo;
	}

	public int getState() {
		return state;
	}

	public String getStateInfo() {
		return stateInfo;
	}

}
