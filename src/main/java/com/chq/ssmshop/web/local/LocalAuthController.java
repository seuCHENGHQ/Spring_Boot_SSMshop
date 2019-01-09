package com.chq.ssmshop.web.local;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chq.ssmshop.entity.LocalAuth;
import com.chq.ssmshop.service.LocalAuthService;
import com.chq.ssmshop.util.CodeUtil;
import com.chq.ssmshop.util.HttpServletRequestUtil;

@Controller
@RequestMapping("/account")
public class LocalAuthController {
	@Autowired
	private LocalAuthService localAuthService;

	@RequestMapping(value = "/registerlocalauth", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> register(HttpServletRequest request, @RequestBody LocalAuth localAuth) {
		Map<String, Object> modelMap = new HashMap<>();
		if (!CodeUtil.checkVerifyCode(request)) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "验证码输入错误");
			return modelMap;
		}

		if (localAuth == null) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "将JSON转换为对象时发生错误");
			return modelMap;
		}
		localAuth = localAuthService.insertLocalAuth(localAuth);

		modelMap.put("success", true);
		modelMap.put("localAuth", localAuth);
		return modelMap;
	}

	@RequestMapping(value = "/loginverify", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> login(HttpServletRequest request, @RequestBody LocalAuth localAuth) {
		Map<String, Object> modelMap = new HashMap<>();
		if (!CodeUtil.checkVerifyCode(request)) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "验证码输入错误");
			return modelMap;
		}

		if (localAuth == null || localAuth.getUsername() == null || localAuth.getPassword() == null) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "用户名或密码");
			return modelMap;
		}

		try {
			localAuth = localAuthService.getLocalAuthByUsernameAndPassword(localAuth.getUsername(),
					localAuth.getPassword());
			if (localAuth == null) {
				modelMap.put("success", false);
				modelMap.put("errMsg", "用户名或密码错误");
				return modelMap;
			}
			request.getSession().setAttribute("user", localAuth.getPersonInfo());
		} catch (Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.getMessage());
			e.printStackTrace();
			return modelMap;
		}

		modelMap.put("success", true);
		modelMap.put("userInfo", localAuth.getPersonInfo());
		return modelMap;
	}

	public Map<String, Object> modifyLocalAuth(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<>();
		if (!CodeUtil.checkVerifyCode(request)) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "验证码输入错误");
			return modelMap;
		}

		String username = HttpServletRequestUtil.getString(request, "username");
		if (username == null || username.length() == 0) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "用户名和密码不能为空");
			return modelMap;
		}
		String password = HttpServletRequestUtil.getString(request, "password");
		if (password == null || password.length() == 0) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "用户名和密码不能为空");
			return modelMap;
		}
		String newPassword = HttpServletRequestUtil.getString(request, "newPassword");
		if (newPassword == null || newPassword.length() == 0) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "用户名和密码不能为空");
			return modelMap;
		}
		String userIdStr = HttpServletRequestUtil.getString(request, "userId");
		if (newPassword == null || newPassword.length() == 0) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "请先登录！");
			return modelMap;
		}

		LocalAuth localAuth = null;
		try {
			localAuth = localAuthService.updateLocalAuth(Long.parseLong(userIdStr), username, password, newPassword,
					new Date());
			if (localAuth == null) {
				modelMap.put("success", false);
				modelMap.put("errMsg", "用户名或密码错误！");
				return modelMap;
			}
		} catch (Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.getMessage());
			e.printStackTrace();
			return modelMap;
		}

		modelMap.put("success", true);
		modelMap.put("localAuth", localAuth);
		return modelMap;
	}

	@RequestMapping(value = "/getloginuser", method = RequestMethod.GET)
	@ResponseBody
	/**
	 * 获取当前登录用户的信息
	 * 
	 * @param request
	 * @return
	 */
	public Map<String, Object> getUserInfo(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<>();
		Object localAuth = request.getSession().getAttribute("user");
		if (localAuth == null) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "请先登录");
			return modelMap;
		}
		modelMap.put("success", true);
		modelMap.put("userInfo", localAuth);
		return modelMap;
	}

	@RequestMapping(value = "/logout", method = RequestMethod.POST)
	@ResponseBody
	/**
	 * 登出
	 * 
	 * @param request
	 * @return
	 */
	public Map<String, Object> logout(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<>();
		request.getSession().setAttribute("user", null);
		modelMap.put("success", true);
		return modelMap;
	}
}
