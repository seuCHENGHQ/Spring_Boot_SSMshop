package com.chq.ssmshop.interceptor;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.chq.ssmshop.entity.Shop;

public class ShopPermissionInterceptor extends HandlerInterceptorAdapter {
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// 获取用户当前在操作的shop
		Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
		// 获取用户可以操作的所有店铺的列表
		List<Shop> shopList = (List<Shop>) request.getSession().getAttribute("shopList");
		if (currentShop != null && shopList != null) {
			// 便利可操作的所有店铺列表，来判断当前店铺是否在其中
			for (Shop shop : shopList) {
				// 如果当前店铺在可操作店铺的列表中，则说明可以操作当前店铺
				if (shop.getShopId() == currentShop.getShopId())
					return true;
			}
		}
		// 否则，不能操作当前店铺，终止controller的执行
		return false;
	}
}
