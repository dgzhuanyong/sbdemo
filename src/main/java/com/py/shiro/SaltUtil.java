package com.py.shiro;

import java.security.SecureRandom;

import org.apache.shiro.crypto.hash.SimpleHash;

public class SaltUtil {
	
	private static final char[] DIGITS_LOWER = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
	private static SecureRandom random = new SecureRandom();
	
	/**
	 * 生成salt
	 * @return
	 */
	public static String getSalt() {
		return encodeHex(generateSalt(8));
	}
	
	
	/**
	 * 生成十六进制的字符串
	 * @param data
	 * @return
	 */
	private static String encodeHex(byte[] data){
		int l = data.length;
		char[] out = new char[l << 1];
		int i = 0; for (int j = 0; i < l; i++) {
			out[(j++)] = DIGITS_LOWER[((0xF0 & data[i]) >>> 4)];
			out[(j++)] = DIGITS_LOWER[(0xF & data[i])];
		}
		return new String(out);
	}
	
	/**
	 * 生成X位字节数
	 * @param nums
	 * @return
	 */
	private static byte[] generateSalt(int nums){
		byte[] bytes = new byte[nums];
		random.nextBytes(bytes);
		return bytes;
	}
	
	public static void main(String[] args) {
		String salt = SaltUtil.getSalt();
		String password = new SimpleHash("MD5", "123456", salt, 1024).toHex();
		System.out.println(password);
		System.out.println(salt);
	}
}
