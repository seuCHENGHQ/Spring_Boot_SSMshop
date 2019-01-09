package com.chq.ssmshop.web.fronted;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chq.ssmshop.entity.HeadLine;
import com.chq.ssmshop.entity.ShopCategory;
import com.chq.ssmshop.service.HeadLineService;
import com.chq.ssmshop.service.ShopCategoryService;

@Controller
@RequestMapping("/frontend")
public class MainPageController {

	@Autowired
	private ShopCategoryService shopCategoryService;

	@Autowired
	private HeadLineService headLineService;

	@RequestMapping(value = "/listmainpageinfo", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> listMainPageInfo() {
		Map<String, Object> modelMap = new HashMap<>();
		List<ShopCategory> shopCategoryList = new ArrayList<>();
		try {
			// 获取一级店铺类别列表（即parentId为null的ShopCategory）
			shopCategoryList = shopCategoryService.getShopCategoryList(null);
			modelMap.put("shopCategoryList", shopCategoryList);
		} catch (Exception e) {
			// TODO: handle exception
			modelMap.put("success", false);
			modelMap.put("errMsg", e.getMessage());
			return modelMap;
		}

		List<HeadLine> headLineList = new ArrayList<>();
		try {
			// 获取当前所有可用的头条列表
			HeadLine headLineCondition = new HeadLine();
			headLineCondition.setEnableStatus(1);
			headLineList = headLineService.getHeadLineList(headLineCondition);
			modelMap.put("headLineList", headLineList);
		} catch (Exception e) {
			// TODO: handle exception
			modelMap.put("success", false);
			modelMap.put("errMsg", e.getMessage());
			return modelMap;
		}
		modelMap.put("success", true);

		return modelMap;
	}

}
