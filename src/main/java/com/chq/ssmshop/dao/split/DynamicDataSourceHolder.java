package com.chq.ssmshop.dao.split;

import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Logger;

public class DynamicDataSourceHolder {
	private static Logger logger = (Logger) LoggerFactory.getLogger(DynamicDataSourceHolder.class);
	// 为什么要有这个线程本地变量?
	/*
	 * 这是因为可能在Spring应用中，有多个线程同时访问mapper，而这几个线程执行的sql又各不相同，因此他们所用到的数据库也不同，可能为主库，
	 * 也可能为从库 ThreadLocal保证了多线程情况下，各线程间不会相互干扰，能访问到自己需要的数据库
	 * 而这里，ThreadLocal变量被声明为static,因此会被保存在JVM的方法区内，且只有一个，因此即使java多线程模型中，
	 * 各线程也只能访问到同一个contextHolder变量
	 */
	private static ThreadLocal<String> contextHolder = new ThreadLocal<>();
	public static final String DB_MASTER = "master";
	public static final String DB_SLAVE = "slave";

	public static String getDbType() {
		String db = contextHolder.get();
		// 当mapper的方法第一次被某个线程调用时，该线程的contextHolder.get()就只能得到一个null,此时为了保险起见，使用主库
		if (db == null) {
			db = DB_MASTER;
		}
		return db;
	}

	public static void setDbType(String str) {
		logger.debug("所使用的数据源时：" + str);
		contextHolder.set(str);
	}

}
