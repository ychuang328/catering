/**
 * WeixinApiController.java
 * com.weiwork.catering.controller
 * Copyright (c) 2016, 北京微课创景教育科技有限公司版权所有.
*/

package com.weiwork.catering.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.weiwork.catering.support.weixin.SignatureUtil;
import com.weiwork.catering.support.weixin.WeixinUtil;

/**
 * 微信公众号。API
 * <p>
 * 
 * @author   ychuang328
 * @Date	 2016-8-17 	 
 */
@RequestMapping("/weixin")
@Controller
public class WeixinApiController {

	/**
	 * 微信公众号.平台校验
	 * <p>
	 *
	 * @return echostr 随机字符
	 */
	@RequestMapping("authorize")
	public String authorize(@RequestParam("signature") String signature, 
				@RequestParam("timestamp") String timestamp, 
				@RequestParam("nonce") String nonce, 
				@RequestParam("echostr") String echostr){
		if(SignatureUtil.checkSignature(signature, WeixinUtil.TOKEN, timestamp, nonce)){
				return echostr;
		}
		return "";
	}
}

