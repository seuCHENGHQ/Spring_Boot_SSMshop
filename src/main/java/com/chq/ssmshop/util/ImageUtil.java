package com.chq.ssmshop.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.Date;

import javax.imageio.ImageIO;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;

public class ImageUtil {

	public static String getImageName() {
		StringBuilder sb = new StringBuilder();
		sb.append(new Date().getTime());
		sb.append(new DecimalFormat("00000").format(Math.random() * 10000));
		return sb.toString();
	}

	public static String getExtension(String fileName) {
		int index = fileName.lastIndexOf('.');
		String extension = fileName.substring(index, fileName.length());
		return extension;
	}

	/**
	 * 
	 * @param targetAddr 这里应传入绝对路径
	 */
	public static void makeDir(String targetAddr) {
		File dir = new File(targetAddr);
		if (!dir.exists()) {
			dir.mkdirs();
		}
	}

	/**
	 * 
	 * @param absolutePath 图片绝对路径
	 * @return
	 */
	public static boolean deleteImage(String absolutePath) {
		File image = new File(absolutePath);
		if (image.exists()) {
			return image.delete();
		}
		return false;
	}

	/**
	 * 
	 * @param is         上传文件的输入流
	 * @param fileName   上传文件的文件名
	 * @param targetAddr 存放缩略图的目标文件夹
	 * @return 返回店铺缩略图文件的绝对路径
	 * @throws IOException 产生缩略图出现异常，抛出
	 */
	public static String generateThumbnail(InputStream is, String fileName, String targetAddr) throws IOException {
		String basePath = PathUtil.getShopImagePath();
		String imageName = getImageName();
		String extension = getExtension(fileName);

		makeDir(basePath + targetAddr);

		File watermark = new File(PathUtil.getImageBasePath() + "watermark.png");
		File thumbnail = new File(basePath + targetAddr + imageName + extension);
		Thumbnails.of(is).size(400, 400).watermark(Positions.BOTTOM_RIGHT, ImageIO.read(watermark), 0.5f)
				.toFile(thumbnail);
		return thumbnail.toString();
	}

	/**
	 * 
	 * @param is
	 * @param fileName
	 * @param targetAddr
	 * @return
	 * @throws IOException
	 */
	public static String generateProductImg(InputStream is, String fileName, String targetAddr) throws IOException {
		String basePath = PathUtil.getShopImagePath();
		String imageName = getImageName();
		String extension = getExtension(fileName);

		makeDir(basePath + targetAddr);

		File watermark = new File(PathUtil.getImageBasePath() + "watermark.png");
		File thumbnail = new File(basePath + targetAddr + imageName + extension);
		Thumbnails.of(is).size(800, 800).watermark(Positions.BOTTOM_RIGHT, ImageIO.read(watermark), 0.5f)
				.toFile(thumbnail);
		return thumbnail.toString();
	}

	public static void main(String[] args) {
////		String path = Thread.currentThread().getContextClassLoader().getResource("").getPath();
//		String basePath = PathUtil.getImageBasePath();
////		String currTime = new Date().getTime() + "";
//		String extension = getExtension("xuejie.jpg");
//		File destFile = new File(basePath + "/test/" + "xuejieoutput" + extension);
//		try {
//			Thumbnails.of(new File(basePath + "test/xuejie.jpg")).size(400, 400)
//					.watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File(basePath + "test/watermark.png")), 0.25f)
//					.toFile(destFile);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

		String targetAddr = "/1/";
		File uploadImg = new File(PathUtil.getImageBasePath() + "test/xuejie.jpg");
		try {
			System.out.println(generateThumbnail(new FileInputStream(uploadImg), "xuejie.jpg", targetAddr));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
