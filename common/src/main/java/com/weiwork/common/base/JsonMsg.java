package com.weiwork.common.base;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;

import com.google.gson.Gson;

public class JsonMsg implements Serializable{
	private static final long serialVersionUID = 1L;
	/** json code key in map */
	public static final String K_CODE = "code";
	/** json msg key  in map */
	public static final String K_MSG = "msg";
	/** json data key  in map */
	public static final String K_DATA = "data";
	
	/** json success value  in map */
	public static final String V_CODE_SUCCESS = "success";
	/** json error value in map */
	public static final String V_CODE_ERROR = "error";
	
	public static final String V_CODE_NOLOGIN = "nologin";
	public static final String V_MSG_ERROR = "操作失败";
	public static final String V_MSG_SUCCESS = "操作成功";
	public static final String V_MSG_NOLOGIN = "未登录";
	
	private String code = V_CODE_SUCCESS;
	private String msg = V_MSG_SUCCESS;
	private Object data;
	
	public JsonMsg(String code,String msg){
		this.code = code;
		this.msg = msg;
	}
    public JsonMsg(){
	}
    
    /** *
     * 创建一个新的错误消息对象
     * @param msg 错误消息提示
     * @return
     */
    public static JsonMsg newErrMsg(String msg){
    	return new JsonMsg(V_CODE_ERROR,msg);
    }
    
    /** *
     * 创建一个新的成功消息对象
     * @param msg 成功消息提示
     * @return
     */
    public static JsonMsg newSuccessMsg(String msg){
    	return new JsonMsg(V_CODE_SUCCESS,msg);
    }
    
    /** *
     * 创建一个新的未登录消息对象
     * @param msg 未登录消息提示
     * @return
     */
    public static JsonMsg newNologinMsg(String msg){
    	return new JsonMsg(V_CODE_NOLOGIN,msg);
    }
	

	
	/** *
     * 返回一个JsonP的字符串
     * @param callback jsonp的callback名
     * @return Json字符串
     */
    public String toJsonP(String callback){
    	return callback+"("+new Gson().toJson(this)+")";
    }
    
    /**
	 * ajax - jsonp
	 * @param response HttpServletResponse
	 * @param callback jsonp的callback名
     * @throws IOException 
	 */
	public void writeJsonp(HttpServletResponse response, String callback) throws IOException {
		JsonConfig config = new JsonConfig();
		config.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);//禁止循环生产JSON
		JSONObject json = JSONObject.fromObject(this, config);
		String result = json.toString();

		response.setContentType("aplication/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter out;
		out = response.getWriter();
		out.print(callback + "(" + result + ")");
		out.flush();
		out.close();
	}
    
	public String getCode() {
		return code;
	}
	public void setCodeSuccess() {
		this.code = V_CODE_SUCCESS;
	}
	public void setCodeError() {
		this.code = V_CODE_ERROR;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
}
