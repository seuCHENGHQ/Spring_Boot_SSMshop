package com.chq.ssmshop.service;

import java.util.List;

import com.chq.ssmshop.dto.ImageHolder;
import com.chq.ssmshop.dto.ProductExecution;
import com.chq.ssmshop.dto.ProductImageHolder;
import com.chq.ssmshop.entity.Product;

public interface ProductService {
	ProductExecution insertProduct(Product product, ImageHolder productThumbnail);

	ProductExecution insertProduct(Product product, ImageHolder productThumbnail,
			List<ProductImageHolder> productImgList);
	
	ProductExecution changeProductEnableStatus(Product product);

	ProductExecution updateProduct(Product product, ImageHolder productThumbnail,
			List<ProductImageHolder> productImgList);

	ProductExecution selectProductListByShopId(long shopId);

	ProductExecution selectProductById(long productId);

	ProductExecution updateProduct(Product product, ImageHolder productThumbnail);

	ProductExecution deleteProduct(long productId);
	
	ProductExecution getProductList(Product productCondition, int pageIndex, int pageSize);
}
