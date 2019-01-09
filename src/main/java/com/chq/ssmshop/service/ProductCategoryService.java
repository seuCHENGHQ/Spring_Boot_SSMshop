package com.chq.ssmshop.service;

import java.util.List;

import com.chq.ssmshop.dto.ProductCategoryExecution;
import com.chq.ssmshop.entity.ProductCategory;

public interface ProductCategoryService {
	ProductCategoryExecution insertProductCategory(ProductCategory productCategory);

	ProductCategoryExecution updateProductCategory(ProductCategory productCategory);

	ProductCategoryExecution getProductCategoryListByShopId(long shopId);

	ProductCategoryExecution getProductCategoryByProductCategoryId(long productCategoryId);

	ProductCategoryExecution deleteProductCategoryById(long productCategoryId);
	
	ProductCategoryExecution batchInsertProductCategory(List<ProductCategory> productCategoryList);
}
