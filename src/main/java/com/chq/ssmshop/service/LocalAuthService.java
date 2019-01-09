package com.chq.ssmshop.service;

import java.util.Date;

import com.chq.ssmshop.entity.LocalAuth;
import com.chq.ssmshop.exception.LocalAuthOperationException;

public interface LocalAuthService {
	LocalAuth getLocalAuthByUsernameAndPassword(String username, String password);

	LocalAuth getLocalAuthByUserId(long userId);

	LocalAuth insertLocalAuth(LocalAuth localAuth) throws LocalAuthOperationException;

	LocalAuth updateLocalAuth(long userId, String username, String password, String newPassword, Date lastEditTime)
			throws LocalAuthOperationException;
}
