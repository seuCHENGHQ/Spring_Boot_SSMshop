package com.chq.ssmshop.web.productadmin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/productadmin")
public class ProductAdminController {
	
	@RequestMapping(value="/productmanagement",method=RequestMethod.GET)
	public String productManagement() {
		return "/product/productmanagement";
	}
	
	@RequestMapping(value="/registerproduct",method=RequestMethod.GET)
	public String registerProduct() {
		return "/product/registerproduct";
	}
	
	@RequestMapping(value="/updateproduct",method=RequestMethod.GET)
	public String updateProduct() {
		return "/product/updateproduct";
	}
}
