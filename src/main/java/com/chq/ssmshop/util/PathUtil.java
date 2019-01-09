package com.chq.ssmshop.util;

import java.io.File;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PathUtil {

	private static String winImgPath;

	private static String linuxImgPath;

	@Value("${win.img.basepath}")
	public void setWinImgPath(String winImgPath) {
		PathUtil.winImgPath = winImgPath;
	}

	@Value("${linux.img.basepath}")
	public void setLinuxImgPath(String linuxImgPath) {
		PathUtil.linuxImgPath = linuxImgPath;
	}

	/**
	 * 
	 * @return 这里返回图片目录的文件夹路径
	 */
	public static String getImageBasePath() {
		String os = System.getProperty("os.name");
		String seperator = File.pathSeparator;
		String basePath = "";
		if (os.toLowerCase().startsWith("win")) {
			basePath = winImgPath;
		} else {
			basePath = linuxImgPath;
		}
		basePath.replaceAll("/", seperator);
		return basePath;
	}

	/**
	 * 
	 * @return 这里返回存放店铺图片的文件夹路径
	 */
	public static String getShopImagePath() {
		return getImageBasePath() + "shop/";
	}

	public static String getProductThumbnailPath(Integer shopId, Integer productId) {
		return getShopImagePath() + shopId + "/" + productId + "/";
	}

	public static void main(String[] args) {
		System.out.println(System.getProperty("os.name"));
	}
}
