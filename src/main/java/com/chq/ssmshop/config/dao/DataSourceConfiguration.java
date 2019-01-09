package com.chq.ssmshop.config.dao;

import java.beans.PropertyVetoException;
import java.util.HashMap;
import java.util.Map;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.chq.ssmshop.dao.split.DynamicDataSource;
import com.chq.ssmshop.util.DESUtil;
import com.mchange.v2.c3p0.ComboPooledDataSource;

@Configuration
//配置mybatis mapper的扫描路径　有了这个注解，就不用再配置org.mybatis.spring.mapper.MapperScannerConfigurer这个类了
@MapperScan("com.chq.ssmshop.dao")
public class DataSourceConfiguration implements ApplicationContextAware {
	@Value("${jdbc.driver}")
	private String jdbcDriver;
	@Value("${jdbc.master.url}")
	private String jdbcMasterUrl;
	@Value("${jdbc.slave.url}")
	private String jdbcSlaveUrl;
	@Value("${jdbc.username}")
	private String jdbcUsername;
	@Value("${jdbc.password}")
	private String jdbcPassword;

	/**
	 * 生成spring-dao.xml中对应的bean datasource
	 * 
	 * @return
	 * @throws PropertyVetoException
	 */
//	@Bean(name = "dataSource")
//	public ComboPooledDataSource createDataSource() throws PropertyVetoException {
//		// 生成dataSource实例
//		ComboPooledDataSource dataSource = new ComboPooledDataSource();
//		// 和spring-dao中配置的一样，配置dataSource中的property属性
//		dataSource.setDriverClass(jdbcDriver);
//		dataSource.setJdbcUrl(jdbcMasterUrl);
//		dataSource.setUser(DESUtil.getDecryptString(jdbcUsername));
//		dataSource.setPassword(DESUtil.getDecryptString(jdbcPassword));
//
//		// 设置连接池的私有属性
//		dataSource.setMaxPoolSize(30);
//		dataSource.setMinPoolSize(10);
//		dataSource.setAutoCommitOnClose(false);
//		dataSource.setCheckoutTimeout(10000);
//		dataSource.setAcquireRetryAttempts(2);
//		return dataSource;
//	}

	private ApplicationContext applicationContext;

	@Bean(name = "master")
	public ComboPooledDataSource createMasterDataSource() throws PropertyVetoException {
		ComboPooledDataSource master = new ComboPooledDataSource();
		// 和spring-dao中配置的一样，配置dataSource中的property属性
		master.setDriverClass(jdbcDriver);
		master.setJdbcUrl(jdbcMasterUrl);
		master.setUser(DESUtil.getDecryptString(jdbcUsername));
		master.setPassword(DESUtil.getDecryptString(jdbcPassword));

		// 设置连接池的私有属性
		master.setMaxPoolSize(30);
		master.setMinPoolSize(10);
		master.setAutoCommitOnClose(false);
		master.setCheckoutTimeout(10000);
		master.setAcquireRetryAttempts(2);
		return master;
	}

	@Bean(name = "slave")
	public ComboPooledDataSource createSlaveDataSource() throws PropertyVetoException {
		ComboPooledDataSource slave = new ComboPooledDataSource();
		// 和spring-dao中配置的一样，配置dataSource中的property属性
		slave.setDriverClass(jdbcDriver);
		slave.setJdbcUrl(jdbcSlaveUrl);
		slave.setUser(DESUtil.getDecryptString(jdbcUsername));
		slave.setPassword(DESUtil.getDecryptString(jdbcPassword));

		// 设置连接池的私有属性
		slave.setMaxPoolSize(30);
		slave.setMinPoolSize(10);
		slave.setAutoCommitOnClose(false);
		slave.setCheckoutTimeout(10000);
		slave.setAcquireRetryAttempts(2);
		return slave;
	}

	@Bean(name = "dataSource")
	public DynamicDataSource createDynamicDataSource() {
		DynamicDataSource dataSource = new DynamicDataSource();
		Map<Object, Object> targetDataSources = new HashMap<>();
		targetDataSources.put("master", applicationContext.getBean("master"));
		targetDataSources.put("slave", applicationContext.getBean("slave"));
		dataSource.setTargetDataSources(targetDataSources);
		return dataSource;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}
}
