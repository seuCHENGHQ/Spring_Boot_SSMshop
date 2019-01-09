/**
 * 
 */

$(function(){
	var shopId = getQueryString("shopId");
	var getShopInfoUrl = '/SSMshop/shopadmin/getshopbyid/'+shopId;
	var updateShopInfoUrl = '/SSMshop/shopadmin/update?shopId='+shopId;
	var productCategoryManagementUrl = '/SSMshop/productcategoryadmin/productcategorymanagement?shopId='+shopId;
	var productManagementUrl = '/SSMshop/productadmin/productmanagement?shopId='+shopId;
	
	getInitInfo();
	
	function getInitInfo(){
		$.getJSON(getShopInfoUrl,function(data){
			$('#shop-name').html(data.shop.shopName);
		});
		$('#shopInfo').attr('href',updateShopInfoUrl);
		$('#productCategoryManagement').attr('href',productCategoryManagementUrl);
		$('#product-management').attr('href',productManagementUrl)
	}
	
	
})