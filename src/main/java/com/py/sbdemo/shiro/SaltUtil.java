package com.py.sbdemo.shiro;

import java.security.SecureRandom;

import org.apache.shiro.crypto.hash.SimpleHash;

import com.py.sbdemo.special.Constants;

public class SaltUtil {
	
	private static final char[] DIGITS_LOWER = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
	private static SecureRandom random = new SecureRandom();
	
	/**
	 * 生成salt
	 * @return
	 */
	public static String getSalt() {
		return encodeHex(generateSalt(Constants.SALT_SIZE));
	}
	
	/**
	 * 生成密码
	 * @param salt
	 * @return
	 */
	public static String getPassWord(String salt) {
		return new SimpleHash(Constants.HASH_ALGORITHM, Constants.DEFAULT_PASSWORD, salt, Constants.HASH_INTERATIONS).toHex();
	}
	
	/**
	 * 生成密码
	 * @param salt
	 * @return
	 */
	public static String getPassWord(String salt,String password) {
		return new SimpleHash(Constants.HASH_ALGORITHM, password, salt, Constants.HASH_INTERATIONS).toHex();
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
	
	
	
	public static void main(String[] args) {
		String salt = SaltUtil.getSalt();
		String password = SaltUtil.getPassWord(salt);
		System.out.println(password);
		System.out.println(salt);
	}
}
