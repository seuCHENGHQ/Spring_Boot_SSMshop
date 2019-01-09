package com.chq.ssmshop.config.web;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.chq.ssmshop.interceptor.ShopLoginInterceptor;
import com.chq.ssmshop.interceptor.ShopPermissionInterceptor;
import com.google.code.kaptcha.servlet.KaptchaServlet;

/**
 * 开启MVC 自动注入Spring IoC容器 WebMvcConfigurerAdapter：配置视图解析器
 * ApplicationContextAware接口，让MvcConfiguration可以调用自己所在的上下文中的其他bean
 * 
 * 注意，在Spring5.0中，WebMvcConfigurerAdapter已被废止
 * 
 * @author chq
 *
 */
@Configuration
//相当于在xml中配置<mvc:annotation-driven/> 同时还取消了spring boot对Spring mvc进行自动配置
@EnableWebMvc
public class MvcConfiguration implements WebMvcConfigurer, ApplicationContextAware {

	private ApplicationContext applicationContext;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	/**
	 * <mvc:resources mapping="/resources/**" location="/resources/" />
	 */
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		// 对静态资源进行拦截，把resources/**的请求解析到classpath:/resources/**下面去
		// 这里主要是针对js css文件
//		registry.addResourceHandler("/resources/**").addResourceLocations("classpath:/resources/");
		registry.addResourceHandler("/mainpage/**")
				.addResourceLocations("file:/home/chq/java_workspace/ImageResources/mainpage/");
		registry.addResourceHandler("/home/chq/java_workspace/ImageResources/shop/**")
				.addResourceLocations("file:/home/chq/java_workspace/ImageResources/shop/");
//		WebMvcConfigurer.super.addResourceHandlers(registry);
	}

	/**
	 * <mvc:default-servlet-handler /> 定义默认的请求处理器
	 */
	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}

	/**
	 * 创建视图解析器
	 * 
	 * @return
	 */
	@Bean(name = "viewResolver")
	public ViewResolver createViewResolver() {
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		// 这个一定要有，设置ApplicationContext
		viewResolver.setApplicationContext(this.applicationContext);
		viewResolver.setCache(false);
		viewResolver.setPrefix("/WEB-INF/html");
		viewResolver.setSuffix(".html");
		return viewResolver;
	}

	/**
	 * 设置文件上传解析器
	 * 
	 * @return
	 */
	@Bean(name = "multipartResolver")
	public MultipartResolver createMultipartResolver() {
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
		multipartResolver.setDefaultEncoding("utf-8");
		// 1024*1024=20M
		multipartResolver.setMaxUploadSize(20971520);
		multipartResolver.setMaxInMemorySize(20971520);
		return multipartResolver;
	}

	@Value("${kaptcha.border}")
	private String border;
	@Value("${kaptcha.textproducer.font.color}")
	private String fontColor;
	@Value("${kaptcha.textproducer.font.size}")
	private String fontSize;
	@Value("${kaptcha.image.width}")
	private String imageWidth;
	@Value("${kaptcha.image.height}")
	private String imageHeight;
	@Value("${kaptcha.textproducer.char.string}")
	private String charString;
	@Value("${kaptcha.noise.color}")
	private String noiseColor;
	@Value("${kaptcha.textproducer.char.length}")
	private String charLength;
	@Value("${kaptcha.textproducer.font.names}")
	private String fontName;

	/**
	 * 加载验证码的servlet
	 */
	@Bean
	public ServletRegistrationBean<KaptchaServlet> createServletRegistryBean() {
		ServletRegistrationBean<KaptchaServlet> servlet = new ServletRegistrationBean<KaptchaServlet>(
				new KaptchaServlet(), "/Kaptcha");
		// 配置验证码的相关参数
		servlet.addInitParameter("kaptcha.border", border);
		servlet.addInitParameter("kaptcha.textproducer.font.color", fontColor);
		servlet.addInitParameter("kaptcha.textproducer.font.size", fontSize);
		servlet.addInitParameter("kaptcha.image.width", imageWidth);
		servlet.addInitParameter("kaptcha.image.height", imageHeight);
		servlet.addInitParameter("kaptcha.textproducer.char.string", charString);
		servlet.addInitParameter("kaptcha.noise.color", noiseColor);
		servlet.addInitParameter("kaptcha.textproducer.char.length", charLength);
		servlet.addInitParameter("kaptcha.textproducer.font.names", fontName);
		return servlet;
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		// 拦截路径
		String interceptPath = "/shopadmin/**";
		// 注册拦截器 InterceptorRegistration的作用是在添加完拦截器后，用来对拦截器进行进一步的配置，比如加入拦截路径
		InterceptorRegistration loginIR = registry.addInterceptor(new ShopLoginInterceptor());
		loginIR.addPathPatterns(interceptPath);

		// 注册权限验证拦截器
		InterceptorRegistration permissionIR = registry.addInterceptor(new ShopPermissionInterceptor());
		permissionIR.addPathPatterns(interceptPath);
		// 这里都是成对出现的，路由和执行查询操作的url都不应该被拦截
		permissionIR.excludePathPatterns("/shopadmin/shoplist");
		permissionIR.excludePathPatterns("/shopadmin/getshoplistbyowner/**");
		
		permissionIR.excludePathPatterns("/shopadmin/shopmanagement");
		permissionIR.excludePathPatterns("/shopadmin/getshopbyid/**");
		
		permissionIR.excludePathPatterns("/shopadmin/register");
		permissionIR.excludePathPatterns("/shopadmin/registershop");
//		WebMvcConfigurer.super.addInterceptors(registry);
	}

}
