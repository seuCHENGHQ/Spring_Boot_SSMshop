package com.chq.ssmshop.service;

import com.chq.ssmshop.dto.PersonInfoExecution;

public interface PersonInfoService {
	PersonInfoExecution getPersonInfo(long userId);
}
