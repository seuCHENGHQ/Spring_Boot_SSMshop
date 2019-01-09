package com.chq.ssmshop.entity;

import java.util.Date;

public class ProductCategory {
	private Integer productCategoryId;
	private String productCategoryName;
	private Integer priority;
	private Date createTime;
	private Date lastEditTime;
	private Shop shop;

	public Integer getProductCategoryId() {
		return productCategoryId;
	}

	public String getProductCategoryName() {
		return productCategoryName;
	}

	public Integer getPriority() {
		return priority;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public Date getLastEditTime() {
		return lastEditTime;
	}

	public Shop getShop() {
		return shop;
	}

	public void setProductCategoryId(Integer productCategoryId) {
		this.productCategoryId = productCategoryId;
	}

	public void setProductCategoryName(String string) {
		this.productCategoryName = string;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public void setLastEditTime(Date lastEditTime) {
		this.lastEditTime = lastEditTime;
	}

	public void setShop(Shop shop) {
		this.shop = shop;
	}
}
