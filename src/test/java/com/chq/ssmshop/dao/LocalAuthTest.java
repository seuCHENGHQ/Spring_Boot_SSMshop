package com.chq.ssmshop.dao;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.chq.ssmshop.entity.LocalAuth;
import com.chq.ssmshop.entity.PersonInfo;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LocalAuthTest{

	@Autowired
	private LocalAuthDao localAuthDao;
	@Autowired
	private PersonInfoDao personInfoDao;

//	@Test
	public void insertLocalAuthTest() {
		PersonInfo personInfo = new PersonInfo();
		personInfo.setName("localAuthTest");
		personInfo.setCreateTime(new Date());
		personInfo.setLastEditTime(new Date());
		personInfo.setUserType(0);
		personInfo.setEnableStatus(0);
		int effectedRow = personInfoDao.insertPersonInfo(personInfo);
		assertEquals(1, effectedRow);

		LocalAuth localAuth = new LocalAuth();
		localAuth.setUsername("insertTest");
		localAuth.setPassword("insertTest");
		localAuth.setCreateTime(new Date());
		localAuth.setLastEditTime(new Date());
		localAuth.setPersonInfo(personInfo);
		effectedRow = localAuthDao.insertLocalAuth(localAuth);
		assertEquals(1, effectedRow);
	}

//	@Test
	public void queryLocalByUsernameAndPasswordTest() {
		LocalAuth localAuth = localAuthDao.queryLocalByUserNameAndPwd("insertTest", "insertTest");
		assertEquals(1, localAuth.getLocalAuthId().intValue());
	}

//	@Test
	public void queryLocalAuthByUserIdTest() {
		LocalAuth auth = localAuthDao.queryLocalByUserId(7);
		assertEquals(1, auth.getLocalAuthId().intValue());
	}

	@Test
	public void updateLocalAuthTest() {
		String newPassword = "newPassword";

		int effectedRow = localAuthDao.updateLocalAuth(7, "insertTest", "insertTest", newPassword, new Date());
		assertEquals(1, effectedRow);
		LocalAuth auth = localAuthDao.queryLocalByUserId(7);
		System.out.println(auth.getPassword());
	}
}
