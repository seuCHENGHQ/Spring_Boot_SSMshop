package com.chq.ssmshop.dto;

import java.io.InputStream;

import com.chq.ssmshop.entity.Product;

public class ProductImageHolder {
	private String fileName;
	private InputStream image;
	private Product product;

	public ProductImageHolder() {

	}

	public ProductImageHolder(InputStream image, String fileName, Product product) {
		this.image = image;
		this.fileName = fileName;
		this.product=product;
	}

	public String getFileName() {
		return fileName;
	}

	public InputStream getImage() {
		return image;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public void setImage(InputStream image) {
		this.image = image;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Product getProduct() {
		return product;
	}
}
