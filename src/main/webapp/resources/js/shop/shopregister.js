/**
 * 
 * 
 * 
 */

$(function() {
	// 这个url是提交店铺信息用的
	var registerShopUrl = '/SSMshop/shopadmin/registershop';

	// 这个url是获取店铺种类和地区信息的
	var initUrl = '/SSMshop/shopadmin/getinitinfo';

	getInitInfo();

	// 这个函数会去获取数据库中的所有店铺种类信息和店铺位置信息，并填入对应的select中
	function getInitInfo() {
		// alert(initUrl);
		var tempShopCategoryHtml = '';
		var tempAreaHtml = '';

		$.getJSON(initUrl, function(data) {
			data.shopCategoryList.map(function(item, index) {
				tempShopCategoryHtml += '<option value="' + item.shopCategoryId
						+ '">' + item.shopCategoryName + '</option>';

			});

			data.areaList.map(function(item, index) {
				tempAreaHtml += '<option value="' + item.areaId + '">'
						+ item.areaName + '</option>';
			});
			// 这个一定要放到getJSON方法里面，因为getJSON方法是个异步访问的方法，temp字符串还没拼接完，就会走到下面使用html()方法进行赋值的地方，这样页面显示出来就是空白
			$('#shop-category').html(tempShopCategoryHtml);
			$('#shop-area').html(tempAreaHtml);
		});
	}

	$('#submit').click(function() {
		var shop = {};
		shop.shopName = $('#shop-name').val();
		shop.area = {
			areaId : $('#shop-area').val()
		}
		shop.shopCategory = {
			shopCategoryId : $('#shop-category').val()
		}
		shop.shopAddr = $('#shop-addr').val();
		shop.phone = $('#phone').val();
		shop.priority = 0;
		shop.shopDesc = $('#shop-desc').val();
		var shopImg = $('#shop-img')[0].files[0];

		var formData = new FormData();
		formData.append('shopImg', shopImg);
		formData.append('shopStr', JSON.stringify(shop));

		var verifyCode = $('#j_captcha').val();
		if (!verifyCode) {
			$.toast('请输入验证码！');
			window.location.href='/SSMshop/shopadmin/listshop';
			return;
		}

		formData.append('verifyCode', verifyCode);

		$.ajax({
			type : "POST",
			url : registerShopUrl,
			data : formData,
			//不缓存此页面
			cache : false,
			contentType: false,
			processData: false,
			success : function(response) {
					if(response.success){
						$.toast('店铺注册成功！');
						//在店铺注册成功之后，跳转至别的页面
						window.location.href='/SSMshop/shopadmin/listshop';
					}else{
						$.toast('店铺注册失败！'+response.errMsg);
					}
					//每次点提交之后都更换验证码
					$('#captcha_img').click();
			}
		});

	});
});