/**
 * 
 */

$(function() {
	/*
	 * 这里等到账户系统弄好了之后要删掉，从session中取对应的用户id
	 */
	// var userId = 1;
	var userId = getQueryString('userId');
	var getShopListByOwnerUrl = '/SSMshop/shopadmin/getshoplistbyowner/'
			+ userId;
	var getUserInfoUrl = '/SSMshop/personInfoAdmin/getPersonInfo/' + userId;
	var shopManagementUrl = '/SSMshop/shopadmin/shopmanagement?shopId=';

	getUserInfo();
	getShopList();

	// 获取对应的当前账号的用户信息
	function getUserInfo() {
		$.getJSON(getUserInfoUrl, function(data) {
			if (data.success) {
				$('#user-name').html(data.personInfo.name)
			} else {
				/*
				 * 如果这里没检测到用户信息，则直接跳转回登录页面
				 */
				$.toast(data.errMsg);
				return;
			}
		});
	}

	function getShopList() {
		tempShopListHtml = '';
		$.getJSON(getShopListByOwnerUrl, function(data) {
			if (data.success) {
				data.shopList.map(function(item, index) {
					var shopEnableStatus = '';
					switch (item.enableStatus) {
					case (0):
						shopEnableStatus += '等待审核'
						break;
					case (1):
						shopEnableStatus += '审核通过'
						break;
					case (2):
						shopEnableStatus += '店铺封禁'
						break;
					default:
						shopEnableStatus += '非法店铺代码'
						break;

					}
					tempShopListHtml += '<div class="row row-shop">'
							+ '<div class="col-40 shop-name">' + item.shopName
							+ '</div>' + '<div class="col-40">'
							+ shopEnableStatus + '</div>'
							+ '<div class="col-20">' + '<a href="'
							+ shopManagementUrl + item.shopId
							+ '" class="button">进入</a>' + '</div></div>';
				});
				$('#shop-list').html(tempShopListHtml);
			} else {
				$.toast(data.errMsg);
			}
		});
	}
})