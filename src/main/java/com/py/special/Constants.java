package com.py.special;

import java.io.Serializable;

/**
 * 常量
 */
public class Constants implements Serializable{

	private static final long serialVersionUID = -2087804150399520300L;

	/**当前用户*/
	public static final String CURRENT_USER = "currentUser";
	
	/**当前用户所拥有的权限*/
	public static final String CURRENT_USER_PRIVILEGES = "privileges";
	
	/** 强制退出session*/
	public static final String SESSION_FORCE_LOGOUT_KEY = "session.force.logout";
	
	/** 密码加密类型*/
	public static final String HASH_ALGORITHM = "SHA-1";
	
	/** sha-1 迭代次数( 设定安全的密码，生成随机的salt并经过1024次 sha-1 hash )*/
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
