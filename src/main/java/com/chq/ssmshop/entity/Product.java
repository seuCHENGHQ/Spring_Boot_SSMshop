package com.chq.ssmshop.entity;

import java.util.Date;
import java.util.List;

public class Product {
	private Integer productId;
	private String productName;
	private String productDesc;
	private String imgAddr;
	private Integer normalPrice;
	private Integer promotionPrice;
	private Integer priority;
	private Date createTime;
	private Date lastEditTime;
	private Integer enableStatus;
	private ProductCategory productCategory;
	private Shop shop;
	private List<ProductImg> productImgList;

	public Integer getProductId() {
		return productId;
	}

	public String getProductName() {
		return productName;
	}

	public String getProductDesc() {
		return productDesc;
	}

	public String getImgAddr() {
		return imgAddr;
	}

	public Integer getNormalPrice() {
		return normalPrice;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public Date getLastEditTime() {
		return lastEditTime;
	}

	public Integer getEnableStatus() {
		return enableStatus;
	}

	public ProductCategory getProductCategory() {
		return productCategory;
	}

	public Shop getShop() {
		return shop;
	}

	public List<ProductImg> getProductImgList() {
		return productImgList;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public void setProductDesc(String productDesc) {
		this.productDesc = productDesc;
	}

	public void setImgAddr(String imgAddr) {
		this.imgAddr = imgAddr;
	}

	public void setNormalPrice(Integer normalPrice) {
		this.normalPrice = normalPrice;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public void setLastEditTime(Date lastEditTime) {
		this.lastEditTime = lastEditTime;
	}

	public void setEnableStatus(Integer enableStatus) {
		this.enableStatus = enableStatus;
	}

	public void setProductCategory(ProductCategory productCategory) {
		this.productCategory = productCategory;
	}

	public void setShop(Shop shop) {
		this.shop = shop;
	}

	public void setProductImgList(List<ProductImg> productImgList) {
		this.productImgList = productImgList;
	}

	public Integer getPromotionPrice() {
		return promotionPrice;
	}

	public void setPromotionPrice(Integer promotionPrice) {
		this.promotionPrice = promotionPrice;
	}

	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

}
