package com.py;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * 
 * Description: spring boot 启动类继承自 SpringBootServletInitializer 方可正常部署至常规tomcat下，其主要能够起到web.xml的作用
 *		
 * @author changlu
 *
 * 2018年8月3日
 */
public class ServletInitializer extends SpringBootServletInitializer {
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(SpringBootAppApplication.class);
	}
}
