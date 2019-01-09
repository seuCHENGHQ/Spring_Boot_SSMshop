package com.chq.ssmshop.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chq.ssmshop.cache.JedisUtil;
import com.chq.ssmshop.dao.AreaDao;
import com.chq.ssmshop.entity.Area;
import com.chq.ssmshop.service.AreaService;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ch.qos.logback.classic.Logger;

@Service
public class AreaServiceImpl implements AreaService {
	private Logger logger = (Logger) LoggerFactory.getLogger(AreaServiceImpl.class);

	@Autowired
	private AreaDao areaDao;
	@Autowired
	private JedisUtil.Keys jedisKeys;
	@Autowired
	private JedisUtil.Strings jedisStrings;

	@Override
	/**
	 * 加入Redis后要对这里进行改写 调用service的方法时，应该先从Redis中取数据，如果取不到，再从数据库中取数据，并将数据存储到Redis中
	 */
	public List<Area> queryArea() {
		String key = AREALISTKEY;
		List<Area> areaList = null;
		/*
		 * 因为areaList在Redis中是以JSON字符串的形式存储的，而前台需要的是List对象，因此这里需要将Redis中的JSON字符串转换为List对象
		 * 当向Redis中存储数据的时候，需要将List对象转换为JSON字符串
		 */
		ObjectMapper mapper = new ObjectMapper();
		if (!jedisKeys.exists(key)) {
			areaList = areaDao.listArea();
			String jsonString = null;
			try {
				jsonString = mapper.writeValueAsString(areaList);
			} catch (JsonProcessingException e) {
				logger.error(e.getMessage());
				e.printStackTrace();
			}
			jedisStrings.set(key, jsonString);
		} else {
			String jsonString = jedisStrings.get(key);
			JavaType javaType = mapper.getTypeFactory().constructParametricType(ArrayList.class, Area.class);
			try {
				areaList = mapper.readValue(jsonString, javaType);
			} catch (JsonParseException e) {
				logger.error(e.getMessage());
				e.printStackTrace();
			} catch (JsonMappingException e) {
				logger.error(e.getMessage());
				e.printStackTrace();
			} catch (IOException e) {
				logger.error(e.getMessage());
				e.printStackTrace();
			}
		}
		return areaList;
	}

}
