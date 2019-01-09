package com.chq.ssmshop.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chq.ssmshop.cache.JedisUtil;
import com.chq.ssmshop.dao.ShopCategoryDao;
import com.chq.ssmshop.entity.ShopCategory;
import com.chq.ssmshop.service.ShopCategoryService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import ch.qos.logback.classic.Logger;

@Service
public class ShopCategroyServiceImpl implements ShopCategoryService {
	private Logger logger = (Logger) LoggerFactory.getLogger(ShopCategroyServiceImpl.class);

	@Autowired
	private ShopCategoryDao shopCategoryDao;

	@Autowired
	private JedisUtil.Keys jedisKeys;
	@Autowired
	private JedisUtil.Strings jedisStrings;

	@Override
	public List<ShopCategory> queryAllShopCategory() {
		List<ShopCategory> shopCategoryList = shopCategoryDao.queryAllShopCategory();
		return shopCategoryList;
	}

	@Override
	public List<ShopCategory> getShopCategoryList(ShopCategory shopCategoryCondition) {
		String key = KEYSTRING;
		List<ShopCategory> shopCategoryList = null;

		ObjectMapper mapper = new ObjectMapper();
		if (shopCategoryCondition == null) {
			key = key + "_allfirstlevel";
		} else if (shopCategoryCondition.getParent() != null
				&& shopCategoryCondition.getParent().getShopCategoryId() != null) {
			key = key + "_parent" + shopCategoryCondition.getParent().getShopCategoryId();
		} else if (shopCategoryCondition != null) {
			// 这个是创建店铺时使用的，创建店铺时，我们的店铺类别一定是一个二级类别，因此需要将所有的二级类别都列出来，为什么这样能取出所有的二级类别，见mapper.xml中的sql
			key = key + "_allsecondlevel";
		}

		if (!jedisKeys.exists(key)) {
			shopCategoryList = shopCategoryDao.queryShopCategory(shopCategoryCondition);
			String shopCategoryStr = null;
			try {
				shopCategoryStr = mapper.writeValueAsString(shopCategoryList);
				jedisStrings.set(key, shopCategoryStr);
			} catch (JsonProcessingException e) {
				logger.error(e.getMessage());
				e.printStackTrace();
			}
		} else {
			String shopCategoryListStr = null;
			shopCategoryListStr = jedisStrings.get(key);
			JavaType javaType = mapper.getTypeFactory().constructParametricType(ArrayList.class, ShopCategory.class);
			try {
				shopCategoryList = mapper.readValue(shopCategoryListStr, javaType);
			} catch (IOException e) {
				logger.error(e.getMessage());
				e.printStackTrace();
			}
		}

		return shopCategoryList;

	}

}
