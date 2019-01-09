package com.chq.ssmshop.web.productadmin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.chq.ssmshop.dto.ImageHolder;
import com.chq.ssmshop.dto.ProductExecution;
import com.chq.ssmshop.dto.ProductImageHolder;
import com.chq.ssmshop.dto.ProductImgExecution;
import com.chq.ssmshop.entity.Product;
import com.chq.ssmshop.entity.ProductImg;
import com.chq.ssmshop.enums.ProductExecutionEnum;
import com.chq.ssmshop.enums.ProductImgExecutionEnum;
import com.chq.ssmshop.service.ProductImgService;
import com.chq.ssmshop.service.ProductService;
import com.chq.ssmshop.util.CodeUtil;
import com.chq.ssmshop.util.HttpServletRequestUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@RequestMapping(value = "/productoperation")
public class ProductOperationController {

	private static final int MAX_PRODUCT_IMG = 6;

	@Autowired
	private ProductService productService;

	@Autowired
	private ProductImgService productImgService;

	@RequestMapping(value = "/getproductlist", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getProductList(@RequestParam long shopId) {
		Map<String, Object> modelMap = new HashMap<>();
		ProductExecution pe = productService.selectProductListByShopId(shopId);
		if (pe.getState() == ProductExecutionEnum.SUCCESS.getState()) {
			modelMap.put("success", true);
			modelMap.put("productList", pe.getProductList());
		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", pe.getStateInfo());
		}

		return modelMap;
	}

	@RequestMapping(value = "/registerproduct", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> registerProduct(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<>();

		// 检查验证码是否正确
		if (!CodeUtil.checkVerifyCode(request)) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "验证码错误");
			return modelMap;
		}

		// 从request中获取JSON字符串并生成Product对象
		String productStr = HttpServletRequestUtil.getString(request, "productStr");
		if (productStr == null) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "productStr为null");
			return modelMap;
		}

		ObjectMapper mapper = new ObjectMapper();
		Product product = null;
		try {
			product = (Product) mapper.readValue(productStr, Product.class);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			modelMap.put("success", false);
			modelMap.put("errMsg", "生成Product对象时出现问题");
			return modelMap;
		}

		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());
		CommonsMultipartFile multipartFile = null;
		MultipartHttpServletRequest multipartRequest = null;
		if (multipartResolver.isMultipart(request)) {
			// 获取product的缩略图
			multipartRequest = (MultipartHttpServletRequest) request;
			multipartFile = (CommonsMultipartFile) multipartRequest.getFile("product-thumbnail");
			if (multipartFile != null) {
				try {
					ImageHolder productThumbnail = new ImageHolder(multipartFile.getInputStream(),
							multipartFile.getOriginalFilename());
					// 这里生成product的缩略图和将product插入到数据库中
					ProductExecution pe = productService.insertProduct(product, productThumbnail);
					if (pe.getState() != ProductExecutionEnum.SUCCESS.getState()) {
						modelMap.put("success", false);
						modelMap.put("errMsg", pe.getStateInfo());
						return modelMap;
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					modelMap.put("success", false);
					modelMap.put("errMsg", e.getMessage());
					return modelMap;
				}
			} else {
				modelMap.put("success", false);
				modelMap.put("errMsg", "必须上传产品缩略图");
				return modelMap;
			}
		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "必须上传产品缩略图");
			return modelMap;
		}

		// 获取productImg
		List<ProductImageHolder> productImgList = new ArrayList<>();
		for (int i = 0; i < MAX_PRODUCT_IMG; i++) {
			multipartFile = (CommonsMultipartFile) multipartRequest.getFile("productImg" + i);
			if (multipartFile != null) {
				try {
					productImgList.add(new ProductImageHolder(multipartFile.getInputStream(),
							multipartFile.getOriginalFilename(), product));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					modelMap.put("success", false);
					modelMap.put("errMsg", e.getMessage());
					return modelMap;
				}
			} else {
				break;
			}
		}

		// 插入productImg
		ProductImgExecution pie = null;
		for (ProductImageHolder imageHolder : productImgList) {
			ProductImg productImg = new ProductImg();
			productImg.setProduct(product);
			pie = productImgService.insertProductImg(productImg, imageHolder);
			if (pie.getState() != ProductImgExecutionEnum.SUCCESS.getState()) {
				modelMap.put("success", false);
				modelMap.put("errMsg", pie.getStateInfo());
				return modelMap;
			}
		}

		modelMap.put("success", true);

		return modelMap;

	}

	@RequestMapping(value = "/updateproduct", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateProduct(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<>();

		// 检查验证码是否正确
		if (!CodeUtil.checkVerifyCode(request)) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "验证码错误");
			return modelMap;
		}

		// 从request中获取JSON字符串并生成Product对象
		String productStr = HttpServletRequestUtil.getString(request, "productStr");
		if (productStr == null) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "productStr为null");
			return modelMap;
		}

		ObjectMapper mapper = new ObjectMapper();
		Product product = null;
		try {
			product = (Product) mapper.readValue(productStr, Product.class);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			modelMap.put("success", false);
			modelMap.put("errMsg", "生成Product对象时出现问题");
			return modelMap;
		}

		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());
		CommonsMultipartFile multipartFile = null;
		MultipartHttpServletRequest multipartRequest = null;
		if (multipartResolver.isMultipart(request)) {
			multipartRequest = (MultipartHttpServletRequest) request;
			multipartFile = (CommonsMultipartFile) multipartRequest.getFile("product-thumbnail");
			ImageHolder productThumbnail = null;
			if (multipartFile != null) {
				try {
					// 获取上传的缩略图
					productThumbnail = new ImageHolder(multipartFile.getInputStream(),
							multipartFile.getOriginalFilename());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					modelMap.put("success", false);
					modelMap.put("errMsg", e.getMessage());
					return modelMap;
				}
			}

			// 获取productImg
			List<ProductImageHolder> productImgList = new ArrayList<>();
			for (int i = 0; i < MAX_PRODUCT_IMG; i++) {
				multipartFile = (CommonsMultipartFile) multipartRequest.getFile("productImg" + i);
				if (multipartFile != null) {
					try {
						productImgList.add(new ProductImageHolder(multipartFile.getInputStream(),
								multipartFile.getOriginalFilename(), product));
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						modelMap.put("success", false);
						modelMap.put("errMsg", e.getMessage());
						return modelMap;
					}
				} else {
					break;
				}
			}
			// 更新product
			ProductExecution pe = productService.updateProduct(product, productThumbnail, productImgList);
			if (pe.getState() == ProductExecutionEnum.SUCCESS.getState()) {
				modelMap.put("success", true);
				modelMap.put("product", product);
			} else {
				modelMap.put("success", false);
				modelMap.put("errMsg", pe.getStateInfo());
			}
		}

		return modelMap;
	}

	@RequestMapping(value = "/getproductbyid", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getProductById(@RequestParam long productId) {
		Map<String, Object> modelMap = new HashMap<>();
		ProductExecution pe = productService.selectProductById(productId);
		if (pe.getState() == ProductExecutionEnum.SUCCESS.getState()) {
			modelMap.put("success", true);
			modelMap.put("product", pe.getProduct());
		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", pe.getStateInfo());
		}
		return modelMap;
	}

	@RequestMapping(value = "/changestatus", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> changeStatus(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<>();
		// 从request中获取JSON字符串并生成Product对象
		String productStr = HttpServletRequestUtil.getString(request, "productStr");
		if (productStr == null) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "productStr为null");
			return modelMap;
		}

		ObjectMapper mapper = new ObjectMapper();
		Product product = null;
		try {
			product = (Product) mapper.readValue(productStr, Product.class);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			modelMap.put("success", false);
			modelMap.put("errMsg", "生成Product对象时出现问题");
			return modelMap;
		}
		product.setLastEditTime(new Date());
		ProductExecution pe = productService.changeProductEnableStatus(product);
		if(pe.getState()!=ProductExecutionEnum.SUCCESS.getState()) {
			modelMap.put("success", false);
			modelMap.put("errMsg", pe.getStateInfo());
			return modelMap;
		}else {
			modelMap.put("success", true);
		}
		return modelMap;
	}
}
