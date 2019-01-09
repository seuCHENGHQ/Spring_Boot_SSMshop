$(function() {
	var url = '/SSMshop/frontend/listmainpageinfo';
	var getUserInfoUrl = '/SSMshop/account/getloginuser';
	var completePersonInfoUrl = '/SSMshop/personinfoadmin/modifypersoninfo?userId=';
	var userShopListUrl = '/SSMshop/shopadmin/shoplist?userId=';
	var modifyPasswordUrl = '/SSMshop/account/modifypassword?userId=';
	var logoutUrl = '/SSMshop/account/logout';

	var loginUrl = '/SSMshop/account/login';
	var registerUrl = '/SSMshop/account/register';

	$.getJSON(url, function(data) {
		if (data.success) {
			var headLineList = data.headLineList;
			var swiperHtml = '';
			headLineList.map(function(item, index) {
				swiperHtml += '' + '<div class="swiper-slide img-wrap">'
						+ '<img class="banner-img" src="'
						+ getServletContextPath() + item.lineImg + '" alt="'
						+ item.lineName + '">' + '</div>';
			});
			$('.swiper-wrapper').html(swiperHtml);
			$(".swiper-container").swiper({
				autoplay : 3000,
				autoplayDisableOnInteraction : false
			});
			var shopCategoryList = data.shopCategoryList;
			var categoryHtml = '';
			shopCategoryList.map(function(item, index) {
				categoryHtml += ''
						+ '<div class="col-50 shop-classify" data-category='
						+ item.shopCategoryId + '>' + '<div class="word">'
						+ '<p class="shop-title">' + item.shopCategoryName
						+ '</p>' + '<p class="shop-desc">'
						+ item.shopCategoryDesc + '</p>' + '</div>'
						+ '<div class="shop-classify-img-warp">'
						+ '<img class="shop-img" src="'
						+ getServletContextPath() + item.shopCategoryImg + '">'
						+ '</div>' + '</div>';
			});
			$('.row').html(categoryHtml);

		}
	});

	$.getJSON(getUserInfoUrl, function(data) {
		if (data.success) {
			login(data.userInfo);
		} else {
			nologin();
		}
	});

	$('#me').click(function() {
		$.openPanel('#panel-left-demo');
	});

	$('.row').on('click', '.shop-classify', function(e) {
		var shopCategoryId = e.currentTarget.dataset.category;
		var newUrl = '/SSMshop/frontend/shoplist?parentId=' + shopCategoryId;
		window.location.href = newUrl;
	});

	function login(userInfo) {
		var tempPanelHtml = '';
		tempPanelHtml += '<p>您好，' + userInfo.name + '</p>' + '<p><a href="'
				+ completePersonInfoUrl + userInfo.userId
				+ '" class="close-panel">' + '完善个人信息' + '</a></p>'
				+ '<p><a href="' + userShopListUrl + userInfo.userId
				+ '" class="close-panel">' + '我的店铺' + '</a></p>'
				+ '<p><a href="' + modifyPasswordUrl + userInfo.userId
				+ '" class="close-panel">' + '修改密码' + '</a></p>'
				+ '<p><a href="' + logoutUrl
				+ '" class="close-panel" id="logout">' + '登出' + '</a></p>';
		$('#panel-content').html(tempPanelHtml);
		// 绑定登出功能
		$('#logout').click(function() {
			$.ajax({
				url : logoutUrl,
				type : 'POST',
				success : function(data) {
					if (data.success) {
						$.toast('登出成功！');
						window.location.href = '/SSMshop/frontend/mainpage';
					} else {
						$.toast('登出失败！');
						return;
					}
				}
			});
		});
	}

	function nologin() {
		var tempPanelHtml = '';
		tempPanelHtml += '<p><a href="' + loginUrl + '" class="close-panel">'
				+ '登录' + '</a></p>' + '<p><a href="' + registerUrl
				+ '" class="close-panel">' + '注册' + '</a></p>';
		$('#panel-content').html(tempPanelHtml);
	}

});
