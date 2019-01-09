package com.chq.ssmshop.web.shopadmin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/shopadmin")
public class ShopAdminController {

	@RequestMapping(value = "/shopmanagement")
	public String shopManagement() {
		/*
		 * 在跳转的之前，要先检测session中有没有userId 没有userId 就跳转回登录页面 有userId，就跳转到店铺管理页面
		 */
		return "/shop/shopmanagement";
	}

	@RequestMapping(value = "register", method = RequestMethod.GET)
	public String register() {
		/*
		 * 其实将来这里可以加一个拦截器，检测用户有没有登录 如果没登录的话，session中一定没有用户userId，因此这里直接跳转回登录页面
		 */
		return "/shop/shopregister";
	}

	@RequestMapping(value = "/update", method = RequestMethod.GET)
	public String update() {
		
		return "/shop/shopupdate";
	}
	
	@RequestMapping(value="/shoplist", method=RequestMethod.GET)
	public String shopList() {
		return "/shop/shoplist";
	}
}
