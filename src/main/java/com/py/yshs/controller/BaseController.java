package com.py.yshs.controller;

import javax.servlet.http.HttpServletRequest;

public class BaseController {
	// ================= 分页用的参数====================
	protected int pageNum = 1; // 当前页
	protected int startIndex = 0; // 开始索引
	protected int pageSize = 100; // 每页显示多少条记录

	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		if (pageNum > 1) {
			startIndex = ((pageNum - 1) * pageSize);
		} else {
			startIndex = 0;
		}
		this.pageNum = pageNum;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	/**
	 * 接受前台传入的分页参数进行设置
	 * 
	 * @param request
	 */
	protected void pageParameter(HttpServletRequest request) {
		// 获取page和pagesize的值
		String pageStr = request.getParameter("PageNum");
		String pagesizeStr = request.getParameter("PageSize");
		if (pageStr != null && !"".equals(pageStr))
			setPageNum(Integer.parseInt(pageStr));
		else
			pageNum = 1;
		setPageNum(pageNum);
		if (pagesizeStr != null && !"".equals(pagesizeStr))
			setPageSize(Integer.parseInt(pagesizeStr));
		else
			pageSize = 100;
		setPageSize(pageSize);
	}

	/**
	 * 接受前台传入的分页参数进行设置 设置每页数据条数 num
	 * 
	 * @param request
	 */
	protected void pageParameter(HttpServletRequest request, int num) {
		// 获取page和pagesize的值
		String pageStr = request.getParameter("PageNum");
		String pagesizeStr = request.getParameter("PageSize");
		if (pageStr != null && !"".equals(pageStr))
			setPageNum(Integer.parseInt(pageStr));
		else
			pageNum = 1;
		setPageNum(pageNum);
		if (pagesizeStr != null && !"".equals(pagesizeStr))
			setPageSize(Integer.parseInt(pagesizeStr));
		else
			pageSize = num;
		setPageSize(pageSize);
	}
}
