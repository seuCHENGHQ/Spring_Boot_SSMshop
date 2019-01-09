package com.chq.ssmshop.config.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.chq.ssmshop.cache.JedisPoolWriper;
import com.chq.ssmshop.cache.JedisUtil;

import redis.clients.jedis.JedisPoolConfig;

@Configuration
public class RedisConfiguration {
	@Value("${redis.hostname}")
	private String hostname;
	@Value("${redis.port}")
	private int port;
	@Value("${redis.pool.maxActive}")
	private int maxTotal;
	@Value("${redis.pool.maxIdle}")
	private int maxIdle;
	@Value("${redis.pool.maxWait}")
	private long maxWaitMillis;
	@Value("${redis.pool.testOnBorrow}")
	private boolean testOnBorrow;

	@Autowired
	private JedisPoolConfig jedisPoolConfig;
	@Autowired
	private JedisPoolWriper jedisPoolWriper;
	@Autowired
	private JedisUtil jedisUtil;

	/**
	 * 配置Redis连接池的属性
	 * 
	 * @return
	 */
	@Bean(name = "jedisPoolConfig")
	public JedisPoolConfig createJedisPoolConfig() {
		JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
		jedisPoolConfig.setMaxTotal(maxTotal);
		jedisPoolConfig.setMaxIdle(maxIdle);
		jedisPoolConfig.setMaxWaitMillis(maxWaitMillis);
		jedisPoolConfig.setTestOnBorrow(testOnBorrow);
		return jedisPoolConfig;
	}

	/**
	 * 在该bean中生成了Redis连接池
	 * 
	 * @return
	 */
	@Bean(name = "jedisWritePool")
	public JedisPoolWriper createJedisPoolWriper() {
		JedisPoolWriper jedisPoolWriper = new JedisPoolWriper(jedisPoolConfig, hostname, port);
		return jedisPoolWriper;
	}

	/**
	 * 封装了对Redis的操作
	 * 
	 * @return
	 */
	@Bean(name = "jedisUtil")
	public JedisUtil createJedisUtil() {
		JedisUtil jedisUtil = new JedisUtil();
		jedisUtil.setJedisPool(jedisPoolWriper);
		return jedisUtil;
	}

	/**
	 * 封装了对Redis中的键的操作
	 * 
	 * @return
	 */
	@Bean(name = "jedisKeys")
	public JedisUtil.Keys createJedisUtilKeys() {
		JedisUtil.Keys jedisKeys = jedisUtil.new Keys();
		return jedisKeys;
	}

	/**
	 * 封装了对Redis中保存的Strings数据类型的操作
	 * 
	 * @return
	 */
	@Bean(name = "jedisStrings")
	public JedisUtil.Strings createJedisStrings() {
		JedisUtil.Strings jedisStrings = jedisUtil.new Strings();
		return jedisStrings;
	}
}
