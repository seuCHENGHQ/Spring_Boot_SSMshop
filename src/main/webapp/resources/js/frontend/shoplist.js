$(function() {
	var loading = false;
	// 这个999是暂时的，后台会传回来当前查询条件下的数据数量，从而覆盖这个
	var maxItems = 999;
	var pageSize = 3;
	var listUrl = '/SSMshop/frontend/listshopspageinfo';
	var searchDivUrl = '/SSMshop/frontend/listinfo';
	var pageNum = 1;
	var parentId = getQueryString('parentId');
	var areaId = '';
	var shopCategoryId = '';
	var shopName = '';

	function getSearchDivData() {
		var url = searchDivUrl + '?' + 'parentId=' + parentId;
		$
				.getJSON(
						url,
						function(data) {
							if (data.success) {
								var shopCategoryList = data.shopCategoryList;
								var html = '';
								html += '<a href="#" class="button" data-category-id=""> 全部类别  </a>';
								shopCategoryList
										.map(function(item, index) {
											html += '<a href="#" class="button" data-category-id='
													+ item.shopCategoryId
													+ '>'
													+ item.shopCategoryName
													+ '</a>';
										});
								$('#shoplist-search-div').html(html);
								var selectOptions = '<option value="">全部街道</option>';
								var areaList = data.areaList;
								areaList.map(function(item, index) {
									selectOptions += '<option value="'
											+ item.areaId + '">'
											+ item.areaName + '</option>';
								});
								$('#area-search').html(selectOptions);
							}
						});
	}
	getSearchDivData();

	function addItems(pageSize, pageIndex) {
		// 生成新条目的HTML
		var url = listUrl + '?' + 'pageIndex=' + pageIndex + '&pageSize='
				+ pageSize + '&parentId=' + parentId + '&areaId=' + areaId
				+ '&shopCategoryId=' + shopCategoryId + '&shopName=' + shopName;
		// loading置位true表明有一次访问数据库正在进行，请勿重复访问数据库以免加载到重复的数据
		loading = true;
		$.getJSON(url, function(data) {
			if (data.success) {
				maxItems = data.count;
				var html = '';
				data.shopList.map(function(item, index) {
					html += '' + '<div class="card" data-shop-id="'
							+ item.shopId + '">' + '<div class="card-header">'
							+ item.shopName + '</div>'
							+ '<div class="card-content">'
							+ '<div class="list-block media-list">' + '<ul>'
							+ '<li class="item-content">'
							+ '<div class="item-media">' + '<img src="'
							+ getServletContextPath() + item.shopImg
							+ '" width="44">' + '</div>'
							+ '<div class="item-inner">'
							+ '<div class="item-subtitle">' + item.shopDesc
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
					// 如果已经显示到了所有记录的最后一条，那么就不会再触发加载事件
					$('.infinite-scroll-preloader').hide();
				} else {
					// 如果没有显示到了所有记录的最后一条，那么就再次触发加载事件
					$('.infinite-scroll-preloader').show();
				}
				pageNum += 1;
				loading = false;
				$.refreshScroller();
			}
		});
	}
	// 预先加载10条
	addItems(pageSize, pageNum);

	// 当底部的滚动事件被触发时要做的操作
	$(document).on('infinite', '.infinite-scroll-bottom', function() {
		// 这里的loading标志位是为了防止重复访问数据库而加载到重复的数据
		if (loading)
			return;
		addItems(pageSize, pageNum);
	});

	// 当卡片shop-list下的卡片元素被点击，当前页面就跳转到指定的店铺详情页去
	$('.shop-list').on('click', '.card', function(e) {
		var shopId = e.currentTarget.dataset.shopId;
		window.location.href = '/SSMshop/frontend/shopdetail?shopId=' + shopId;
	});

	// 选择新的店铺类别之后，重置页码，清空原先已经加载的店铺列表，按照新的店铺类别去查询
	$('#shoplist-search-div').on(
			'click',
			'.button',
			function(e) {
				if (parentId) {
					// 如果传递过来的是一个父类下的子类
					shopCategoryId = e.target.dataset.categoryId;
					if ($(e.target).hasClass('button-fill')) {
						// 如果点击了一个已经被点击的按钮，那么就移除它被点击的样式，并重新加载shoplist
						$(e.target).removeClass('button-fill');
						shopCategoryId = '';
					} else {
						// 如果点击了一个未被点击过的按钮，首先让该按钮的样式变为被点击的样子，然后便利该按钮的同级其他按钮，取消其他按钮的被点击样式
						$(e.target).addClass('button-fill').siblings()
								.removeClass('button-fill');
					}
					/*
					 * 清空已加载的店铺列表的操作发生在这里
					 */
					$('.list-div').empty();
					pageNum = 1;
					addItems(pageSize, pageNum);
				} else {
					// 如果传递过来的父类为空，则按照父类查询
					parentId = e.target.dataset.categoryId;
					if ($(e.target).hasClass('button-fill')) {
						$(e.target).removeClass('button-fill');
						parentId = '';
					} else {
						$(e.target).addClass('button-fill').siblings()
								.removeClass('button-fill');
					}
					/*
					 * 清空已加载的店铺列表的操作发生在这里
					 */
					$('.list-div').empty();
					pageNum = 1;
					addItems(pageSize, pageNum);
					parentId = '';
				}

			});

	// 每次单击搜索栏，不论有没有向其中写入数据，都会刷新当前页面，从第一页开始重新显示
	$('#search').on('input', function(e) {
		shopName = e.target.value;
		$('.list-div').empty();
		pageNum = 1;
		addItems(pageSize, pageNum);
	});

	// 区域的下拉选项框,当select选中的值发生改变时，就触发该事件
	$('#area-search').on('change', function() {
		areaId = $('#area-search').val();
		$('.list-div').empty();
		pageNum = 1;
		addItems(pageSize, pageNum);
	});

	// 侧边栏
	$('#me').click(function() {
		$.openPanel('#panel-left-demo');
	});

	// 初始化页面
	$.init();
});
