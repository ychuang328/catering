/**
 * SignatureUtil.java
 * com.weiwork.catering.support.weixin
 * Copyright (c) 2016, 北京微课创景教育科技有限公司版权所有.
*/

package com.weiwork.catering.support.weixin;

import java.util.Arrays;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * 微信签名工具
 * <p>
 * 
 * @author   ychuang328
 * @Date	 2016-8-17 	 
 */
public class SignatureUtil {

	/**
	 * 微信公众号.签名校验
	 * <p>
	 * @param signature 传过来的加密后的签名
	 * @param token 开发者填写的token参数
	 * @param timestamp 时间戳
	 * @param nonce 随机数
	 * @return 校验结果 true or false
	 */
	public static boolean checkSignature(String signature, String token, String timestamp, String nonce){
		// 1. 三个参数进行字典序排序
		String[] params = new String[]{token, timestamp, nonce};
		Arrays.sort(params);
		// 2. 将三个参数字符串拼接成一个字符串进行sha1加密
		String data = StringUtils.join(params);
		String shaHex = DigestUtils.shaHex(data);
		// 3. 开发者获得加密后的字符串可与signature对比，标识该请求来源于微信
		return StringUtils.equals(shaHex, signature);
	}
}

