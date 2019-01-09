package com.chq.ssmshop.dao.split;

import java.util.Locale;
import java.util.Properties;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.keygen.SelectKeyGenerator;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import ch.qos.logback.classic.Logger;

/*
 * 因为MyBatis会将insert update delete封装在update方法中，因此拦截update 和 query就可以覆盖到对数据库的增删改查操作
 */
@Intercepts({ @Signature(type = Executor.class, method = "update", args = { MappedStatement.class, Object.class }),
		@Signature(type = Executor.class, method = "query", args = { MappedStatement.class, Object.class,
				RowBounds.class, ResultHandler.class }) })
public class DynamicDataSourceInterceptor implements Interceptor {
	private static Logger logger = (Logger) LoggerFactory.getLogger(DynamicDataSourceInterceptor.class);
	public static final String REGEX = ".*insert\\u0020.*|.*update\\\\u0020.*|.*delete\\\\u0020.*";

	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		boolean synchronizationActive = TransactionSynchronizationManager.isActualTransactionActive();
		Object[] objects = invocation.getArgs();
		MappedStatement ms = (MappedStatement) objects[0];
		String lookupKey = DynamicDataSourceHolder.DB_MASTER;

		// 虽然开启未开启事务的都应该是select操作，但为了保险起见，防止某些update insert delete没有开启事务，而意外写进从库导致主从同步异常
		// 这里在未开启事务的操作中，也进行判断，对insert update delete方法同样使用主库进行对应的操作
		if (synchronizationActive != true) {
			// 虽然开启事务的操作都应该是insert update
			// delete这三种操作，但我们在insert时，也会用到查询操作的，因此要对这种查询操作选择使用主库进行查询
			if (ms.getSqlCommandType().equals(SqlCommandType.SELECT)) {
				// 比如我们在insert时会一般不会直接传入id信息，而是使用自增的主键值来进行回填，那么此时的事务还未结束，事务未提交
				// 在 提交读 这个隔离界别下，从库不可能获取到刚插入的这条信息，那么如果此时使用从库来进行select操作，就会读到一个空值
				// 因此在这里我们仍然要为该select操作指定主库来进行相应的查询操作
				// 在mybatis进行insert操作之后，会调用select last_insert_id()的方法来查询刚插入的这条记录的主键id，并进行回填
				if (ms.getId().contains(SelectKeyGenerator.SELECT_KEY_SUFFIX)) {
					/*
					 * 每个MappedStatement都保存了(select|insert|update|delete)中的一条SQL语句相关的信息 因此调用select
					 * last_insert_id()这条语句也会被封装在一个MappedStatement中
					 * 这里就是在检测这里的MappedStatement是不是封装select last_insert_id()这条语句的MappedStatement
					 */
					lookupKey = DynamicDataSourceHolder.DB_MASTER;
				} else {
					// 这里获取Executor要执行的sql语句
					BoundSql boundSql = ms.getSqlSource().getBoundSql(objects[1]);
					String sql = boundSql.getSql().toLowerCase(Locale.CHINA).replaceAll("\\t\\n\\r", " ");
					if (sql.matches(REGEX)) {
						lookupKey = DynamicDataSourceHolder.DB_MASTER;
					} else {
						lookupKey = DynamicDataSourceHolder.DB_SLAVE;
					}
				}
			}
		} else {
			lookupKey = DynamicDataSourceHolder.DB_MASTER;
		}
		logger.debug("设置方法[{}] use [{}] Strategy, SqlCommanType [{}]..", ms.getId(), lookupKey,
				ms.getSqlCommandType().name());
		DynamicDataSourceHolder.setDbType(lookupKey);
		return invocation.proceed();
	}

	@Override
	// 这里是mybatis对拦截器进行织入的部分，当前的拦截器只对Executor有效，因为Executor是真正进行增删改查操作的对象
	public Object plugin(Object target) {
		if (target instanceof Executor) {
			return Plugin.wrap(target, this);
		} else {
			return target;
		}
	}

	@Override
	public void setProperties(Properties properties) {
		// TODO Auto-generated method stub

	}

}
