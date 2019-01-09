package com.chq.ssmshop.web.productcategoryadmin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.chq.ssmshop.service.ProductCategoryService;

@Controller
@RequestMapping("/productcategoryadmin")
public class ProductCategoryAdminController {
	@Autowired
	private ProductCategoryService productCategoryService;
	
	@RequestMapping(value="/productcategorymanagement" ,method=RequestMethod.GET)
	public String productCategoryManagement() {
		return "/productcategory/productcategorymanagement";
	}
}
