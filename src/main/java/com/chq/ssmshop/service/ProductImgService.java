package com.chq.ssmshop.service;

import java.util.List;

import com.chq.ssmshop.dto.ProductImageHolder;
import com.chq.ssmshop.dto.ProductImgExecution;
import com.chq.ssmshop.entity.Product;
import com.chq.ssmshop.entity.ProductImg;

public interface ProductImgService {
	ProductImgExecution insertProductImg(ProductImg productImg, ProductImageHolder imageHolder);
	
	ProductImgExecution insertProductImg(Product product, List<ProductImageHolder> imageHolderList);

	ProductImgExecution getProductImgListByProductId(long productId);

	ProductImgExecution getProductImgByProductImgId(long productImgId);

	ProductImgExecution deleteProductImgByProductId(long productId);

	ProductImgExecution deleteProductImgByProductImgId(long productImgId);
}
