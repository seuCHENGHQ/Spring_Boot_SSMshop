package com.chq.ssmshop.dao;

import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.chq.ssmshop.entity.ProductCategory;
import com.chq.ssmshop.entity.Shop;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductCategoryDaoTest{
	@Autowired
	private ProductCategoryDao pcd;
	
//	@Test
	public void insertProductCategoryTest() {
		ProductCategory pc = new ProductCategory();
		pc.setProductCategoryName("测试名称１");
		pc.setPriority(0);
		pc.setCreateTime(new Date());
		pc.setLastEditTime(new Date());
		Shop shop = new Shop();
		shop.setShopId(9);
		pc.setShop(shop);
		int effected = pcd.insertProductCategory(pc);
		assertEquals(1, effected);
		
	}
	
//	@Test
	public void updateProductCategoryTest() {
		ProductCategory pc = new ProductCategory();
		pc.setProductCategoryId(2);
		pc.setProductCategoryName("更新测试");
		pc.setPriority(1);
		pc.setLastEditTime(new Date());
		int effected = pcd.updateProductCategory(pc);
		assertEquals(1, effected);
	}
	
	@Test
	public void getProductCategoryListByIdTest() {
		List<ProductCategory> pcList = pcd.getProductCategoryListByShopId(9);
		assertEquals(1, pcList.size());
	}
	
//	@Test
	public void getProductCategoryByProductCategoryIdTest() {
		ProductCategory pc = pcd.getProductCategoryByProductCategoryId(2);
		assertEquals(2, pc.getProductCategoryId().intValue());
	}
	
//	@Test
	public void deleteProductCategoryByIdTest() {
		int effected = pcd.deleteProductCategoryById(3);
		assertEquals(1, effected);
		
	}
}
