package com.chq.ssmshop.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chq.ssmshop.dao.LocalAuthDao;
import com.chq.ssmshop.dao.PersonInfoDao;
import com.chq.ssmshop.entity.LocalAuth;
import com.chq.ssmshop.exception.LocalAuthOperationException;
import com.chq.ssmshop.service.LocalAuthService;
import com.chq.ssmshop.util.MD5;

@Service
public class LocalAuthServiceImpl implements LocalAuthService {
	@Autowired
	private LocalAuthDao localAuthDao;
	@Autowired
	private PersonInfoDao personInfoDao;

	@Override
	public LocalAuth getLocalAuthByUsernameAndPassword(String username, String password) {
		return localAuthDao.queryLocalByUserNameAndPwd(username, MD5.getMd5(password));
	}

	@Override
	public LocalAuth getLocalAuthByUserId(long userId) {
		return localAuthDao.queryLocalByUserId(userId);
	}

	@Override
	@Transactional
	public LocalAuth insertLocalAuth(LocalAuth localAuth) {
		if (localAuth == null) {
			throw new LocalAuthOperationException("null local auth");
		} else if (localAuth.getUsername() == null || localAuth.getPassword() == null) {
			throw new LocalAuthOperationException("null username or password");
		} else if (localAuth.getPersonInfo() == null) {
			throw new LocalAuthOperationException("null user data");
		} else if (localAuth.getPersonInfo().getUserType() == null) {
			throw new LocalAuthOperationException("null user type");
		} else if (localAuth.getPersonInfo().getEnableStatus() == null) {
			throw new LocalAuthOperationException("null user enable status");
		} else if (localAuth.getUsername() == null || localAuth.getUsername().length() == 0) {
			throw new LocalAuthOperationException("null user name");
		}

		localAuth.setCreateTime(new Date());
		localAuth.setLastEditTime(new Date());
		localAuth.getPersonInfo().setCreateTime(new Date());
		localAuth.getPersonInfo().setLastEditTime(new Date());
		localAuth.setPassword(MD5.getMd5(localAuth.getPassword()));

		try {
			int effectedRow = personInfoDao.insertPersonInfo(localAuth.getPersonInfo());
			if (effectedRow < 1) {
				throw new LocalAuthOperationException("插入personInfo出现异常");
			}
		} catch (Exception e) {
			throw new LocalAuthOperationException("插入personInfo出现异常");
		}

		try {
			int effectedRow = localAuthDao.insertLocalAuth(localAuth);
			if (effectedRow < 1) {
				throw new LocalAuthOperationException("插入localAuth出现异常");
			}
		} catch (Exception e) {
			throw new LocalAuthOperationException("插入localAuth出现异常");
		}
		return localAuth;
	}

	@Override
	@Transactional
	public LocalAuth updateLocalAuth(long userId, String username, String password, String newPassword,
			Date lastEditTime) {
		try {
			int effectedRow = localAuthDao.updateLocalAuth(userId, username, MD5.getMd5(password), MD5.getMd5(newPassword), new Date());
			if (effectedRow < 1) {
				throw new LocalAuthOperationException("更新localAuth出现异常");
			}
		} catch (Exception e) {
			throw new LocalAuthOperationException("更新localAuth出现异常");
		}

		return localAuthDao.queryLocalByUserId(userId);
	}

}
