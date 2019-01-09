package com.chq.ssmshop.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.chq.ssmshop.dao.ProductImgDao;
import com.chq.ssmshop.dto.ProductImageHolder;
import com.chq.ssmshop.dto.ProductImgExecution;
import com.chq.ssmshop.entity.Product;
import com.chq.ssmshop.entity.ProductImg;
import com.chq.ssmshop.enums.ProductImgExecutionEnum;
import com.chq.ssmshop.exception.ProductImgException;
import com.chq.ssmshop.service.ProductImgService;
import com.chq.ssmshop.util.ImageUtil;

@Service
public class ProductImgServiceImpl implements ProductImgService {

	@Autowired
	private ProductImgDao productImgDao;

	@Override
	@Transactional(isolation = Isolation.REPEATABLE_READ)
	public ProductImgExecution insertProductImg(ProductImg productImg, ProductImageHolder imageHolder) {
		// TODO Auto-generated method stub
		if (productImg == null) {
			return new ProductImgExecution(ProductImgExecutionEnum.UNSUCCESS);
		} else if (productImg.getProduct() == null || productImg.getProduct().getProductId() == null) {
			return new ProductImgExecution(ProductImgExecutionEnum.NULL_PRODUCT);
		}

		if (imageHolder == null || imageHolder.getImage() == null || imageHolder.getFileName() == null) {
			return new ProductImgExecution(ProductImgExecutionEnum.NULL_PRODUCT_IMG_ADDR);
		}

		try {
			String imgAddr = addImg(productImg, imageHolder);
			productImg.setImgAddr(imgAddr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new ProductImgException("生成productImg的时候出现问题！");
		}

		if (productImg.getPriority() == null) {
			productImg.setPriority(0);
		}
		productImg.setCreateTime(new Date());
		int effectedRow = productImgDao.insertProductImg(productImg);
		if (effectedRow < 1) {
			throw new ProductImgException("插入productImg的时候出现问题！");
		}
		return new ProductImgExecution(ProductImgExecutionEnum.SUCCESS, productImg);
	}

	@Override
	public ProductImgExecution getProductImgListByProductId(long productId) {
		// TODO Auto-generated method stub
		List<ProductImg> productImgList = productImgDao.getProductImgListByProductId(productId);
		if (productImgList == null) {
			return new ProductImgExecution(ProductImgExecutionEnum.UNSUCCESS);
		} else {
			return new ProductImgExecution(ProductImgExecutionEnum.SUCCESS, productImgList);
		}
	}

	@Override
	public ProductImgExecution getProductImgByProductImgId(long productImgId) {
		// TODO Auto-generated method stub
		ProductImg productImg = productImgDao.getProductImgByProductImgId(productImgId);
		if (productImg == null) {
			return new ProductImgExecution(ProductImgExecutionEnum.UNSUCCESS);
		} else {
			return new ProductImgExecution(ProductImgExecutionEnum.SUCCESS, productImg);
		}
	}

	@Override
	@Transactional(isolation = Isolation.REPEATABLE_READ)
	public ProductImgExecution deleteProductImgByProductId(long productId) {
		// TODO Auto-generated method stub
		ProductImgExecution pie = this.getProductImgListByProductId(productId);
		for (ProductImg productImg : pie.getProductImgList()) {
			ProductImgExecution temp = this.deleteProductImgByProductImgId(productImg.getProductImgId());
			if (temp == null || pie.getState() != ProductImgExecutionEnum.SUCCESS.getState()) {
				throw new ProductImgException("删除productId=" + productId + "的图片时出现异常！");
			}
		}
		return pie;
	}

	@Override
	@Transactional(isolation = Isolation.REPEATABLE_READ)
	public ProductImgExecution deleteProductImgByProductImgId(long productImgId) {
		// TODO Auto-generated method stub
		ProductImgExecution pie = this.getProductImgByProductImgId(productImgId);
		if (pie.getState() != ProductImgExecutionEnum.SUCCESS.getState()) {
			throw new ProductImgException("删除一个不存在的productImg！");
		} else {
			int effectedRow = productImgDao.deleteProductImg(productImgId);
			if (effectedRow < 1) {
				throw new ProductImgException("从数据库删除productImgId=" + productImgId + "时出现异常！");
			}
			deleteImg(pie.getProductImg());
		}
		return pie;
	}

	private String addImg(ProductImg productImg, ProductImageHolder imageHolder) throws IOException {
		StringBuilder sb = new StringBuilder();
		sb.append(imageHolder.getProduct().getShop().getShopId());
		sb.append("/");
		sb.append(productImg.getProduct().getProductId());
		sb.append("/");

		String targetAddr = sb.toString();
		String absolutePath = ImageUtil.generateProductImg(imageHolder.getImage(), imageHolder.getFileName(),
				targetAddr);

		return absolutePath;
	}

	private boolean deleteImg(ProductImg productImg) {
		return ImageUtil.deleteImage(productImg.getImgAddr());
	}

	@Override
	@Transactional
	public ProductImgExecution insertProductImg(Product product, List<ProductImageHolder> imageHolderList) {
		// TODO Auto-generated method stub
		ProductImgExecution pie = null;
		List<ProductImg> productImgList = new ArrayList<ProductImg>();
		for (ProductImageHolder pih : imageHolderList) {
			ProductImg img = new ProductImg();
			img.setProduct(product);
			pie = this.insertProductImg(img, pih);
			if (pie.getState() != ProductImgExecutionEnum.SUCCESS.getState()) {
				throw new ProductImgException("插入productImg的时候出现问题！");
			}
			productImgList.add(pie.getProductImg());
		}
		pie = new ProductImgExecution(ProductImgExecutionEnum.SUCCESS, productImgList);
		return pie;
	}

}
