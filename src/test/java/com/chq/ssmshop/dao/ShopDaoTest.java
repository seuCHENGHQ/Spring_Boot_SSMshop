package com.chq.ssmshop.dao;

import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.chq.ssmshop.entity.Area;
import com.chq.ssmshop.entity.PersonInfo;
import com.chq.ssmshop.entity.Shop;
import com.chq.ssmshop.entity.ShopCategory;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ShopDaoTest {
	@Autowired
	private ShopDao shopDao;

//	@Test
	public void insertShopTest() {
		Shop shop = new Shop();

		PersonInfo owner = new PersonInfo();
		owner.setUserId(1);
		shop.setOwner(owner);

		Area area = new Area();
		area.setAreaId(1);
		shop.setArea(area);

		ShopCategory shopCategory = new ShopCategory();
		shopCategory.setShopCategoryId(12);
		shop.setShopCategory(shopCategory);

		shop.setShopName("testShop1");
		shop.setShopDesc("descTest");
		shop.setShopAddr("addrTest");
		shop.setPhone("13027710396");
		shop.setShopImg("这里将来要放店铺的展示图片所在的路径");
		shop.setPriority(0);
		shop.setCreateTime(new Date());
		shop.setLastEditTime(new Date());
		shop.setEnableStatus(0);
		shop.setAdvice("adviceTest");

		int affectedRow = shopDao.insertShop(shop);
		assertEquals(affectedRow, 1);
	}

//	@Test
	public void updateShopTest() {
		Shop shop = new Shop();
		shop.setShopId(19);

		PersonInfo owner = new PersonInfo();
		owner.setUserId(1);
		shop.setOwner(owner);

		Area area = new Area();
		area.setAreaId(1);
		shop.setArea(area);

		ShopCategory shopCategory = new ShopCategory();
		shopCategory.setShopCategoryId(13);
		shop.setShopCategory(shopCategory);

		shop.setShopName("testShop1");
		shop.setShopDesc("descTest");
		shop.setShopAddr("addrTest");
		shop.setPhone("13027710396");
		shop.setShopImg("这里将来要放店铺的展示图片所在的路径updateTest");
		shop.setPriority(0);
		shop.setLastEditTime(new Date());
		shop.setEnableStatus(0);
		shop.setAdvice("updateTest");

		int affectedRow = shopDao.updateShop(shop);
		assertEquals(affectedRow, 1);
	}

	@Test
	public void queryAllShopTest() {
		List<Shop> shopList = shopDao.queryAllShop();
		System.out.println(shopList.size());
//		assertEquals(shopList.size(), 8);
	}

//	@Test
	public void queryShopTest() {
		Shop shop = shopDao.queryShop(7);
		assertEquals(shop.getShopId().intValue(), 7);
	}

//	@Test
	public void queryShopByOwnerId() {
		List<Shop> shopList = shopDao.queryShopByOwnerId(1);
		assertEquals(8, shopList.size());
	}

//	@Test
	public void queryShopListTest() {
		Shop shopCondition = new Shop();

		ShopCategory child = new ShopCategory();
//		child.setShopCategoryId(10);
		ShopCategory parent = new ShopCategory();
		parent.setShopCategoryId(4);
		child.setParent(parent);

		shopCondition.setShopCategory(child);

		List<Shop> shopList = shopDao.queryShopList(shopCondition, 0, 6);
		int count = shopDao.queryShopCount(shopCondition);
		assertEquals(count, shopList.size());

	}
}
