package com.chq.ssmshop.web.fronted;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chq.ssmshop.dto.ShopExecution;
import com.chq.ssmshop.entity.Area;
import com.chq.ssmshop.entity.Shop;
import com.chq.ssmshop.entity.ShopCategory;
import com.chq.ssmshop.service.AreaService;
import com.chq.ssmshop.service.ShopCategoryService;
import com.chq.ssmshop.service.ShopService;
import com.chq.ssmshop.util.HttpServletRequestUtil;

@Controller
@RequestMapping("/frontend")
public class ShopListController {
	@Autowired
	private ShopCategoryService shopCategoryService;
	@Autowired
	private ShopService shopService;
	@Autowired
	private AreaService areaService;

	@RequestMapping(value = "/listinfo", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> listShopPageInfo(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<>();
		long parentId = HttpServletRequestUtil.getLong(request, "parentId");
		List<ShopCategory> shopCategoryList = null;
		if (parentId != -1) {
			// 如果parentId存在，则说明要查询该parentId下的所有二级店铺类别
			try {
				ShopCategory shopCategoryCodition = new ShopCategory();
				ShopCategory parent = new ShopCategory();
				parent.setShopCategoryId(Long.valueOf(parentId).intValue());
				shopCategoryCodition.setParent(parent);
				shopCategoryList = shopCategoryService.getShopCategoryList(shopCategoryCodition);
			} catch (Exception e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.getMessage());
				return modelMap;
			}
		} else {
			// 如果parentId不存在，就查询所有一级shopcategory
			try {
				shopCategoryList = shopCategoryService.getShopCategoryList(null);
			} catch (Exception e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.getMessage());
				return modelMap;
			}
		}

		List<Area> areaList = null;
		try {
			areaList = areaService.queryArea();
		} catch (Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.getMessage());
			return modelMap;
		}

		modelMap.put("success", true);
		modelMap.put("shopCategoryList", shopCategoryList);
		modelMap.put("areaList", areaList);
		return modelMap;
	}

	@RequestMapping(value = "/listshopspageinfo", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> listShops(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
		int pageSize = HttpServletRequestUtil.getInt(request, "pageSize");
		if ((pageIndex > -1) && (pageSize > -1)) {
			// 这里获取查询条件
			long parentId = HttpServletRequestUtil.getLong(request, "parentId");
			long shopCategoryId = HttpServletRequestUtil.getLong(request, "shopCategoryId");
			long areaId = HttpServletRequestUtil.getLong(request, "areaId");
			String shopName = HttpServletRequestUtil.getString(request, "shopName");
			// 将查询条件组装为一个shopConditoin对象
			Shop shopCondition = compactShopCondition4Search(parentId, shopCategoryId, areaId, shopName);
			ShopExecution se = shopService.getShopList(shopCondition, pageIndex, pageSize);
			modelMap.put("shopList", se.getShopList());
			modelMap.put("count", se.getCount());
			modelMap.put("success", true);
		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "empty pageSize or pageIndex");
		}

		return modelMap;
	}

	private Shop compactShopCondition4Search(long parentId, long shopCategoryId, long areaId, String shopName) {
		Shop shopCondition = new Shop();
		if (shopCategoryId != -1L) {
			ShopCategory shopCategory = new ShopCategory();
			shopCategory.setShopCategoryId(Long.valueOf(shopCategoryId).intValue());
			shopCondition.setShopCategory(shopCategory);
		}
		if (parentId != -1L) {
			ShopCategory parentCategory = new ShopCategory();
			parentCategory.setShopCategoryId(Long.valueOf(parentId).intValue());
			if (shopCondition.getShopCategory() == null) {
				ShopCategory shopCategory = new ShopCategory();
				shopCondition.setShopCategory(shopCategory);
			}
			shopCondition.getShopCategory().setParent(parentCategory);
		}
		if (areaId != -1L) {
			Area area = new Area();
			area.setAreaId(Long.valueOf(areaId).intValue());
			shopCondition.setArea(area);
		}

		if (shopName != null) {
			shopCondition.setShopName(shopName);
		}
		// 前端只能查询到审核通过的店铺，未通过的是看不到的
		shopCondition.setEnableStatus(1);
		return shopCondition;
	}
}
