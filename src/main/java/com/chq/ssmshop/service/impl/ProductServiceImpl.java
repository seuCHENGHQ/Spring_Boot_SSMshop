package com.chq.ssmshop.service.impl;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chq.ssmshop.dao.ProductDao;
import com.chq.ssmshop.dto.ImageHolder;
import com.chq.ssmshop.dto.ProductExecution;
import com.chq.ssmshop.dto.ProductImageHolder;
import com.chq.ssmshop.dto.ProductImgExecution;
import com.chq.ssmshop.entity.Product;
import com.chq.ssmshop.enums.ProductExecutionEnum;
import com.chq.ssmshop.enums.ProductImgExecutionEnum;
import com.chq.ssmshop.exception.ProductOperationException;
import com.chq.ssmshop.service.ProductImgService;
import com.chq.ssmshop.service.ProductService;
import com.chq.ssmshop.util.ImageUtil;
import com.chq.ssmshop.util.PageCalculator;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductDao productDao;
	@Autowired
	private ProductImgService productImgService;

	@Override
	@Transactional
	public ProductExecution insertProduct(Product product, ImageHolder productThumbnail) {
		// TODO Auto-generated method stub
		if (product == null) {
			return new ProductExecution(ProductExecutionEnum.NULL_PRODUCT);
		} else if (product.getProductName() == null || product.getProductName().length() == 0) {
			return new ProductExecution(ProductExecutionEnum.NULL_PRODUCT_NAME);
		} else if (product.getProductCategory() == null
				|| product.getProductCategory().getProductCategoryId() == null) {
			return new ProductExecution(ProductExecutionEnum.NULL_PRODUCT_CATEGORY);
		} else if (product.getShop() == null || product.getShop().getShopId() == null) {
			return new ProductExecution(ProductExecutionEnum.NULL_SHOP);
		}

		if (product.getPriority() == null) {
			product.setPriority(0);
		}
		product.setCreateTime(new Date());
		product.setLastEditTime(new Date());
		product.setEnableStatus(0);
		int effectedRow = productDao.insertProduct(product);

		if (effectedRow < 1) {
			throw new ProductOperationException("productDao注册商品时出现异常！");
		}

		if (productThumbnail == null || productThumbnail.getImage() == null || productThumbnail.getFileName() == null) {
			return new ProductExecution(ProductExecutionEnum.NULL_PRODUCT_THUMBNAIL);
		} else {
			try {
				addProductThumbnail(product, productThumbnail);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new ProductOperationException("生成商品缩略图时出现异常！");
			}
			product.setLastEditTime(new Date());
			effectedRow = productDao.updateProductByProductId(product);
			if (effectedRow < 1) {
				throw new ProductOperationException("productDao更新时出现异常！");
			}
		}

		return new ProductExecution(ProductExecutionEnum.SUCCESS, product);
	}

	@Override
	public ProductExecution selectProductListByShopId(long shopId) {
		// TODO Auto-generated method stub
		List<Product> productList = productDao.getProductListByShopId(shopId);
		ProductExecution pe = null;
		if (productList != null) {
			pe = new ProductExecution(ProductExecutionEnum.SUCCESS, productList);
		} else {
			pe = new ProductExecution(ProductExecutionEnum.UNSUCCESS);
		}
		return pe;
	}

	@Override
	public ProductExecution selectProductById(long productId) {
		// TODO Auto-generated method stub
		Product product = productDao.getProductByProductId(productId);
		ProductExecution pe = null;
		if (product != null) {
			pe = new ProductExecution(ProductExecutionEnum.SUCCESS, product);
		} else {
			pe = new ProductExecution(ProductExecutionEnum.UNSUCCESS);
		}
		return pe;
	}

	private void addProductThumbnail(Product product, ImageHolder productThumbnail) throws IOException {
		StringBuilder sb = new StringBuilder();
		sb.append(product.getShop().getShopId());
		sb.append("/");
		sb.append(product.getProductId());
		sb.append("/");

		String targetAddr = sb.toString();
		String productThumbnailPath = ImageUtil.generateThumbnail(productThumbnail.getImage(),
				productThumbnail.getFileName(), targetAddr);
		product.setImgAddr(productThumbnailPath);
	}

	private void deleteProductThumbnail(Product product) {
		ImageUtil.deleteImage(product.getImgAddr());
	}

	@Override
	@Transactional
	public ProductExecution updateProduct(Product product, ImageHolder productThumbnail) {
		// TODO Auto-generated method stub
		if (product == null || product.getProductId() == null) {
			return new ProductExecution(ProductExecutionEnum.NULL_PRODUCT);
		}

		Product existedProduct = productDao.getProductByProductId(product.getProductId());

		if (productThumbnail != null && productThumbnail.getImage() != null && productThumbnail.getFileName() != null) {
			if (existedProduct.getImgAddr() != null) {
				deleteProductThumbnail(existedProduct);
			}
			try {
				addProductThumbnail(product, productThumbnail);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new ProductOperationException("生成商品缩略图时出现异常！");
			}
		}
		product.setLastEditTime(new Date());
		int effectedRow = productDao.updateProductByProductId(product);
		if (effectedRow < 1) {
			throw new ProductOperationException("productDao更新时出现异常！");
		}

		return new ProductExecution(ProductExecutionEnum.SUCCESS, product);
	}

	/**
	 * 这个方法还未完成，请勿使用！
	 */
	@Override
	public ProductExecution deleteProduct(long productId) {
		// TODO Auto-generated method stub
		Product product = productDao.getProductByProductId(productId);
		if (product == null) {
			return new ProductExecution(ProductExecutionEnum.DELETE_ERROR);
		} else {
			// 删除product时，不仅要删掉数据库中的数据，文件系统中的图片同样也要删掉
			return new ProductExecution(ProductExecutionEnum.SUCCESS, product);
		}
	}

	@Override
	@Transactional
	public ProductExecution insertProduct(Product product, ImageHolder productThumbnail,
			List<ProductImageHolder> productImgList) {
		// TODO Auto-generated method stub

		return null;
	}

	@Override
	@Transactional
	public ProductExecution updateProduct(Product product, ImageHolder productThumbnail,
			List<ProductImageHolder> productImgList) {
		// TODO Auto-generated method stub
		ProductExecution pe = this.updateProduct(product, productThumbnail);
		if (pe.getState() != ProductExecutionEnum.SUCCESS.getState()) {
			throw new ProductOperationException("更新product时出现异常！");
		}

		ProductImgExecution pie = productImgService.deleteProductImgByProductId(product.getProductId());
		if (pie.getState() != ProductImgExecutionEnum.SUCCESS.getState()) {
			throw new ProductOperationException("删除原有producgImg时出现异常！");
		}

		pie = productImgService.insertProductImg(product, productImgList);
		if (pie.getState() != ProductImgExecutionEnum.SUCCESS.getState()) {
			throw new ProductOperationException("添加producgImg时出现异常！");
		}

		return pe;
	}

	@Override
	@Transactional
	public ProductExecution changeProductEnableStatus(Product product) {
		// TODO Auto-generated method stub
		if (product == null || product.getProductId() == null || product.getEnableStatus() == null) {
			return new ProductExecution(ProductExecutionEnum.UNSUCCESS);
		}
		int effectedRows = productDao.updateProductByProductId(product);
		if (effectedRows < 1) {
			throw new ProductOperationException("改变product enable status时出现异常！");
		}
		return new ProductExecution(ProductExecutionEnum.SUCCESS, product);
	}

	@Override
	public ProductExecution getProductList(Product productCondition, int pageIndex, int pageSize) {
		int rowIndex = PageCalculator.calculateRowIndex(pageIndex, pageSize);
		List<Product> productList = productDao.queryProductList(productCondition, rowIndex, pageSize);
		int count = productDao.queryProductCount(productCondition);
		ProductExecution pe = new ProductExecution();
		pe.setProductList(productList);
		pe.setCount(count);
		return pe;
	}
}
