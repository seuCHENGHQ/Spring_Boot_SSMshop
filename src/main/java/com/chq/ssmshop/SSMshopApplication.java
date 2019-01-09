package com.chq.ssmshop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
public class SSMshopApplication {

	public static void main(String[] args) {
		SpringApplication.run(SSMshopApplication.class, args);
	}

}
