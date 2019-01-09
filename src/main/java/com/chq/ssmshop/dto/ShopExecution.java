package com.chq.ssmshop.dto;

import java.util.List;

import com.chq.ssmshop.entity.Shop;
import com.chq.ssmshop.enums.ShopExecutionEnum;

public class ShopExecution {
	// 本次执行的状态码
	private int state;

	// 本次执行的状态信息
	private String stateInfo;;

	// 本次执行影响到的shop数量
	private int count;

	private Shop shop;

	private List<Shop> shopList;

	/**
	 * 操作失败的构造器
	 * 
	 * @param stateEnum
	 */
	public ShopExecution(ShopExecutionEnum stateEnum) {
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
	}

	/**
	 * 操作成功的构造器
	 * 
	 * @param stateEnum
	 * @param shop
	 */
	public ShopExecution(ShopExecutionEnum stateEnum, Shop shop) {
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
		this.shop = shop;
		this.count = 1;
	}

	/**
	 * 操作成功的构造器
	 * 
	 * @param stateEnum
	 * @param shopList
	 */
	public ShopExecution(ShopExecutionEnum stateEnum, List<Shop> shopList) {
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
		this.shopList = shopList;
		this.count = shopList.size();
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getStateInfo() {
		return stateInfo;
	}

	public void setStateInfo(String stateInfo) {
		this.stateInfo = stateInfo;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public Shop getShop() {
		return shop;
	}

	public void setShop(Shop shop) {
		this.shop = shop;
	}

	public List<Shop> getShopList() {
		return shopList;
	}

	public void setShopList(List<Shop> shopList) {
		this.shopList = shopList;
	}

}
