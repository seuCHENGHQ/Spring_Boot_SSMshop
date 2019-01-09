package com.chq.ssmshop.web.fronted;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chq.ssmshop.dto.ProductExecution;
import com.chq.ssmshop.enums.ProductExecutionEnum;
import com.chq.ssmshop.service.ProductService;

@Controller
@RequestMapping(value = "/frontend")
public class ProductDetailController {
	@Autowired
	private ProductService productService;

	@RequestMapping(value = "/getproductdetail")
	@ResponseBody
	public Map<String, Object> getProductDetail(@RequestParam("productId") Integer productId) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		if (productId == null) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "productId不能为空");
			return modelMap;
		}
		ProductExecution pe = productService.selectProductById(productId);
		if (pe.getState() == ProductExecutionEnum.SUCCESS.getState()) {
			modelMap.put("success", true);
			modelMap.put("product", pe.getProduct());
		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", pe.getStateInfo());
		}
		return modelMap;

	}
}
