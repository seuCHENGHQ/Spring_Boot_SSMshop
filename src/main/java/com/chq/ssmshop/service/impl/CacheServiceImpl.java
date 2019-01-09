package com.chq.ssmshop.service.impl;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chq.ssmshop.cache.JedisUtil;
import com.chq.ssmshop.service.CacheService;

@Service
public class CacheServiceImpl implements CacheService {
	@Autowired
	private JedisUtil.Keys jedisKeys;

	@Override
	public void removeFromCache(String keyPrefix) {
		// 找出redis中所有以keyPrefix开头的键
		Set<String> keySet = jedisKeys.keys(keyPrefix + "*");
		for(String key : keySet) {
			jedisKeys.del(key);
		}
	}
}
