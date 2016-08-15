package com.weiwork.common.utils.validate;

import java.util.HashSet;
import java.util.Set;

import com.weiwork.common.utils.encrypt.EncryptUtil;

/**
 * 校验码生成与校验
 * 实例：激活码的生成规则，采用的此方法
 * 
 */
public class ValidateCodeUtil {

	private ValidateCodeUtil() {
	}

	/**
	 * 生成校验码，校验码的长度是由index1 + index2决定的
	 * 
	 * @param code	    要产生校验码的原始字符串
	 * @param index	截取生成的校验码的位数，EP：index2 = 4 就是后4位
	 * @return			生成的校验码
	 */
	private static String getValidateCode(final String code, final int index) {
		String sumStr = ValidateCodeUtil.arithmetic(code);
		return sumStr.substring(sumStr.length() - index);
	}

	/**
	 * 生成校验码的算法
	 * 
	 * @param key   原码字符串
	 * @return		根据算法生成的校验码
	 */
	private static String arithmetic(final String key) {
		char[] chars = key.toCharArray();
		String ASCIIStr = "";
		for (char c : chars) {
			ASCIIStr += ValidateCodeUtil.getAscii(c);
		}
		chars = ASCIIStr.toCharArray();
		int sum = 0;
		for (int i = 0; i < chars.length; i++) {
			int sumi = 0;
			for (int j = i; j < chars.length; j++) {
				sumi += Integer.parseInt(chars[j] + "");
			}
			sum += sumi;
		}
		return sum + "";
	}

	/**
	 * 得到字符对应的ASCII码
	 * 
	 * @param cn	单个字符
	 * @return		ASCII码
	 */
	private static int getAscii(final char cn) {
		byte[] bytes = (String.valueOf(cn)).getBytes();
		if (bytes.length == 1) { //单字节字符
			return bytes[0];
		} else if (bytes.length == 2) { //双字节字符
			int hightByte = 256 + bytes[0];
			int lowByte = 256 + bytes[1];
			int ascii = (256 * hightByte + lowByte) - 256 * 256;
			return ascii;
		} else {
			return 0; //错误
		}
	}

	/**
	 * 生成密码,16位长度
	 * @param num	生成的数量
	 * @return 		生成的不重复密码
	 */
	public static Set<String> getValidateCode(final int num) {
		Set<String> set = new HashSet<String>();
		long sl = System.currentTimeMillis();
		for (int i = 0; i < num; i++) {
			String aa = ValidateCodeUtil.getValidateCode(EncryptUtil.md5(sl + i + "1"), 4)
					+ ValidateCodeUtil.getValidateCode(EncryptUtil.md5(sl + i + "2"), 4)
					+ ValidateCodeUtil.getValidateCode(EncryptUtil.md5(sl + i + "3"), 4)
					+ ValidateCodeUtil.getValidateCode(EncryptUtil.md5(sl + i + "4"), 4);
			set.add(aa);
		}
		return set;
	}

	/**
	 * 按指定位数包装数字位数不够以0初齐
	 * @param num		数字
	 * @param second	位数
	 */
	public static String warpNum(final int num, final int second) {
		String s = String.valueOf(num);
		StringBuilder sb = new StringBuilder();
		if (s.length() <= second) {
			int a = second - s.length();
			for (int i = 0; i < a; i++) {
				sb.append("0");
			}
		}
		sb.append(s);
		return sb.toString();
	}
	
	public static void main(String[] args) {
		Set<String> codes = getValidateCode(1000);
		System.out.println(codes.size());
	}
}
