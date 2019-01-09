/**
 * 该js和更新店铺信息进行绑定
 */

$(function() {

	var shopId = getQueryString("shopId");

	var getInitInfoUrl = '/SSMshop/shopadmin/getinitinfo';

	// 向该地址传递表单信息
	var updateShopUrl = '/SSMshop/shopadmin/updateshop';

	var getShopInfoUrl = '/SSMshop/shopadmin/getshopbyid/' + shopId;

	getInitInfo();
	// 因为getJSON是异步的，因此不能在这里直接调用getShopInfo()，因为执行getShopInfo()的时候可能下拉选项的几个option还没加载出来
	// getShopInfo();

	function getInitInfo() {
		var tempAreaHtml = '';
		var tempShopCategoryHtml = '';

		$.getJSON(getInitInfoUrl, function(data) {
			data.shopCategoryList.map(function(item, index) {
				tempShopCategoryHtml += '<option value="' + item.shopCategoryId
						+ '">' + item.shopCategoryName + '</option>'
			});

			data.areaList.map(function(item, index) {
				tempAreaHtml += '<option value="' + item.areaId + '">'
						+ item.areaName + '</option>'
			});

			$('#shop-category').html(tempShopCategoryHtml);
			$('#shop-area').html(tempAreaHtml);
			getShopInfo();
		});
	}

	function getShopInfo() {
		$
				.getJSON(
						getShopInfoUrl,
						function(data) {
							if (data.success) {
								// 让select控件选中店铺注册在数据库中的信息
								var selectedShopCategoryId = data.shop.shopCategory.shopCategoryId;
								var selectedAreaId = data.shop.area.areaId;
								$('#shop-category')
										.find(
												'option[value="'
														+ selectedShopCategoryId
														+ '"]').attr(
												'selected', 'selected');
								$('#shop-area').find(
										'option[value="' + selectedAreaId
												+ '"]').attr('selected',
										'selected');

								$('#shop-name').val(data.shop.shopName);
								$('#shop-addr').val(data.shop.shopAddr);
								$('#phone').val(data.shop.phone);
								$('#shop-desc').val(data.shop.shopDesc);
							} else {
								$.toast(data.errMsg);
								return;
							}
						});
	}

	$('#submit').click(function() {
		var shop = {};
		/*
		 * 这里将来要替换掉，从session中获取shopId
		 */
		shop.shopId = shopId;
		shop.shopName = $('#shop-name').val();
		shop.shopAddr = $('#shop-addr').val();
		shop.phone = $('#phone').val();
		shop.shopDesc = $('#shop-desc').val();

		shop.shopCategory = {
			shopCategoryId : $('#shop-category').val()
		}
		shop.area = {
			areaId : $('#shop-area').val()
		}

		var verifyCode = $('#j_captcha').val();
		if (!verifyCode) {
			$.toast('请输入验证码！');
			$('#captcha_img').click();
			return;
		}
		var shopImg = $('#shop-img')[0].files[0];

		var formData = new FormData();
		formData.append('shopImg', shopImg);
		formData.append('shopStr', JSON.stringify(shop));
		formData.append('verifyCode', verifyCode);

		$.ajax({
			type : 'POST',
			url : updateShopUrl,
			data : formData,
			cache : false,
			contentType : false,
			processData : false,
			success : function(response) {
				if (response.success) {
					$.toast('更改店铺成功！');
					$('#return').click();
				} else {
					$.toast('店铺信息更改失败！' + response.errMsg);
				}
				$('#captcha_img').click();
			}
		});
	});
});