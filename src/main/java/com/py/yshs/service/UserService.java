package com.py.yshs.service;

import org.springframework.stereotype.Service;

import com.py.yshs.utils.Utils;

@Service
public class UserService {
	
	
	/**
	 * 根据明文密码和盐值获取加密的密码（使用shiro的加密方式）
	 * @param password	明文密码
	 * @param salt	盐值
	 * @return
	 */
	public String getEncodePwd(String password, String salt) throws Exception{
		byte[] saltByte = Utils.decodeHex(salt.toCharArray());
		byte[] hashPassword = Utils.encodeHex(password.getBytes(), saltByte);
		return Utils.encodeHex(hashPassword);
	}
}
