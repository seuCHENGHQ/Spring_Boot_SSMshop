package com.chq.ssmshop.entity;

import java.util.Date;

public class ProductImg {
	private Integer productImgId;
	private String imgAddr;
	private String imgDesc;
	private Integer priority;
	private Date createTime;
	private Product product;

	public Integer getProductImgId() {
		return productImgId;
	}

	public String getImgAddr() {
		return imgAddr;
	}

	public String getImgDesc() {
		return imgDesc;
	}

	public Integer getPriority() {
		return priority;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public Product getProduct() {
		return product;
	}

	public void setProductImgId(Integer productImgId) {
		this.productImgId = productImgId;
	}

	public void setImgAddr(String imgAddr) {
		this.imgAddr = imgAddr;
	}

	public void setImgDesc(String imgDesc) {
		this.imgDesc = imgDesc;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public void setProduct(Product product) {
		this.product = product;
	}
}
