/**
 * 
 */

$(function() {
	var mainpageUrl = '/SSMshop/frontend/mainpage';
	var registerUrl = '/SSMshop/account/registerlocalauth';

	$('#submit').click(function() {
		var localAuth = {};
		localAuth.username = $('#username').val();
		localAuth.password = $('#password').val();
		localAuth.personInfo = {
			name : $('#name').val(),
			gender : $('#gender').val(),
			email : $('#email').val(),
			userType : 0,
			enableStatus : 1
		};

		var verifyCode = $('#j_captcha').val();
		if (!$('#j_captcha')) {
			$.toast('请输入验证码！');
			return;
		}

		$.ajax({
			type : "POST",
			url : registerUrl + '?verifyCode=' + verifyCode,
			data : JSON.stringify(localAuth),
			// 这里设置发送数据的类型
			contentType : 'application/json',
			// 这里设置返回值类型
			dataType : 'json',
			success : function(data) {
				if (data.success) {
					$.toast('注册成功！');
					window.location.href = mainpageUrl;
				} else {
					$.toast('注册失败！');
					$.toast(data.errMsg);
				}
			}

		});
	});
})