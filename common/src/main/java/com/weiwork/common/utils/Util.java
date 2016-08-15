package com.weiwork.common.utils;

import java.lang.reflect.Array;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Collection;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

/**
 * 常用的工具类的集合
 * <p>
 * 包含最常用工具类，比如判空、判断相等等与具体对象无关的方法
 * @Date	 2015-4-27 
 */
public class Util {

	/**
	 * 判断一个对象是否为空。它支持如下对象类型：
	 * <ul>
	 * <li>null : 一定为空
	 * <li>字符串     : ""为空,多个空格也为空
	 * <li>数组
	 * <li>集合
	 * <li>Map
	 * <li>其他对象 : 一定不为空
	 * </ul>
	 * 
	 * @param obj
	 *            任意对象
	 * @return 是否为空
	 */
	public final static boolean isEmpty(final Object obj) {
		if (obj == null) {
			return true;
		}
		if (obj instanceof String) {
			return "".equals(String.valueOf(obj).trim());
		}
		if (obj.getClass().isArray()) {
			return Array.getLength(obj) == 0;
		}
		if (obj instanceof Collection<?>) {
			return ((Collection<?>) obj).isEmpty();
		}
		if (obj instanceof Map<?, ?>) {
			return ((Map<?, ?>) obj).isEmpty();
		}
		return false;
	}
	
	/**
	 * 判断当前操作系统是否为windows
	 * 
	 * @return windwos操作系统为true，否则false
	 */
	public final static boolean isWinOs() {
		return System.getProperties().getProperty("os.name").toLowerCase().startsWith("win");
	}

	/**
	 * 判断当前请求是否来自IOS
	 * 
	 * @return 如果是 IOS则为true，否则false
	 */
	public final static boolean isIOS(HttpServletRequest request) {
		if(request == null || StringUtils.isBlank(request.getHeader("User-Agent"))){
			return false;
		}
		String ua = request.getHeader("User-Agent").toLowerCase();
		return (ua.contains("iphone;") || ua.contains("iphone os") || ua.contains("mac os"));
	}
	
	/**
	 * 判断当前请求是否来自Android
	 * 
	 * @return 如果是 Android则为true，否则false
	 */
	public final static boolean isAndroid(HttpServletRequest request) {
		if(request == null || StringUtils.isBlank(request.getHeader("User-Agent"))){
			return false;
		}
		String ua = request.getHeader("User-Agent").toLowerCase();
		return (ua.contains("linux;") || ua.contains("android"));
	}
	
	/**
	 * 判断当前请求是否来自微信打开
	 * 
	 * @return 如果是 微信  则为true，否则false
	 */
	public final static boolean isWeixinOpen(HttpServletRequest request) {
		if(request == null || StringUtils.isBlank(request.getHeader("User-Agent"))){
			return false;
		}
		String ua = request.getHeader("User-Agent").toLowerCase();
		return ua.contains("micromessenger");
	}

	/**
	 * 判断当前请求是否来自PC浏览器
	 * 
	 * @return 如果是 PC浏览器 则为true，否则false
	 */
	public final static boolean isPC(HttpServletRequest request) {
		return !isMobile(request);
	}

	/**
	 * 判断当前请求是否来自手机端
	 * 
	 * @return 如果是 手机端  则为true，否则false
	 */
	public final static boolean isMobile(HttpServletRequest request) {
		if(request == null || StringUtils.isBlank(request.getHeader("User-Agent"))){
			return false;
		}
		String ua = request.getHeader("User-Agent").toLowerCase();
		String mobile=".*mobile.*";
		return (ua.matches(mobile)||isAndroid(request)||isIOS(request));
	}
	
	/** 
	 * @Title: getIpAddr  
	 * @Description: 获取客户端IP地址   
	 * @return String    
	 * @throws 
	 */  
	public static String getIpAddr(HttpServletRequest request) {   
		   if(request == null){
			   return null;
		   }
	       String ip = request.getHeader("x-forwarded-for");   
	       if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {   
	           ip = request.getHeader("Proxy-Client-IP");   
	       }   
	       if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {   
	           ip = request.getHeader("WL-Proxy-Client-IP");   
	       }   
	       if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {   
	           ip = request.getRemoteAddr();   
	           if(ip!=null && ip.equals("127.0.0.1")){     
	               //根据网卡取本机配置的IP     
	               InetAddress inet=null;     
	               try {     
	                   inet = InetAddress.getLocalHost();     
	               } catch (UnknownHostException e) {     
	                   e.printStackTrace();     
	               }     
	               ip= inet.getHostAddress();     
	           }  
	       }   
	       // 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割  
	       if(ip != null && ip.length() > 15){    
	           if(ip.indexOf(",")>0){     
	               ip = ip.substring(0,ip.indexOf(","));     
	           }     
	       }     
	       return ip;   
	}  
	
	/** 
     * 全角转半角: 
     * <p>注：空格统一转成32的空格</p>
     * @param fullStr 
     * @return 
     */  
    public static final String full2Half(String fullStr) {  
        if(isEmpty(fullStr)){  
            return fullStr;  
        }  
        char[] c = fullStr.toCharArray();  
        for (int i = 0; i < c.length; i++) {  
            if (c[i] >= 65281 && c[i] <= 65374) {  
                c[i] = (char) (c[i] - 65248);  
            } else if (c[i] == 12288) { // 空格  
                c[i] = (char) 32;  
            } else if(c[i] ==160){ //特殊字符 ‘ ’的转化  
            	c[i] = (char)(32);  
            } else if(c[i] >= 57344 && c[i] <= 57423){ //特殊字符 ‘’、‘’的转化  
            	c[i] = (char)(32);  
            } else if(c[i] ==8226){ //特殊字符 ‘·’的转化  
            	c[i] = (char)(183);  
            }
        }  
        return new String(c);  
    }  
    
    /** 
     * 半角转全角 
     * @param halfStr 
     * @return 
     */  
    public static final String half2Full(String halfStr) {  
        if(isEmpty(halfStr)){  
            return halfStr;  
        }  
        char[] c = halfStr.toCharArray();  
        for (int i = 0; i < c.length; i++) {  
            if (c[i] == 32) {  
                c[i] = (char) 12288;  
            } else if (c[i] < 127) {  
                c[i] = (char) (c[i] + 65248);  
            }  
        }  
        return new String(c);  
    }  
}
