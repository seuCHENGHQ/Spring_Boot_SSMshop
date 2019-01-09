package com.chq.ssmshop.dao.split;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class DynamicDataSource extends AbstractRoutingDataSource {

	@Override
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource#
	 * determineCurrentLookupKey()
	 * 
	 * 对于AbstractRoutingDataSource来说，其中有一个 private Map<Object, Object>
	 * targetDataSources;
	 * 这样一个map来保存多个数据源，根据该determineCurrentLookupKey()方法返回的不同值来从map中取出不同的数据源来使用
	 * 
	 * 这里可以看出，使用了状态模式，由拦截器来决定DynamicDataSourceHolder.getDbType()返回什么字符串，
	 * 从而由Map中取出了不同数据源，实现了读写分离
	 */
	protected Object determineCurrentLookupKey() {
		return DynamicDataSourceHolder.getDbType();
	}

}
