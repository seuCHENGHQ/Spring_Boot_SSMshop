package com.chq.ssmshop.web.fronted;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chq.ssmshop.dto.ProductCategoryExecution;
import com.chq.ssmshop.dto.ProductExecution;
import com.chq.ssmshop.dto.ShopExecution;
import com.chq.ssmshop.entity.Product;
import com.chq.ssmshop.entity.ProductCategory;
import com.chq.ssmshop.entity.Shop;
import com.chq.ssmshop.enums.ProductCategoryExecutionEnum;
import com.chq.ssmshop.enums.ShopExecutionEnum;
import com.chq.ssmshop.service.ProductCategoryService;
import com.chq.ssmshop.service.ProductService;
import com.chq.ssmshop.service.ShopService;
import com.chq.ssmshop.util.HttpServletRequestUtil;

@Controller
@RequestMapping("/frontend")
public class ShopDetailController {
	@Autowired
	private ShopService shopService;
	@Autowired
	private ProductService productService;
	@Autowired
	private ProductCategoryService productCategoryService;

	@RequestMapping(value = "/listshopdetailpageinfo", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> shopDetailPageInfo(HttpServletRequest request, @RequestParam("shopId") Integer shopId) {
		Map<String, Object> modelMap = new HashMap<>();
		ShopExecution se = null;
		ProductCategoryExecution pce = null;
		Shop shop = null;
		List<ProductCategory> productCategoryList = null;
		if (shopId != null) {
			se = shopService.queryShop(shopId);
			if (se.getState() == ShopExecutionEnum.SUCCESS.getState()) {
				shop = se.getShop();
			} else {
				modelMap.put("success", false);
				modelMap.put("errMsg", se.getStateInfo());
				return modelMap;
			}
			pce = productCategoryService.getProductCategoryListByShopId(shopId);
			if (pce.getState() == ProductCategoryExecutionEnum.SUCCESS.getState()) {
				productCategoryList = pce.getProductCategoryList();
			} else {
				modelMap.put("success", false);
				modelMap.put("errMsg", pce.getStateInfo());
				return modelMap;
			}
			modelMap.put("success", true);
			modelMap.put("shop", shop);
			modelMap.put("productCategoryList", productCategoryList);
		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "empty shopId");
		}

		return modelMap;
	}

	@RequestMapping(value = "/listproductbyshop", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> listProductByShop(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<>();
		int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
		int pageSize = HttpServletRequestUtil.getInt(request, "pageSize");
		long shopId = HttpServletRequestUtil.getLong(request, "shopId");
		if ((pageIndex > -1) && (pageSize > -1) && (shopId > -1)) {
			long productCategoryId = HttpServletRequestUtil.getLong(request, "productCategoryId");
			// productName不为null，说明要进行模糊查询
			String productName = HttpServletRequestUtil.getString(request, "productName");
			Product productCondition = compactProductCondition4Search(shopId, productCategoryId, productName);
			ProductExecution pe = productService.getProductList(productCondition, pageIndex, pageSize);
			modelMap.put("productList", pe.getProductList());
			modelMap.put("count", pe.getCount());
			modelMap.put("success", true);
		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "empty pageSize or pageIndex or shopId");
		}
		return modelMap;
	}

	private Product compactProductCondition4Search(long shopId, long productCategoryId, String productName) {
		Product productCondition = new Product();
		Shop shop = new Shop();
		shop.setShopId(Long.valueOf(shopId).intValue());
		productCondition.setShop(shop);
		if (productCategoryId != -1L) {
			ProductCategory productCategory = new ProductCategory();
			productCategory.setProductCategoryId(Long.valueOf(productCategoryId).intValue());
			productCondition.setProductCategory(productCategory);
		}
		if (productName != null) {
			productCondition.setProductName(productName);
		}
		// 前端进行访问时，只允许访问到上架的商品，为而不允许访问到未上架的商品
		productCondition.setEnableStatus(1);
		return productCondition;
	}
}
