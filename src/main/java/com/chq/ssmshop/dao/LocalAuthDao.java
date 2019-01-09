package com.chq.ssmshop.dao;

import java.util.Date;

import org.apache.ibatis.annotations.Param;

import com.chq.ssmshop.entity.LocalAuth;

public interface LocalAuthDao {

	/**
	 * 通过用户名和密码查找对应的账号信息
	 * 
	 * @param username
	 * @param password
	 * @return
	 */
	LocalAuth queryLocalByUserNameAndPwd(@Param("username") String username, @Param("password") String password);

	/**
	 * 通过用户信息id查找该用户的localAuth
	 * 
	 * @param userId
	 * @return
	 */
	LocalAuth queryLocalByUserId(@Param("userId") long userId);

	/**
	 * 插入账号信息，注册账号时使用的
	 * 
	 * @param localAuth
	 * @return
	 */
	int insertLocalAuth(LocalAuth localAuth);

	/**
	 * 修改已存在账号的密码 必须当userId,username,oldPassword全部一致时才允许修改密码，以提高安全性
	 * 
	 * @param userId
	 * @param username
	 * @param password
	 * @param newPassword
	 * @param lastEditTime
	 * @return
	 */
	int updateLocalAuth(@Param("userId") long userId, @Param("username") String username,
			@Param("password") String password, @Param("newPassword") String newPassword,
			@Param("lastEditTime") Date lastEditTime);
}
