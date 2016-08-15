package com.weiwork.common.utils.http;

import java.util.HashMap;
import java.util.Map;

/**
 * api接口调用后台服务，参数自定义传输封装
 * 
 */
public class HttpParam {

	// 通过cookie传输的参数名称定义
	public final static String COOKIE_TOKEN = "vkoweb";
	public final static String COOKIE_TERMINAL = "terminal";
	public final static String COOKIE_SID = "sid";

	// 通过http头自定义传输的参数名称定义
	public final static String HEADER_IP = "X-Forwarded-For";
	// 设置http头的User-Agent参数
	public final static String HEADER_USER_AGENT = "User-Agent";
	
	private Map<String, String> commonParams; // get/post 传参
	private Map<String, String> cookieParams; // cookie 传参
	private Map<String, String> headerParams; // header 传参

	/**
	 * 私有初始化
	 */
	private HttpParam() {
		commonParams = new HashMap<String, String>();
		cookieParams = new HashMap<String, String>();
		headerParams = new HashMap<String, String>();
	}

	/**
	 * 静态调用初始化
	 */
	public static HttpParam init() {
		return new HttpParam();
	}

	public boolean hasCommon() {
		return !getCommonParams().isEmpty();
	}

	public boolean hasCookie() {
		return !getCookieParams().isEmpty();
	}

	public boolean hasHeader() {
		return !getHeaderParams().isEmpty();
	}

	public HttpParam addCommon(String key, String value) {
		getCommonParams().put(key, value);
		return this;
	}

	public HttpParam addCookie(String key, String value) {
		getCookieParams().put(key, value);
		return this;
	}

	public HttpParam addHeader(String key, String value) {
		getHeaderParams().put(key, value);
		return this;
	}

	public Map<String, String> getCommonParams() {
		return commonParams;
	}

	public Map<String, String> getCookieParams() {
		return cookieParams;
	}

	public Map<String, String> getHeaderParams() {
		return headerParams;
	}

}
