package com.py.yshs.utils;

import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.py.yshs.special.Constants;

/**
 * 工具类
 */
public class Utils {
	
	private static final char[] DIGITS_LOWER = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
	private static SecureRandom random = new SecureRandom();
	private static final Logger logger = LoggerFactory.getLogger(Utils.class);
	static Properties prop = null;
	
	private Utils() {}
	
	static{
		prop = new Properties();
		InputStream in = Utils.class.getResourceAsStream("/paramConfig.properties");
		try {
			prop.load(in);
		} catch (IOException e) {
			logger.error("工具类：Utils 读取属性文件时出现异常");
			logger.error(e.getMessage());
		}
	}
	
	/**
	 * 非空判断
	 * @param values
	 * @return
	 */
	public static boolean isNotNull(List<String> values){
		for (String str : values) {
			if (str == null || "".equals(str.trim()) || str.trim().equalsIgnoreCase("null")) return false;
			str.trim();//去除首位空格
		}
		return true;
	}
	
	/**
	 * 非空判断
	 * @param values
	 * @return
	 */
	public static boolean isNotNull(String[] values){
		for (String str : values) {
			if (str == null || str.trim().equals("") || str.trim().equalsIgnoreCase("null")) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * 非空判断
	 * @param values
	 * @return
	 */
	public static boolean isNotNull(String str){
		if (str == null || str.trim().equals("") || str.trim().equalsIgnoreCase("null")) {
			return false;
		}
		return true;
	}
	
	/**
	 * 非空判断
	 * @param values
	 * @return
	 */
	public static boolean isNotNull(Long lng){
		if (null == lng || "".equals(lng) || lng <= 0) {
			return false;
		}
		return true;
	}
	
	/**
	 * 非空判断
	 * @param values
	 * @return
	 */
	public static boolean isNotNull(Object[] objArry){
		for (Object object : objArry) {
			if (object == null) {
				return false;
			}
		}
		return true;
	}
	
	
	/**
	 * 根据参数名称返回/paramConfig.properties中配置的参数值
	 * @param 参数名称
	 * @return 参数值
	 */
	public static String getProperties(String key) {
		return prop.getProperty(key).trim();
	}
	
	/**
	 * 获取当前系统时间，格式为：yyyy-MM-dd HH:mm:ss
	 */
	public static String getCurrentTime() {
		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return format.format(date);
	}
	
	/**
	 * 获取当前系统日期，格式为：yyyy-MM-dd HH:mm:ss
	 */
	public static Date getCurrentDate() {
		return new Date();
	}
	
	/**
	 * 字符串时间转date
	 * @param time
	 * @param format  yyyy-MM-dd HH:mm:ss
	 * @return	
	 * @throws Exception
	 */
	public static Date formatDate(String time,String format) throws Exception{
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.parse(time);
	}
	
	/**
     * 当浮点型数据位数超过10位之后，数据变成科学计数法显示。用此方法可以使其正常显示。
     * @param value
     * @return Sting
     */
    public static String formatFloatNumber(double value) {
        if(value != 0.00){
            java.text.DecimalFormat df = new java.text.DecimalFormat("########.00");
            return df.format(value);
        }else{
            return "0.00";
        }

    }
    
	/** 
     * 产生随机的六位数 
     * @return 
     */  
    public static String getSix(){  
        Random rad=new Random();  
        String result  = rad.nextInt(1000000) +"";  
        if(result.length()!=6){  
            return getSix();  
        }  
        return result;
    }  
	
	
	/**
	 * 生成唯一标识   uuid 并用“”替换“-”
	 *  uuid :xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx (8-4-4-4-12)
	 * @return 唯一标识
	 */
	public static String getUuid() {
		String id = UUID.randomUUID().toString();
		id = id.replace("-","");
		return id;
	}
	
	/**
	 * 生成十六进制的字符串
	 * @param data
	 * @return
	 */
	public static String encodeHex(byte[] data){
		int l = data.length;
		char[] out = new char[l << 1];
		int i = 0; for (int j = 0; i < l; i++) {
			out[(j++)] = DIGITS_LOWER[((0xF0 & data[i]) >>> 4)];
			out[(j++)] = DIGITS_LOWER[(0xF & data[i])];
		}
		return new String(out);
	}
	
	/**
	 * 生成8位字节数
	 * @param nums
	 * @return
	 */
	public static byte[] generateSalt(int nums){
		byte[] bytes = new byte[nums];
		random.nextBytes(bytes);
		return bytes;
	}
	
	/**
	 * 密码加密
	 * @param input	原密码
	 * @param algorithm	加密方式，默认SHA-1
	 * @param salt	盐值
	 * @param iterations	加密次数
	 * @return
	 */
	public static byte[] encodeHex(byte[] input, byte[] salt) throws Exception{
		MessageDigest digest = MessageDigest.getInstance(Constants.HASH_ALGORITHM);
		if (salt != null) {
			digest.update(salt);
		}
		byte[] result = digest.digest(input);
		for (int i = 1; i < Constants.HASH_INTERATIONS; i++) {
			digest.reset();
			result = digest.digest(result);
		}
		return result;
	}
	/**
	 * 密码解密
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public static byte[] decodeHex(char[] data) throws Exception{
		int len = data.length;
		if ((len & 0x1) != 0) {
			throw new Exception("Odd number of characters.");
		}
		byte[] out = new byte[len >> 1];
		int i = 0; 
		for (int j = 0; j < len; i++) {
			int f = toDigit(data[j], j) << 4;
			j++;
			f |= toDigit(data[j], j);
			j++;
			out[i] = ((byte)(f & 0xFF));
		}
	  
		return out;
	}
	
	private static int toDigit(char ch, int index)throws Exception{
		int digit = Character.digit(ch, 16);
		if (digit == -1) {
			throw new Exception("Illegal hexadecimal character " + ch + " at index " + index);
		}
		return digit;
	}
	
}
