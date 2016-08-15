package com.weiwork.common.net;

import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 网络资源监测
 */
public class WebCheck {

	/**
	 * 网络资源是否正常
	 * 
	 * @param url 网络资源地址
	 * @return
	 */
	public static boolean is200(final String url) {
		HttpURLConnection con = null;
		try {
			con = (HttpURLConnection) new URL(url).openConnection();
			return (con.getResponseCode() == 200);
		} catch (Exception e) {
			return false;
		} finally {
			if (con != null) {
				con.disconnect();
			}
		}
	}

}
