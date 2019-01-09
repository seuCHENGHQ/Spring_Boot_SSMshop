package com.chq.ssmshop.enums;

public enum PersonInfoExecutionEnum {

	SUCCESS(0, "operation success"), NULL_USER(1, "user not exist");

	private int state;
	private String stateInfo;

	private PersonInfoExecutionEnum(int state, String stateInfo) {
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
