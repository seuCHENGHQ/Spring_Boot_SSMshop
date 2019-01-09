package com.chq.ssmshop.web.fronted;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 这个controller主要是起到路由的作用，把请求转发给合适的页面来进行加载和显示
 * 
 * @author chq
 *
 */
@Controller
@RequestMapping("/frontend")
public class FrontentController {

	@RequestMapping(value = "/mainpage", method = RequestMethod.GET)
	public String mainPage() {
		return "/frontend/index";
	}

	@RequestMapping(value = "/shoplist", method = RequestMethod.GET)
	public String shopList() {
		return "/frontend/shoplist";
	}

	@RequestMapping(value = "/shopdetail", method = RequestMethod.GET)
	public String shopDetail() {
		return "/frontend/shopdetail";
	}

	@RequestMapping(value = "/productdetail")
	public String productDetail() {
		return "/frontend/productdetail";
	}
}
