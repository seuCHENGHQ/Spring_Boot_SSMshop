package com.chq.ssmshop.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.chq.ssmshop.entity.Product;

public interface ProductDao {
	int insertProduct(Product product);

	Product getProductByProductId(long productId);

	List<Product> getProductListByShopId(long shopId);

	int updateProductByProductId(Product product);

	int deleteProductByProductId(long productId);

	int updateProductCategoryToNull(long productCategoryId);

	/**
	 * 查询商品列表并分页，可输入的条件有：商品名（模糊），商品状态，店铺Id,商品类别
	 * 
	 * @param productCondition
	 * @param beginIndex
	 * @param pageSize
	 * @return
	 */
	List<Product> queryProductList(@Param("productCondition") Product productCondition, @Param("rowIndex") int rowIndex,
			@Param("pageSize") int pageSize);

	/**
	 * 查询对应的商品总数
	 * 
	 * @param productCondition
	 * @return
	 */
	int queryProductCount(@Param("productCondition") Product productCondition);
}
