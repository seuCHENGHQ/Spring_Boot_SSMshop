package com.chq.ssmshop.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.chq.ssmshop.entity.Shop;

public interface ShopDao {
	int insertShop(Shop shop);

	int updateShop(Shop shop);

	List<Shop> queryAllShop();

	Shop queryShop(long shopId);

	/**
	 * 分页查询商店,可输入的条件有:店铺名(模糊),店铺状态,店铺类别,区域,owner
	 * 
	 * @param shopCondition 其中装载了上边写的5个输入条件
	 * @param rowIndex      从第几行开始取
	 * @param pageSize      取多少行
	 * @return
	 */
	List<Shop> queryShopList(@Param("shopCondition") Shop shopCondition, @Param("rowIndex") int rowIndex,
			@Param("pageSize") int pageSize);

	List<Shop> queryShopByOwnerId(long ownerId);

	/**
	 * 
	 * @return 返回queryShopList总数
	 */
	int queryShopCount(@Param("shopCondition") Shop shopCondition);
}
