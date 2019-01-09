package com.chq.ssmshop.enums;

public enum ProductImgExecutionEnum {
	SUCCESS(0, "operation success"), UNSUCCESS(1, "operation unsuccess"),
	NULL_PRODUCT_IMG_ADDR(2, "null product img addr"),NULL_PRODUCT(3,"null product");

	private int state;
	private String stateInfo;

	private ProductImgExecutionEnum(int state, String stateInfo) {
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
