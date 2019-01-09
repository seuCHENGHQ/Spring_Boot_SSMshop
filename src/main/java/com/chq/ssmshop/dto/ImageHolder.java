package com.chq.ssmshop.dto;

import java.io.InputStream;

public class ImageHolder {
	private String fileName;
	private InputStream image;

	public ImageHolder() {

	}

	public ImageHolder(InputStream image, String fileName) {
		this.image = image;
		this.fileName = fileName;
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

}
