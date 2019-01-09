package com.chq.ssmshop.web.personinfoadmin;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chq.ssmshop.dto.PersonInfoExecution;
import com.chq.ssmshop.enums.PersonInfoExecutionEnum;
import com.chq.ssmshop.service.PersonInfoService;

@Controller
@RequestMapping("/personInfoAdmin")
public class PersonInfoController {
	
	@Autowired
	private PersonInfoService personInfoService;
	
	@RequestMapping(value="/getPersonInfo/{userId}",method=RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getPersonInfoByUserId(@PathVariable("userId")Integer userId) {
		Map<String, Object> modelMap = new HashMap<>();
		PersonInfoExecution pie = personInfoService.getPersonInfo(userId);
		if(pie.getState()==PersonInfoExecutionEnum.SUCCESS.getState()) {
			modelMap.put("success", true);
			modelMap.put("personInfo", pie.getUser());
		}else {
			modelMap.put("succes", false);
			modelMap.put("errMsg", pie.getStateInfo());
			return modelMap;
		}
		
		return modelMap;
	}
}
