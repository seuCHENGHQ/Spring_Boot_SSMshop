package com.chq.ssmshop.dao;

import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.chq.ssmshop.entity.Product;
import com.chq.ssmshop.entity.ProductImg;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductImgDaoTest {

	@Autowired
	private ProductImgDao productImgDao;

//	@Test
	public void insertProductImgTest() {
		ProductImg pi = new ProductImg();
		Product product = new Product();
		product.setProductId(4);

		pi.setImgAddr("inserttest");
		pi.setImgDesc("inserttest");
		pi.setPriority(0);
		pi.setCreateTime(new Date());
		pi.setProduct(product);

		int effectedRow = productImgDao.insertProductImg(pi);
		assertEquals(1, effectedRow);
	}

//	@Test
	public void selectProductListTest() {
		List<ProductImg> productImgList = productImgDao.getProductImgListByProductId(4);
		assertEquals(2, productImgList.size());
	}

	@Test
	public void selectProductTest() {
		ProductImg productImg = productImgDao.getProductImgByProductImgId(2);
		assertEquals(2, productImg.getProductImgId().intValue());
	}

//	@Test
	public void deleteProductImgTest() {
		int effectedRow = productImgDao.deleteProductImg(1);
		assertEquals(1, effectedRow);
	}
}
