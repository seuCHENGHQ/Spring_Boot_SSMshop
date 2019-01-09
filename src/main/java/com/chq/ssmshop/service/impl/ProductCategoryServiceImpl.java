package com.chq.ssmshop.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chq.ssmshop.dao.ProductCategoryDao;
import com.chq.ssmshop.dao.ProductDao;
import com.chq.ssmshop.dto.ProductCategoryExecution;
import com.chq.ssmshop.entity.ProductCategory;
import com.chq.ssmshop.enums.ProductCategoryExecutionEnum;
import com.chq.ssmshop.exception.ProductCategoryOperationException;
import com.chq.ssmshop.service.ProductCategoryService;

@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {

	@Autowired
	private ProductCategoryDao productCategoryDao;
	@Autowired
	private ProductDao productDao;

	@Override
	@Transactional
	public ProductCategoryExecution insertProductCategory(ProductCategory productCategory) {
		// TODO Auto-generated method stub
		if (productCategory.getShop() == null || productCategory.getShop().getShopId() == null) {
			return new ProductCategoryExecution(ProductCategoryExecutionEnum.NULL_SHOP_ID);
		} else if (productCategory.getProductCategoryName() == null) {
			return new ProductCategoryExecution(ProductCategoryExecutionEnum.NULL_PRODUCT_CATEGORY_NAME);
		}

		productCategory.setCreateTime(new Date());
		productCategory.setLastEditTime(new Date());
		if (productCategory.getPriority() == null) {
			productCategory.setPriority(0);
		}
		int effectedRow = productCategoryDao.insertProductCategory(productCategory);
		if (effectedRow < 1) {
			throw new ProductCategoryOperationException("插入商品类别时出现错误！");
		}

		return new ProductCategoryExecution(ProductCategoryExecutionEnum.SUCCESS, productCategory);
	}

	@Override
	@Transactional
	public ProductCategoryExecution updateProductCategory(ProductCategory productCategory) {
		// TODO Auto-generated method stub
		if (productCategory.getProductCategoryId() == null) {
			return new ProductCategoryExecution(ProductCategoryExecutionEnum.NULL_PRODUCT_CATEGORY_ID);
		} else if (productCategory.getProductCategoryName() == null) {
			return new ProductCategoryExecution(ProductCategoryExecutionEnum.NULL_PRODUCT_CATEGORY_NAME);
		}

		productCategory.setLastEditTime(new Date());
		int effectedRow = productCategoryDao.updateProductCategory(productCategory);
		if (effectedRow < 1) {
			throw new ProductCategoryOperationException("更新商品类别时出现错误！" + productCategory.getProductCategoryId());
		}
		return new ProductCategoryExecution(ProductCategoryExecutionEnum.SUCCESS, productCategory);
	}

	@Override
	public ProductCategoryExecution getProductCategoryListByShopId(long shopId) {
		// TODO Auto-generated method stub
		List<ProductCategory> productCategoryList = productCategoryDao.getProductCategoryListByShopId(shopId);
		ProductCategoryExecution pce = new ProductCategoryExecution(ProductCategoryExecutionEnum.SUCCESS,
				productCategoryList);
		return pce;
	}

	@Override
	public ProductCategoryExecution getProductCategoryByProductCategoryId(long productCategoryId) {
		// TODO Auto-generated method stub
		ProductCategory productCategory = productCategoryDao.getProductCategoryByProductCategoryId(productCategoryId);
		ProductCategoryExecution pce = new ProductCategoryExecution(ProductCategoryExecutionEnum.SUCCESS,
				productCategory);
		return pce;
	}

	@Override
	@Transactional
	public ProductCategoryExecution deleteProductCategoryById(long productCategoryId) {
		// TODO Auto-generated method stub
		try {
			int effectedRow = productDao.updateProductCategoryToNull(productCategoryId);
			if (effectedRow < 0) {
				throw new ProductCategoryOperationException("删除商品类别时出现错误！" + productCategoryId);
			}
		} catch (Exception e) {
			throw new RuntimeException("删除商品类别时出现错误！" + e.getMessage());
		}
		ProductCategory productCategory = productCategoryDao.getProductCategoryByProductCategoryId(productCategoryId);
		int effectedRow = productCategoryDao.deleteProductCategoryById(productCategoryId);
		if (effectedRow < 1) {
			throw new ProductCategoryOperationException("删除商品类别时出现错误！" + productCategoryId);
		}
		return new ProductCategoryExecution(ProductCategoryExecutionEnum.SUCCESS, productCategory);
	}

	@Override
	@Transactional
	public ProductCategoryExecution batchInsertProductCategory(List<ProductCategory> productCategoryList) {
		// TODO Auto-generated method stub
		if (productCategoryList == null || productCategoryList.size() == 0) {
			return new ProductCategoryExecution(ProductCategoryExecutionEnum.NULL_PRODUCT_CATEGORY_LIST);
		}
		for (ProductCategory pc : productCategoryList) {
			ProductCategoryExecution pce = this.insertProductCategory(pc);
			if (pce.getState() != ProductCategoryExecutionEnum.SUCCESS.getState()) {
				throw new ProductCategoryOperationException("批量插入商品类别时出现错误！" + pce.getStateInfo());
			}
		}
		return new ProductCategoryExecution(ProductCategoryExecutionEnum.SUCCESS, productCategoryList);
	}

}
