package com.chq.ssmshop.dto;

import java.util.List;

import com.chq.ssmshop.entity.ProductCategory;
import com.chq.ssmshop.enums.ProductCategoryExecutionEnum;

public class ProductCategoryExecution {
	private int state;
	private String stateInfo;
	private int count;
	private ProductCategory productCategory;
	private List<ProductCategory> productCategoryList;

	/**
	 * service操作失败时使用的构造器
	 * 
	 * @param pcee
	 */
	public ProductCategoryExecution(ProductCategoryExecutionEnum pcee) {
		this.state = pcee.getState();
		this.stateInfo = pcee.getStateInfo();
	}

	/**
	 * service操作成功时使用的构造器
	 * 
	 * @param pcee
	 * @param productCategoryList
	 */
	public ProductCategoryExecution(ProductCategoryExecutionEnum pcee, List<ProductCategory> productCategoryList) {
		this.state = pcee.getState();
		this.stateInfo = pcee.getStateInfo();
		this.productCategoryList = productCategoryList;
		this.count = productCategoryList.size();
	}

	/**
	 * service操作成功时使用的构造器
	 * 
	 * @param pcee
	 * @param pc
	 */
	public ProductCategoryExecution(ProductCategoryExecutionEnum pcee, ProductCategory pc) {
		this.state = pcee.getState();
		this.stateInfo = pcee.getStateInfo();
		this.productCategory = pc;
		this.count = 1;
	}

	public int getState() {
		return state;
	}

	public String getStateInfo() {
		return stateInfo;
	}

	public ProductCategory getProductCategory() {
		return productCategory;
	}

	public List<ProductCategory> getProductCategoryList() {
		return productCategoryList;
	}

	public void setState(int state) {
		this.state = state;
	}

	public void setStateInfo(String stateInfo) {
		this.stateInfo = stateInfo;
	}

	public void setProductCategory(ProductCategory productCategory) {
		this.productCategory = productCategory;
	}

	public void setProductCategoryList(List<ProductCategory> productCategoryList) {
		this.productCategoryList = productCategoryList;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

}
