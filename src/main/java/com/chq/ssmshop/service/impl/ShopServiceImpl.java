package com.chq.ssmshop.service.impl;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chq.ssmshop.dao.ShopDao;
import com.chq.ssmshop.dto.ImageHolder;
import com.chq.ssmshop.dto.ShopExecution;
import com.chq.ssmshop.entity.Shop;
import com.chq.ssmshop.enums.ShopExecutionEnum;
import com.chq.ssmshop.exception.ShopOperationException;
import com.chq.ssmshop.service.ShopService;
import com.chq.ssmshop.util.ImageUtil;
import com.chq.ssmshop.util.PageCalculator;

@Service
public class ShopServiceImpl implements ShopService {

	@Autowired
	private ShopDao shopDao;

	@Override
	@Transactional
	public ShopExecution registerShop(Shop shop, ImageHolder imageHolder) {
		// TODO Auto-generated method stub
		if (shop == null) {
			return new ShopExecution(ShopExecutionEnum.NULL_SHOP);
		} else if (shop.getOwner() == null) {
			return new ShopExecution(ShopExecutionEnum.NULL_OWNER);
		} else if (shop.getShopCategory() == null) {
			return new ShopExecution(ShopExecutionEnum.NULL_SHOP_CATEGORY);
		} else if (shop.getShopName() == null) {
			return new ShopExecution(ShopExecutionEnum.NULL_SHOP_NAME);
		} else if (shop.getShopDesc() == null) {
			return new ShopExecution(ShopExecutionEnum.NULL_SHOP_DESC);
		}
		// 现将店铺信息插入到数据库中,以获取该店铺在数据库中的shop_id，为下边的插入图片做准备
		shop.setEnableStatus(0);
		shop.setCreateTime(new Date());
		shop.setLastEditTime(new Date());
		int effectedRow = shopDao.insertShop(shop);
		if (effectedRow <= 0) {
			// 这里抛出一个RuntimeException是为了让事务进行回滚
			throw new ShopOperationException("shopDao在创建店铺时出现异常！");
		}
		if (imageHolder.getImage() != null) {
			try {
				addImg(shop, imageHolder);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				throw new ShopOperationException("添加店铺缩略图出现异常！");
			}
			shop.setLastEditTime(new Date());
			effectedRow = shopDao.updateShop(shop);
			if (effectedRow <= 0) {
				throw new ShopOperationException("更新店铺缩略图绝对路径时出现问题！");
			}
		}
		return new ShopExecution(ShopExecutionEnum.SUCCESS, shop);
	}

	@Override
	public ShopExecution queryAllShop() {
		// TODO Auto-generated method stub
		List<Shop> shopList = shopDao.queryAllShop();
		ShopExecution se = new ShopExecution(ShopExecutionEnum.SUCCESS, shopList);
		return se;
	}

	@Override
	@Transactional
	public ShopExecution updateShop(Shop shop, ImageHolder imageHolder) {
		// TODO Auto-generated method stub
		if (shop == null || shop.getShopId() == null) {
			return new ShopExecution(ShopExecutionEnum.NULL_SHOP);
		}
		// 如果图片不为null，图片名字也不为空，则进行图片的更新
		if (imageHolder.getImage() != null && imageHolder.getFileName() != null
				&& !"".equals(imageHolder.getFileName())) {
			Shop existShop = shopDao.queryShop(shop.getShopId());
			// 如果Img已经存在值了，说明已有一个缩略图，应该将其删除，再放置新的
			if (existShop.getShopImg() != null) {
				deleteImg(existShop);
			}
			try {
				addImg(shop, imageHolder);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				throw new ShopOperationException("在更新店铺缩略图时出现异常！");
			}
			shop.setLastEditTime(new Date());
			int effectedRow = shopDao.updateShop(shop);
			if (effectedRow <= 0) {
				throw new ShopOperationException("在更新店铺时出现异常！");
			} else {
				return new ShopExecution(ShopExecutionEnum.SUCCESS, existShop);
			}
		}
		return new ShopExecution(ShopExecutionEnum.UNSUCCESS);
	}

	private void addImg(Shop shop, ImageHolder imageHolder) throws IOException {
		String targetAddr = shop.getShopId() + "/";
		String absolutePath = ImageUtil.generateThumbnail(imageHolder.getImage(), imageHolder.getFileName(),
				targetAddr);
		shop.setShopImg(absolutePath);
	}

	private void deleteImg(Shop shop) {
		String imgAbsolutePath = shop.getShopImg();
		ImageUtil.deleteImage(imgAbsolutePath);
	}

	@Override
	public ShopExecution queryShop(long shopId) {
		// TODO Auto-generated method stub
		Shop shop = shopDao.queryShop(shopId);
		ShopExecution se = new ShopExecution(ShopExecutionEnum.SUCCESS, shop);
		return se;
	}

	@Override
	public ShopExecution queryShopByOwnerId(long ownerId) {
		// TODO Auto-generated method stub
		List<Shop> shopList = shopDao.queryShopByOwnerId(ownerId);
		ShopExecution se = new ShopExecution(ShopExecutionEnum.SUCCESS, shopList);
		return se;
	}

	@Override
	public ShopExecution getShopList(Shop shopCondition, int pageIndex, int pageSize) {
		// TODO Auto-generated method stub
		int startIndex = PageCalculator.calculateRowIndex(pageIndex, pageSize);
		// 这里得到的是分页内的所有店铺列表
		List<Shop> shopList = shopDao.queryShopList(shopCondition, startIndex, pageSize);
		// 这里得到的是符合查询条件的的所有店铺列表
		int count = shopDao.queryShopCount(shopCondition);
		ShopExecution se = null;
		if(shopList!=null) {
			se = new ShopExecution(ShopExecutionEnum.SUCCESS, shopList);
			se.setCount(count);
		}else {
			se = new ShopExecution(ShopExecutionEnum.UNSUCCESS);
		}
		return se;
	}

}
