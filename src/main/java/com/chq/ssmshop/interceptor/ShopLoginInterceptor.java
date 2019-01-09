package com.chq.ssmshop.interceptor;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.chq.ssmshop.entity.PersonInfo;

public class ShopLoginInterceptor extends HandlerInterceptorAdapter {
	/*
	 * HandlerInterceptorAdapter实现了AsyncHandlerInterceptor接口，
	 * 而AsyncHandlerInterceptor又实现了HandlerInterceptor接口
	 * 因此我们直接继承HandlerInterceptorAdapter就可以编写自己的拦截器了
	 */

	/**
	 * 主要做事前拦截，在用户发生操作之前进行拦截，改写preHandle之中的逻辑，就可以进行拦截了
	 * 因为返回的是boolean型的变量，因此可以根据每个拦截器的返回值来确定是否继续向下执行
	 * 
	 * request是dispatcher servlet传来的请求，response是处理完成之后的返回值，handler是controller
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		Object personInfo = request.getSession().getAttribute("user");
		if (personInfo != null) {
			PersonInfo user = (PersonInfo) personInfo;
			/*
			 * 首先做空值判断，如果不为空，并且账号状态是可用的，那么就返回true，说明满足权限的要求
			 */
			if (user != null && user.getUserId() != null && user.getUserId() > 0 && user.getEnableStatus() == 1) {
				return true;
			}
		}
		// 如果不满足登录验证，则直接跳转到账号登录页面，方式是
		PrintWriter writer = response.getWriter();
		writer.println("<html>");
		writer.println("<script>");
		writer.println("window.location.href='/SSMshop/account/login'");
		writer.println("</script>");
		writer.println("</html>");

		return false;
	}
}
