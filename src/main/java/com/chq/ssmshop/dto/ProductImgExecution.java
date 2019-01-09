package com.chq.ssmshop.dto;

import java.util.List;

import com.chq.ssmshop.entity.ProductImg;
import com.chq.ssmshop.enums.ProductImgExecutionEnum;

public class ProductImgExecution {
	private int state;
	private String stateInfo;
	private int count;
	private ProductImg productImg;
	private List<ProductImg> productImgList;

	/**
	 * productService操作失败时使用的构造器
	 * 
	 * @param piee
	 */
	public ProductImgExecution(ProductImgExecutionEnum piee) {
		this.state = piee.getState();
		this.stateInfo = piee.getStateInfo();
	}

	/**
	 * productService操作成功时使用的构造器
	 * 
	 * @param piee
	 * @param productImg
	 */
	public ProductImgExecution(ProductImgExecutionEnum piee, ProductImg productImg) {
		this.state = piee.getState();
		this.stateInfo = piee.getStateInfo();
		this.count = 1;
		this.productImg = productImg;
	}

	/**
	 * productService操作成功时使用的构造器
	 * 
	 * @param piee
	 * @param productImgList
	 */
	public ProductImgExecution(ProductImgExecutionEnum piee, List<ProductImg> productImgList) {
		this.state = piee.getState();
		this.stateInfo = piee.getStateInfo();
		this.count = productImgList.size();
		this.productImgList = productImgList;
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

	public ProductImg getProductImg() {
		return productImg;
	}

	public List<ProductImg> getProductImgList() {
		return productImgList;
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

	public void setProductImg(ProductImg productImg) {
		this.productImg = productImg;
	}

	public void setProductImgList(List<ProductImg> productImgList) {
		this.productImgList = productImgList;
	}

}
