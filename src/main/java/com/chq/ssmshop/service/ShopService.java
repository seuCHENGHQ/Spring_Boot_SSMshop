package com.chq.ssmshop.service;

import com.chq.ssmshop.dto.ImageHolder;
import com.chq.ssmshop.dto.ShopExecution;
import com.chq.ssmshop.entity.Shop;

public interface ShopService {
	ShopExecution registerShop(Shop shop, ImageHolder imageHolder);

	ShopExecution queryAllShop();

	ShopExecution updateShop(Shop shop, ImageHolder imageHolder);
	
	ShopExecution queryShop(long shopId);
	
	ShopExecution queryShopByOwnerId(long ownerId);
	
	ShopExecution getShopList(Shop shopCondition, int pageIndex, int pageSize);
	
//	public ShopExecution deleteShop(long shopId);
}
