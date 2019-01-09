package com.chq.ssmshop.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chq.ssmshop.cache.JedisUtil;
import com.chq.ssmshop.dao.HeadLineDao;
import com.chq.ssmshop.entity.HeadLine;
import com.chq.ssmshop.service.HeadLineService;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import ch.qos.logback.classic.Logger;

@Service
public class HeadLineServiceImpl implements HeadLineService {
	private Logger logger = (Logger) LoggerFactory.getLogger(HeadLineServiceImpl.class);

	@Autowired
	private HeadLineDao headLineDao;
	@Autowired
	private JedisUtil.Keys jedisKeys;
	@Autowired
	private JedisUtil.Strings jedisStrings;

	@Override
	public List<HeadLine> getHeadLineList(HeadLine headLineCondition) {
		String key = KEYSTRING;
		List<HeadLine> headLineList = null;
		ObjectMapper mapper = new ObjectMapper();
		if (headLineCondition != null && headLineCondition.getEnableStatus() != null) {
			// 根据不同的enableStatus来产生不同的键，保存不同的头条列表
			/*
			 * 在这里一共会产生三种不同的key: 1.headlinelist_0 2.headlinelist_1 3.headlinelist
			 */
			key = key + "_" + headLineCondition.getEnableStatus();
		}

		if (!jedisKeys.exists(key)) {
			headLineList = headLineDao.queryHeadLine(headLineCondition);
			String headLineStr = null;
			try {
				headLineStr = mapper.writeValueAsString(headLineList);
				jedisStrings.set(key, headLineStr);
			} catch (Exception e) {
				logger.error(e.getMessage());
				e.printStackTrace();
			}
		} else {
			JavaType javaType = mapper.getTypeFactory().constructParametricType(ArrayList.class, HeadLine.class);
			String headLineStr = null;
			try {
				headLineStr = jedisStrings.get(key);
				headLineList = mapper.readValue(headLineStr, javaType);
			} catch (Exception e) {
				logger.error(e.getMessage());
				e.printStackTrace();
			}
		}

		return headLineList;
	}

}
