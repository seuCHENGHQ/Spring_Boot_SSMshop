package com.chq.ssmshop.config.dao;

import java.io.IOException;

import javax.sql.DataSource;

import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

@Configuration
public class SessionFactoryConfiguration {
	@Autowired
	private DataSource dataSource;

	// mybatis的配置文件所在路径
	@Value("${mybatis_config_file}")
	private String mybatisConfigFile;

	// mapper.xml文件所在的位置
	@Value("${mapper_path}")
	private String mapperPath;

	// 实体类所在的位置
	@Value("${typa_alias_package}")
	private String typeAliasPackage;

	/**
	 * 向IoC容器中注入SqlSessionFactoryBean
	 * 
	 * @return
	 * @throws IOException
	 */
	@Bean(name = "sqlSessionFactory")
	public SqlSessionFactoryBean createSqlSessionFactory() throws IOException {
		SqlSessionFactoryBean sqlSessionFactory = new SqlSessionFactoryBean();
		sqlSessionFactory.setConfigLocation(new ClassPathResource(mybatisConfigFile));
		// 添加mapper.xml扫描路径
		PathMatchingResourcePatternResolver pathMatchingResourcePatternResolver = new PathMatchingResourcePatternResolver();
		String packageSerachPath = PathMatchingResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX + mapperPath;
		sqlSessionFactory.setMapperLocations(pathMatchingResourcePatternResolver.getResources(packageSerachPath));

		sqlSessionFactory.setDataSource(dataSource);
		sqlSessionFactory.setTypeAliasesPackage(typeAliasPackage);
		return sqlSessionFactory;
	}
}
