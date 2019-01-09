package com.chq.ssmshop.dto;

import java.util.List;

import com.chq.ssmshop.entity.Product;
import com.chq.ssmshop.enums.ProductExecutionEnum;

public class ProductExecution {
	private int state;
	private String stateInfo;
	private int count;
	private List<Product> productList;
	private Product product;

	public ProductExecution() {

	}

	/**
	 * productService操作失败时使用的构造器
	 * 
	 * @param pee
	 */
	public ProductExecution(ProductExecutionEnum pee) {
		this.state = pee.getState();
		this.stateInfo = pee.getStateInfo();
	}

	/**
	 * productService操作成功时使用的构造器
	 * 
	 * @param pee
	 * @param product
	 */
	public ProductExecution(ProductExecutionEnum pee, Product product) {
		this.state = pee.getState();
		this.stateInfo = pee.getStateInfo();
		this.count = 1;
		this.product = product;
	}

	/**
	 * productService操作成功时使用的构造器
	 * 
	 * @param pee
	 * @param productList
	 */
	public ProductExecution(ProductExecutionEnum pee, List<Product> productList) {
		this.state = pee.getState();
		this.stateInfo = pee.getStateInfo();
		this.count = productList.size();
		this.productList = productList;
	}

	public int getState() {
		return state;
	}

	public String getStateInfo() {
		return stateInfo;
	}

	public int getCount() {
		return count;
	}

	public List<Product> getProductList() {
		return productList;
	}

	public Product getProduct() {
		return product;
	}

	public void setState(int state) {
		this.state = state;
	}

	public void setStateInfo(String stateInfo) {
		this.stateInfo = stateInfo;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public void setProductList(List<Product> productList) {
		this.productList = productList;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

}
