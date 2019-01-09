package com.chq.ssmshop.dto;

import com.chq.ssmshop.entity.PersonInfo;
import com.chq.ssmshop.enums.PersonInfoExecutionEnum;

public class PersonInfoExecution {
	private PersonInfo user;
	private int count;
	private int state;
	private String stateInfo;

	public PersonInfo getUser() {
		return user;
	}

	public int getCount() {
		return count;
	}

	public void setUser(PersonInfo user) {
		this.user = user;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getState() {
		return state;
	}

	public String getStateInfo() {
		return stateInfo;
	}

	public void setState(int state) {
		this.state = state;
	}

	public void setStateInfo(String stateInfo) {
		this.stateInfo = stateInfo;
	}

	/**
	 * 获取user失败时使用的构造器
	 * 
	 * @param personInfoExecutionEnum
	 */
	public PersonInfoExecution(PersonInfoExecutionEnum personInfoExecutionEnum) {
		this.state = personInfoExecutionEnum.getState();
		this.stateInfo = personInfoExecutionEnum.getStateInfo();
	}

	/**
	 * 当获取user成功时采用的构造器
	 * 
	 * @param personInfoExecutionEnum
	 * @param user
	 */
	public PersonInfoExecution(PersonInfoExecutionEnum personInfoExecutionEnum, PersonInfo user) {
		this.user = user;
		this.count = 1;
		this.state = personInfoExecutionEnum.getState();
		this.stateInfo = personInfoExecutionEnum.getStateInfo();
	}
}
