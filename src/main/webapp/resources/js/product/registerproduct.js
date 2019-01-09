/**
 * 
 */

$(function() {
	var shopId = getQueryString("shopId");
	// var shopId = 9
	var registerProductUrl = '/SSMshop/productoperation/registerproduct';
	var getProductCategoryUrl = '/SSMshop/productcategoryadmin/getproductcategorylist?shopId='
			+ shopId;

	getProductCategory();

	function getProductCategory() {
		$.getJSON(getProductCategoryUrl, function(data) {
			if (data.success) {
				var tempProductCategoryHtml = '';
				data.productCategoryList.map(function(item, index) {
					tempProductCategoryHtml += '<option value="'
							+ item.productCategoryId + '">'
							+ item.productCategoryName + '</option>'
				});
				$('#product-category').html(tempProductCategoryHtml);
			} else {
				$.toast(data.errMsg);
			}
		});
	}

	$('.detail-img-div').on('change', '.detail-img:last-child', function() {
		// 最多只能提交6个商品详情图
		if ($('.detail-img').length < 6) {
			// 如果商品详情图少于6张，每次添加完一张图片后，就再增加一个上传图片的按钮
			$('#detail-img').append('<input type="file" class="detail-img">');
		}
	});

	$('#submit').click(
			function() {
				var product = {};
				product.productName = $('#product-name').val();
				product.productDesc = $('#product-desc').val();
				product.priority = $('#priority').val();
				product.normalPrice = $('#normal-price').val();
				product.promotionPrice = $('#promotion-price').val();
				product.productCategory = {
					productCategoryId : $('#product-category').val()
				};
				product.shop = {
					shopId : shopId
				}

				var thumbnail = $('#product-thumbnail')[0].files[0];
				// 在浏览器的console上输出thumbnail
				console.log(thumbnail);

				// 把表单信息封装在formdata中
				/*
				 * FormData对象用以将数据编译成键值对，以便用XMLHttpRequest来发送数据。
				 * 你可以自己创建一个FormData对象，然后调用它的append()方法来添加字段
				 */
				var formData = new FormData();
				formData.append('product-thumbnail', thumbnail);
				$('.detail-img').map(
						function(index, item) {
							// 因为当上传图片不足6个时，会有一个空的上传控件，因此要验证文件的大小要大于0
							if ($('.detail-img')[index].files.length > 0) {
								// 给每个上传的商品详情图定义一个不同的键，index从0开始
								formData.append('productImg' + index,
										$('.detail-img')[index].files[0]);
							}
						});
				// 把product的JSON对象转换为字符串，再添加到formdata中，一起传输给后台
				formData.append('productStr', JSON.stringify(product));
				var verifyCodeActual = $('#j_captcha').val();
				// 这里验证码不能为空，为空直接返回
				if (!verifyCodeActual) {
					$.toast('请输入验证码！');
					return;
				}
				formData.append("verifyCode", verifyCodeActual);
				$.ajax({
					url : registerProductUrl,
					type : 'POST',
					data : formData,
					contentType : false,
					processData : false,
					cache : false,
					success : function(data) {
						if (data.success) {
							$.toast('提交成功！');
							// 提交成功，验证码要刷新一下
							$('#captcha_img').click();
						} else {
							$.toast('提交失败！' + data.errMsg);
							// 提交失败，验证码也要刷新一下
							$('#captcha_img').click();
						}
					}
				});
			});

});