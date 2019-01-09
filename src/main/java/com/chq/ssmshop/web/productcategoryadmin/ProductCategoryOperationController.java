package com.chq.ssmshop.web.productcategoryadmin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chq.ssmshop.dto.ProductCategoryExecution;
import com.chq.ssmshop.entity.ProductCategory;
import com.chq.ssmshop.enums.ProductCategoryExecutionEnum;
import com.chq.ssmshop.service.ProductCategoryService;

@Controller
@RequestMapping("/productcategoryadmin")
public class ProductCategoryOperationController {
	@Autowired
	private ProductCategoryService productCategoryService;

	@RequestMapping(value = "/getproductcategorylist", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getProductCategoryListByShopId(@RequestParam long shopId) {
		Map<String, Object> modelMap = new HashMap<>();
		ProductCategoryExecution pce = productCategoryService.getProductCategoryListByShopId(shopId);
		if (pce.getState() == ProductCategoryExecutionEnum.SUCCESS.getState()) {
			modelMap.put("success", true);
			modelMap.put("productCategoryList", pce.getProductCategoryList());
		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", pce.getStateInfo());
		}

		return modelMap;
	}

	@RequestMapping(value = "/removeproductcategory", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> removeProductCategoryByProductCategoryId(HttpServletRequest request,
			@RequestBody ProductCategory pc) {
		Map<String, Object> modelMap = new HashMap<>();

		ProductCategoryExecution pce = productCategoryService.deleteProductCategoryById(pc.getProductCategoryId());
		if (pce.getState() == ProductCategoryExecutionEnum.SUCCESS.getState()) {
			modelMap.put("success", true);

		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", pce.getStateInfo());
		}

		return modelMap;
	}

	@RequestMapping(value = "/addproductcategorylist", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addProductCategoryList(@RequestBody List<ProductCategory> productCategoryList) {
		Map<String, Object> modelMap = new HashMap<>();
		ProductCategoryExecution pce = productCategoryService.batchInsertProductCategory(productCategoryList);
		if (pce != null && pce.getState() == ProductCategoryExecutionEnum.SUCCESS.getState()) {
			modelMap.put("success", true);
		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", pce != null?pce.getStateInfo():"批量插入productCategory时出现异常");
		}

		return modelMap;
	}
}
