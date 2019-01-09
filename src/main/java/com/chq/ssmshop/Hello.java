package com.chq.ssmshop;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Hello {

	/**
	 * 注意这里的不同，Spring中为@requestmapping(value="/hello",...)
	 * 在springboot中，为name="/hello"
	 * 
	 * @return
	 */
	@RequestMapping(value = "/hello", method = RequestMethod.GET)
	public String hello() {
		return "spring boot!";
	}
}
