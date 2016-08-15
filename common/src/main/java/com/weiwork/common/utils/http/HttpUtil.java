package com.weiwork.common.utils.http;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * http调用封装
 */
public final class HttpUtil {

	private static Logger log = LoggerFactory.getLogger(HttpUtil.class);

	public static String sendGet(String url) throws IOException {
		return sendGet(url, HttpParam.init());
	}

	public static String sendGet(String url, HttpParam hp) throws IOException {
		// 设置通用参数
		StringBuilder params = new StringBuilder();
		if (hp.hasCommon()) {
			params.append(url).append("?");
			for (Entry<String, String> apm : hp.getCommonParams().entrySet()) {
				params.append(apm.getKey()).append("=").append(apm.getValue()).append("&");
			}
			url = params.substring(0, params.lastIndexOf("&"));
		}
		HttpClient http = new DefaultHttpClient();
		try {
			HttpGet get = new HttpGet(url);
			log.debug("send get with url={}", url);

			// 设置Cookie内容
			if (hp.hasCookie()) {
				StringBuilder cookies = new StringBuilder();
				for (Entry<String, String> acm : hp.getCookieParams().entrySet()) {
					cookies.append(acm.getKey()).append("=").append(acm.getValue()).append(";");
				}
				get.setHeader("Cookie", cookies.toString());
				log.debug("\twith cookie：{}", cookies.toString());
			}
			// 设置指定http-头
			if (hp.hasHeader()) {
				for (Entry<String, String> ahm : hp.getHeaderParams().entrySet()) {
					get.setHeader(ahm.getKey(), ahm.getValue());
					log.debug("\twith header：{}={}", ahm.getKey(), ahm.getValue());
				}
			}

			return EntityUtils.toString(http.execute(get).getEntity(),"utf-8").trim();
		} finally {
			http.getConnectionManager().shutdown();
		}
	}

	public static String sendPost(String url, HttpParam hp) throws IOException {
		HttpClient http = new DefaultHttpClient();
		try {
			HttpPost post = new HttpPost(url);
			log.debug("send post with url={}", url);

			// 设置Cookie内容
			if (hp.hasCookie()) {
				StringBuilder cookies = new StringBuilder();
				for (Entry<String, String> acm : hp.getCookieParams().entrySet()) {
					cookies.append(acm.getKey()).append("=").append(acm.getValue()).append(";");
				}
				post.setHeader("Cookie", cookies.toString());
				log.debug("\twith cookie：{}", cookies.toString());
			}
			// 设置指定http-头
			if (hp.hasHeader()) {
				for (Entry<String, String> ahm : hp.getHeaderParams().entrySet()) {
					post.setHeader(ahm.getKey(), ahm.getValue());
					log.debug("\twith header：{}={}", ahm.getKey(), ahm.getValue());
				}
			}
			// 设置通用参数
			List<NameValuePair> paramList = new ArrayList<NameValuePair>();
			if (hp.hasCommon()) {
				for (Entry<String, String> apm : hp.getCommonParams().entrySet()) {
					paramList.add(new BasicNameValuePair(apm.getKey(), apm.getValue()));
					log.debug("\twith param：{}={}", apm.getKey(), apm.getValue());
				}
			}

			post.setEntity(new UrlEncodedFormEntity(paramList, "UTF-8"));
			return EntityUtils.toString(http.execute(post).getEntity()).trim();
		} finally {
			http.getConnectionManager().shutdown();
		}
	}

	/**
	 * 任何异常，调用方需自行处理
	 * 
	 * @param url post地址
	 * @param param 请求参数
	 * @return 正常响应
	 * @throws Exception 自行处理
	 */
	public static String sendPost(String url, String param) throws Exception {
		byte[] data = param.getBytes();
		DataOutputStream outs = null;
		try {
			URL javaUrl = new URL(url);
			HttpURLConnection http = (HttpURLConnection) javaUrl.openConnection();
			http.setConnectTimeout(10 * 1000);
			http.setReadTimeout(20 * 1000);
			http.setRequestMethod("POST");
			http.setDoOutput(true);// 发送POST请求必须设置允许输出
			http.setUseCaches(false);// 不适用cache
			http.setRequestProperty("Connection", "Keep-Alive");
			http.setRequestProperty("Charset", "utf-8");
			http.setRequestProperty("Content-Length", String.valueOf(data.length));
			http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			http.setRequestProperty("User-Agent",
					"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.149 Safari/537.36");
			outs = new DataOutputStream(http.getOutputStream());
			outs.write(data);
			outs.flush();
			if (http.getResponseCode() == 200) {
				return new String(parseStream(http.getInputStream()), "utf-8");
			}
		} finally {
			if (outs != null) {
				outs.close();
			}
		}
		return null;
	}

	private static byte[] parseStream(InputStream ins) throws Exception {
		int len = -1;
		byte[] data = null;
		byte[] buffer = new byte[1024];
		ByteArrayOutputStream outs = null;
		try {
			outs = new ByteArrayOutputStream();
			while ((len = ins.read(buffer)) != -1) {
				outs.write(buffer, 0, len);
			}
			outs.flush();
			data = outs.toByteArray();
		} finally {
			if (outs != null) {
				outs.close();
			}
		}
		return data;
	}

	/**
	 * 从Cookie提取token信息
	 * 
	 * @param heads 响应头数组
	 * @return
	 */
	public static String pickTokenFromCookie(final Header[] heads) {
		for (Header aHeader : heads) {
			log.info("Cookie ： {}", aHeader.getValue());
			if (aHeader.getValue().contains(HttpParam.COOKIE_TOKEN)) {
				for (String avp : aHeader.getValue().split(";")) {
					if (avp.contains(HttpParam.COOKIE_TOKEN)) {
						return avp.split("=")[1];
					}
				}
			}
		}
		return "";
	}

}
