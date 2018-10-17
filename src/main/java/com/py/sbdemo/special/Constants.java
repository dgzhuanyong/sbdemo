package com.py.sbdemo.special;

import java.io.Serializable;

/**
 * 常量
 */
public class Constants implements Serializable{

	private static final long serialVersionUID = -2087804150399520300L;
	
	/** 密码加密类型*/
	public static final String HASH_ALGORITHM = "MD5";
	
	/** salt迭代次数*/
	public static final int HASH_INTERATIONS = 1024;
	
	/**用户密码加盐长度*/
	public static final int SALT_SIZE = 8;
	
	/**默认密码值*/
	public static final String DEFAULT_PASSWORD = "123456";
	
	/**通用新增标识*/
	public static final String CURRENCY_INSERT = "add";
	
	/**通用修改标识*/
	public static final String CURRENCY_UPDATE = "update";
	
	/**通用删除标识*/
	public static final String CURRENCY_DELETE = "delete";
	
	/**通用详情标识*/
	public static final String CURRENCY_DETAIL = "detail";
	
	/**通用重置标识*/
	public static final String CURRENCY_RESET = "reset";
	
	/**通用正常情况标识*/
	public static final String CURRENCY_NORMAL = "normal";
	
	/**通用特殊情况标识*/
	public static final String CURRENCY_SPECIAL = "special";
	
	/**通用非特殊情况标识*/
	public static final String CURRENCY_NO_SPECIAL = "noSpecial";
	
	/**通用数字0标识*/
	public static final String CURRENCY_NUMBER_ZERO = "0";
	
	/**通用数字1标识*/
	public static final String CURRENCY_NUMBER_ONE = "1";
	
	/**通用数字2标识*/
	public static final String CURRENCY_NUMBER_TWO = "2";
	
	/**通用数字3标识*/
	public static final String CURRENCY_NUMBER_THREE = "3";
	
	/**通用数字4标识*/
	public static final String CURRENCY_NUMBER_FOUR = "4";
	
	/**通用数字5标识*/
	public static final String CURRENCY_NUMBER_FIVE = "5";
	
	/**通用数字6标识*/
	public static final String CURRENCY_NUMBER_SIX = "6";
}
