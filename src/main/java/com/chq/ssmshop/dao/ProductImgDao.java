package com.chq.ssmshop.dao;

import java.util.List;

import com.chq.ssmshop.entity.ProductImg;

public interface ProductImgDao {
	int insertProductImg(ProductImg productImg);
	
	ProductImg getProductImgByProductImgId(long productImgId);
	
	List<ProductImg> getProductImgListByProductId(long productId);
	
	int deleteProductImg(long productImgId);
}
