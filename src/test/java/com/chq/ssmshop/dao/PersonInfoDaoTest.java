package com.chq.ssmshop.dao;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.chq.ssmshop.entity.PersonInfo;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PersonInfoDaoTest{
	
	@Autowired
	PersonInfoDao personInfoDao;
	
	@Test
	public void queryPersonInfoByUserIdTest() {
		PersonInfo user = personInfoDao.queryPersonInfoByUserId(1);
		System.out.println(user.getName());
		assertEquals(1, user.getUserId().intValue());
	}
}
