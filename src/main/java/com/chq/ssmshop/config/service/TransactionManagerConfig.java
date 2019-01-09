package com.chq.ssmshop.config.service;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

@Configuration
//首先使用注解@EnableTransactionManagement开启事务的支持
//在Service方法上添加注解@Transactional即可开启事务的支持
@EnableTransactionManagement
public class TransactionManagerConfig implements TransactionManagementConfigurer {

	@Autowired
	private DataSource dataSource;

	@Override
	/**
	 * 用于返回事务管理器的实现类
	 */
	public PlatformTransactionManager annotationDrivenTransactionManager() {
		// TODO Auto-generated method stub
		return new DataSourceTransactionManager(dataSource);
	}

}
