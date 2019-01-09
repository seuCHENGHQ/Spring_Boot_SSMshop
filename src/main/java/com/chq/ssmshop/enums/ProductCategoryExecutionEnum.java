package com.chq.ssmshop.enums;

public enum ProductCategoryExecutionEnum {
	SUCCESS(0, "operation success"), NULL_PRODUCT_CATEGORY_ID(1, "null product category id"),
	NULL_SHOP_ID(2, "null shop id"), NULL_PRODUCT_CATEGORY_NAME(3, "null product category name"),
	NULL_PRODUCT_CATEGORY_LIST(4, "null product category list");

	private int state;
	private String stateInfo;

	private ProductCategoryExecutionEnum(int state, String stateInfo) {
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
