package com.chq.ssmshop.util;

import javax.servlet.http.HttpServletRequest;

public class CodeUtil {
	public static boolean checkVerifyCode(HttpServletRequest request) {
		String trueVerifyCode = (String) request.getSession().getAttribute(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);
		String verifyCode = HttpServletRequestUtil.getString(request, "verifyCode");
		if(trueVerifyCode!=null&&trueVerifyCode.equals(verifyCode)) {
			return true;
		}
		return false;
	}
}
