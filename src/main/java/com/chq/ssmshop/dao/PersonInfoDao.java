package com.chq.ssmshop.dao;

import com.chq.ssmshop.entity.PersonInfo;

public interface PersonInfoDao {
	PersonInfo queryPersonInfoByUserId(long userId);

	int insertPersonInfo(PersonInfo personInfo);
}
