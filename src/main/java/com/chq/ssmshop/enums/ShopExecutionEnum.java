package com.chq.ssmshop.enums;

public enum ShopExecutionEnum {
	SUCCESS(0,"operation success"),UNSUCCESS(1,"operation failed"),NULL_SHOP_ID(2,"shopId is null"),
	NULL_SHOP(3,"shop is null"),NULL_OWNER(4,"shop owner is null"),NULL_AREA(5,"shop area is null"),
	NULL_SHOP_CATEGORY(6,"shop category is null"),NULL_SHOP_NAME(7,"shop name is null"),
	NULL_SHOP_DESC(8,"shop desc is null");
	
	private int state;

	private String stateInfo;

	private ShopExecutionEnum(int state, String stateInfo) {
		this.state=state;
		this.stateInfo=stateInfo;
	}

	public int getState() {
		return state;
	}

	public String getStateInfo() {
		return stateInfo;
	}

}
