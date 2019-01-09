package com.chq.ssmshop.service;

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
public class LocalAuthServiceTest {

	@Autowired
	private LocalAuthService localAuthService;

	@Test
	public void insertLocalAuthTest() {
		PersonInfo personInfo = new PersonInfo();
		personInfo.setName("localAuthTest");
		personInfo.setUserType(0);
		personInfo.setEnableStatus(0);

		LocalAuth localAuth = new LocalAuth();
		localAuth.setUsername("insertTest");
		localAuth.setPassword("insertTest");
		localAuth.setPersonInfo(personInfo);

		localAuth = localAuthService.insertLocalAuth(localAuth);
//		assertEquals(localAuth.getLocalAuthId().intValue(), 3);
	}

//	@Test
	public void updateLocalAuthTest() {
		PersonInfo personInfo = new PersonInfo();
		personInfo.setUserId(9);
//		LocalAuth localAuth = localAuthService.getLocalAuthByUserId(personInfo.getUserId());
		LocalAuth localAuth = localAuthService.updateLocalAuth(personInfo.getUserId(), "insertTest", "updateTest",
				"insertTest", new Date());
		System.out.println(localAuth.getPassword());
	}

//	@Test
	public void selectLocalAuthTest() {
		LocalAuth localAuth = localAuthService.getLocalAuthByUserId(8);
		System.out.println(localAuth.getPassword());
//		System.out.println("q2lse5be2bylq9b59550s555bebl55sss".equals(localAuth.getPassword()));
		assertEquals(2, localAuth.getLocalAuthId().intValue());

		localAuth = localAuthService.getLocalAuthByUsernameAndPassword("insertTest", "updateTest");
		System.out.println("updateTest".equals(localAuth.getPassword()));
		assertEquals(2, localAuth.getLocalAuthId().intValue());
	}

}
