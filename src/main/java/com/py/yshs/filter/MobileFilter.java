package com.py.yshs.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * Description: 手机端filter
 *		
 * @author changlu
 *
 * 2018年8月3日
 */
@WebFilter(urlPatterns="/*")
public class MobileFilter implements Filter{
	
	private static Logger logger = LoggerFactory.getLogger(MobileFilter.class);

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		logger.info("手机端filter初始化");
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
        chain.doFilter(request, response);
		
	}

	@Override
	public void destroy() {
		logger.info("手机端filter销毁");
	}
	
}
