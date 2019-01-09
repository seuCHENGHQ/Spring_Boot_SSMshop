$(function() {
	var loading = false;
	var maxItems = 20;
	var pageSize = 10;

	// 查询某店铺下的商品的url
	var listUrl = '/SSMshop/frontend/listproductbyshop';

	var pageNum = 1;
	var shopId = getQueryString('shopId');
	var productCategoryId = '';
	var productName = '';

	// 查询店铺信息和该店铺所拥有的商品类别信息的url
	var searchDivUrl = '/SSMshop/frontend/listshopdetailpageinfo?shopId='
			+ shopId;

	function getSearchDivData() {
		var url = searchDivUrl;
		$
				.getJSON(
						url,
						function(data) {
							if (data.success) {
								var shop = data.shop;
								$('#shop-cover-pic').attr('src',
										getServletContextPath() + shop.shopImg);
								$('#shop-update-time').html(
										new Date(shop.lastEditTime)
												.Format("yyyy-MM-dd"));
								$('#shop-name').html(shop.shopName);
								$('#shop-desc').html(shop.shopDesc);
								$('#shop-addr').html(shop.shopAddr);
								$('#shop-phone').html(shop.phone);

								var productCategoryList = data.productCategoryList;
								var html = '';
								productCategoryList
										.map(function(item, index) {
											html += '<a href="#" class="button" data-product-search-id='
													+ item.productCategoryId
													+ '>'
													+ item.productCategoryName
													+ '</a>';
										});
								$('#shopdetail-button-div').html(html);
							}
						});
	}
	getSearchDivData();

	function addItems(pageSize, pageIndex) {
		// 生成新条目的HTML
		// 在这里，不论我们怎么搜索，都肯定是搜索当前店铺下的商品，因此shopId是必须要有的
		var url = listUrl + '?' + 'pageIndex=' + pageIndex + '&pageSize='
				+ pageSize + '&productCategoryId=' + productCategoryId
				+ '&productName=' + productName + '&shopId=' + shopId;
		loading = true;
		$.getJSON(url, function(data) {
			if (data.success) {
				maxItems = data.count;
				var html = '';
				data.productList.map(function(item, index) {
					html += '' + '<div class="card" data-product-id='
							+ item.productId + '>'
							+ '<div class="card-header">' + item.productName
							+ '</div>' + '<div class="card-content">'
							+ '<div class="list-block media-list">' + '<ul>'
							+ '<li class="item-content">'
							+ '<div class="item-media">' + '<img src="'
							+ getServletContextPath() + item.imgAddr
							+ '" width="44">' + '</div>'
							+ '<div class="item-inner">'
							+ '<div class="item-subtitle">' + item.productDesc
							+ '</div>' + '</div>' + '</li>' + '</ul>'
							+ '</div>' + '</div>' + '<div class="card-footer">'
							+ '<p class="color-gray">'
							+ new Date(item.lastEditTime).Format("yyyy-MM-dd")
							+ '更新</p>' + '<span>点击查看</span>' + '</div>'
							+ '</div>';
				});
				$('.list-div').append(html);
				var total = $('.list-div .card').length;
				if (total >= maxItems) {
					// 加载完毕，则注销无限加载事件，以防不必要的加载
					// $.detachInfiniteScroll($('.infinite-scroll'));
					// 删除加载提示符
					// $('.infinite-scroll-preloader').hide();

					// 上边哪种写法是不对的
					// 因为如果我们加载完所有的店铺之后，更换了查询条件，那么新的查询虽然会从第一条开始显示，但是由于滚动栏的底部被删除，会再也无法触发加载事件，从而导致新的查询只能显示pageSize数量的店铺
					$('.infinite-scroll-preloader').hide();
				} else {
					$('.infinite-scroll-preloader').show();
				}
				pageNum += 1;
				loading = false;
				$.refreshScroller();
			}
		});
	}

	addItems(pageSize, pageNum);

	$(document).on('infinite', '.infinite-scroll-bottom', function() {
		if (loading)
			return;
		addItems(pageSize, pageNum);
	});

	$('#shopdetail-button-div').on(
			'click',
			'.button',
			function(e) {
				productCategoryId = e.target.dataset.productSearchId;
				if (productCategoryId) {
					if ($(e.target).hasClass('button-fill')) {
						$(e.target).removeClass('button-fill');
						productCategoryId = '';
					} else {
						$(e.target).addClass('button-fill').siblings()
								.removeClass('button-fill');
					}
					$('.list-div').empty();
					pageNum = 1;
					addItems(pageSize, pageNum);
				}
			});

	$('.list-div')
			.on(
					'click',
					'.card',
					function(e) {
						var productId = e.currentTarget.dataset.productId;
						window.location.href = '/SSMshop/frontend/productdetail?productId='
								+ productId;
					});

	$('#search').on('change', function(e) {
		productName = e.target.value;
		$('.list-div').empty();
		pageNum = 1;
		addItems(pageSize, pageNum);
	});

	$('#me').click(function() {
		$.openPanel('#panel-left-demo');
	});
	$.init();
});
