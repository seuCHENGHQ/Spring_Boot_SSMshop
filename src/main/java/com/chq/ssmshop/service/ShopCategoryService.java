package com.chq.ssmshop.service;

import java.util.List;

import com.chq.ssmshop.entity.ShopCategory;

public interface ShopCategoryService {
	public static final String KEYSTRING = "shopcategorylist";

	List<ShopCategory> queryAllShopCategory();

	List<ShopCategory> getShopCategoryList(ShopCategory shopCategoryCondition);
}
