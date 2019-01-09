package com.chq.ssmshop.web.superadmin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chq.ssmshop.entity.Area;
import com.chq.ssmshop.service.AreaService;

@Controller
@RequestMapping(value="/superadmin")
public class AreaController {
	@Autowired
	private AreaService areaService;
	
	@RequestMapping(value="/listarea", method=RequestMethod.GET)
	@ResponseBody
	public Map<String, String> listAllArea(){
		Map<String, String> modelAndView = new HashMap<>();
		List<Area> result = areaService.queryArea();
		for(Area area : result) {
			modelAndView.put(area.getAreaId()+"", area.getAreaName());
		}
		return modelAndView;
	}
}
