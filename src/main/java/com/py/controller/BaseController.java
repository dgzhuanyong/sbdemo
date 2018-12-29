package com.py.controller;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import com.py.utils.Utils;

import sun.misc.BASE64Decoder;

public class BaseController {
	
	/**================= 分页用的参数====================**/
	/**
	 * 当前页 
	 */
	protected int pageNum = 1;
	/**
	 * 开始索引
	 */
	protected int startIndex = 0; 
	/**
	 * 每页显示多少条记录
	 */
	protected int pageSize = 10; 

	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		if (pageNum > 1) {
			startIndex = ((pageNum - 1) * pageSize);
		}else {
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
	/**================= 排序用的参数====================**/
	/**
	 * 排序列
	 */
	protected String orderTable="id";
	/**
	 * 排序方式
	 */
	protected String orderStyle="DESC";
	
	public String getOrderTable() {
		return orderTable;
	}
	public void setOrderTable(String orderTable) {
		this.orderTable = orderTable;
	}
	public String getOrderStyle() {
		return orderStyle;
	}
	public void setOrderStyle(String orderStyle) {
		this.orderStyle = orderStyle;
	}
	
	
	/**
	 * 接受前台传入的分页参数进行设置(LayerUI)
	 * @param request
	 */
	protected void LayerPage(HttpServletRequest request) {
		//获取page和pagesize的值
		String pageStr =  request.getParameter("page");
		String pagesizeStr =  request.getParameter("limit");
		String pageTable =  request.getParameter("field");
		String pageStyle =  request.getParameter("order");
		if (pageStr != null && !"".equals(pageStr))	setPageNum(Integer.parseInt(pageStr));
		else pageNum = 1; setPageNum(pageNum);
		if (pagesizeStr != null && !"".equals(pagesizeStr)) setPageSize(Integer.parseInt(pagesizeStr));
		else pageSize = 10; setPageSize(pageSize);
		if (pageTable != null && !"".equals(pageTable))	setOrderTable(pageTable);
		else orderTable = "id"; setOrderTable(orderTable);
		if (pageStyle != null && !"".equals(pageStyle))	setOrderStyle(pageStyle);
		else orderStyle = "DESC"; setOrderStyle(orderStyle);
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
	
	
	/**
	 * base64转化为图片
	 * @param pic
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("restriction")
	public String base64ToPic(String pic) throws Exception{
		if(pic.indexOf(",") > -1) {
			pic = pic.split(",")[1];
		}
		String newFileName = "";
		BASE64Decoder base = new BASE64Decoder();
        byte[] decode = base.decodeBuffer(pic);
        // 图片输出路径
        String savePath = Utils.getProperties("file_upload_path");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		newFileName = sdf.format(new Date()) + "_" + new Random().nextInt(1000) + ".jpg";
		File path = new File(savePath);
		if(!path.exists()){
			path.mkdirs();
		}
		File file = new File(savePath,newFileName);
		file.createNewFile();
        // 定义图片输入流
        InputStream fin = new ByteArrayInputStream(decode);
        // 定义图片输出流
        FileOutputStream fout = new FileOutputStream(file);
        // 写文件
        byte[] b = new byte[1024];
        int length=0;
		while((length = fin.read(b))>0){
		    fout.write(b, 0, length);
		}
        fin.close();
        fout.close();
		return newFileName;
	}
	
	
	
	
}
