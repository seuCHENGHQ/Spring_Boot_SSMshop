package com.chq.ssmshop.config.dao;

import java.beans.PropertyVetoException;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.chq.ssmshop.util.DESUtil;
import com.mchange.v2.c3p0.ComboPooledDataSource;

@Configuration
//配置mybatis mapper的扫描路径　有了这个注解，就不用再配置org.mybatis.spring.mapper.MapperScannerConfigurer这个类了
@MapperScan("com.chq.ssmshop.dao")
public class DataSourceConfiguration {
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
	@Bean(name = "dataSource")
	public ComboPooledDataSource createDataSource() throws PropertyVetoException {
		// 生成dataSource实例
		ComboPooledDataSource dataSource = new ComboPooledDataSource();
		// 和spring-dao中配置的一样，配置dataSource中的property属性
		dataSource.setDriverClass(jdbcDriver);
		dataSource.setJdbcUrl(jdbcMasterUrl);
		dataSource.setUser(DESUtil.getDecryptString(jdbcUsername));
		dataSource.setPassword(DESUtil.getDecryptString(jdbcPassword));

		// 设置连接池的私有属性
		dataSource.setMaxPoolSize(30);
		dataSource.setMinPoolSize(10);
		dataSource.setAutoCommitOnClose(false);
		dataSource.setCheckoutTimeout(10000);
		dataSource.setAcquireRetryAttempts(2);
		return dataSource;
	}
}
