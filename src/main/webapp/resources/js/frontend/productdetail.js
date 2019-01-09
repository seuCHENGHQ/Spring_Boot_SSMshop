/**
 * 
 */

$(function() {
	var productId = getQueryString('productId');
	var getProductDetailUrl = '/SSMshop/frontend/getproductdetail?productId='
			+ productId;

	$
			.getJSON(
					getProductDetailUrl,
					function(data) {
						if (data.success) {
							$("#product-img").attr(
									'src',
									getServletContextPath()
											+ data.product.imgAddr);
							$('#product-time').html(
									new Date(data.product.lastEditTime)
											.Format("yyyy-MM-dd"));
							$('#product-name').html(
									'商品名：' + data.product.productName);
							$('#product-desc').html(
									'商品描述：' + data.product.productDesc);
							$("#imgList").html(
									'<p>售价：' + data.product.promotionPrice
											+ '元</p>');
							$("#imgList").append(
									'<p>购买商品可获得积分：'
											+ data.product.promotionPrice
											+ '分</p>');

							var productImgList = '';
							data.product.productImgList
									.map(function(item, index) {
										productImgList += '<div class="content-padded"> <div class="row"> <div class="col-100"> <img src="'
												+ getServletContextPath()
												+ item.imgAddr
												+ '" class="height:100%"></div></div></div>';
									});
							$(".list-div").html(productImgList);
						} else {
							window.location.href = '/SSMshop/frontend/mainpage';
						}
					});
})