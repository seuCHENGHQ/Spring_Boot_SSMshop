package com.chq.ssmshop.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.chq.ssmshop.entity.ShopCategory;

public interface ShopCategoryDao {
	List<ShopCategory> queryAllShopCategory();
	
	List<ShopCategory> queryShopCategory(@Param("shopCategoryCondition") ShopCategory shopCategoryCondition);
	
	int insertShopCategory(ShopCategory shopCategory);
}
