/**
 * 
 */

$(function() {
	var loginUrl = '/SSMshop/account/loginverify';
	var mainpageUrl = '/SSMshop/frontend/mainpage';

	$('#submit').click(function() {
		localAuth = {};
		localAuth.username = $('#username').val();
		localAuth.password = $('#password').val();

		var verifyCode = $('#j_captcha').val();
		if (!verifyCode) {
			$.toast('请输入验证码！')
			return;
		}

		$.ajax({
			type : 'POST',
			url : loginUrl + '?verifyCode=' + verifyCode,
			data : JSON.stringify(localAuth),
			// 这里设置发送数据的类型
			contentType : 'application/json',
			// 这里设置返回值类型
			dataType : 'json',
			success : function(data) {
				if (data.success) {
					$.toast('登录成功！')
					// 这里将登录的用户信息记录到session中，并跳转回主页
					window.location.href = mainpageUrl;
				} else {
					$.toast('用户名或密码错误！');
				}
			}
		})
	})
})