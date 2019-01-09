package com.chq.ssmshop.dao;

import java.util.List;

import com.chq.ssmshop.entity.ProductCategory;

public interface ProductCategoryDao {
	Integer insertProductCategory(ProductCategory productCategory);
	
	Integer updateProductCategory(ProductCategory productCategory);
	
	List<ProductCategory> getProductCategoryListByShopId(long shopId);
	
	ProductCategory getProductCategoryByProductCategoryId(long productCategoryId);
	
	Integer deleteProductCategoryById(long productCategoryId);
}
