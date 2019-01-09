package com.chq.ssmshop.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chq.ssmshop.dao.PersonInfoDao;
import com.chq.ssmshop.dto.PersonInfoExecution;
import com.chq.ssmshop.entity.PersonInfo;
import com.chq.ssmshop.enums.PersonInfoExecutionEnum;
import com.chq.ssmshop.service.PersonInfoService;

@Service
public class PersonInfoServiceImpl implements PersonInfoService {

	@Autowired
	private PersonInfoDao personInfoDao;

	@Override
	public PersonInfoExecution getPersonInfo(long userId) {
		// TODO Auto-generated method stub
		PersonInfo user = personInfoDao.queryPersonInfoByUserId(userId);
		PersonInfoExecution personInfoExecution = null;
		if (user != null) {
			personInfoExecution = new PersonInfoExecution(PersonInfoExecutionEnum.SUCCESS, user);
		} else {
			personInfoExecution = new PersonInfoExecution(PersonInfoExecutionEnum.NULL_USER);
		}
		return personInfoExecution;
	}

}
