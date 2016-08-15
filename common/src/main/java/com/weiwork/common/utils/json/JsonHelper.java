package com.weiwork.common.utils.json;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;
import net.sf.json.util.CycleDetectionStrategy;


/**
 * 此类提供的静态方法，将任意对象的Long属性转成字符串
 * 返回给前台数据当为long时会出现+1-1的情况，此方法可避免该问题
 *
 */
public class JsonHelper {
	private final static JsonConfig config = new JsonConfig();
	static {
		config.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);//禁止循环生产JSON
		config.registerJsonValueProcessor(Long.class, new LongJsonValueProcessor());
		config.registerJsonValueProcessor(Date.class, new DateJsonValueProcessor());
	}
	
	private JsonHelper(){}
	
	public static String object2JsonStr(Object obj) {
		JSONObject json = JSONObject.fromObject(obj,config);
		return json.toString();
	}
	
	public static String list2JsonString(Collection list) {
		JSONArray jsonArr = JSONArray.fromObject(list, config);
		return jsonArr.toString();
	}
	
	public static JSONObject object2JsonObj(Object obj) {
		JSONObject json = JSONObject.fromObject(obj,config);
		return json;
	}
	
	public static JSONArray list2JsonObj(Collection list) {
		JSONArray jsonArr = JSONArray.fromObject(list, config);
		return jsonArr;
	}
	
	
    private static class LongJsonValueProcessor implements JsonValueProcessor{
		@Override
		public Object processArrayValue(Object value, JsonConfig arg1) {
			if(value == null){
				return "";
			}  
			if (value instanceof Long) {  
	            return value.toString();
	        } 
	        return value; 
		}

		@Override
		public Object processObjectValue(String arg0, Object value,
				JsonConfig arg2) {
			if (value ==null) {
				return "";
			}
			if (value instanceof Long) {  
	            return value.toString();
	        }  
	        return "";  
		}
    	
    }
    
    private static class DateJsonValueProcessor implements JsonValueProcessor{
		@Override
		public Object processArrayValue(Object value, JsonConfig arg1) {
			if(value==null) {
				return "";
			}
			if (value instanceof Date) {  
	            return JSONObject.fromObject(value);
	        }  
	        return value; 
		}

		@Override
		public Object processObjectValue(String arg0, Object value,
				JsonConfig arg2) {
			if(value==null){
				return "";
			}
			if (value instanceof Date) {  
	           return JSONObject.fromObject(value);
	        }  
	        return value;  
		}
    	
    }
    public static void main(String[] args) {
    	List<Long> list=new ArrayList<Long>();
    	list.add(1l);
    	list.add(2l);
    	String str=JsonHelper.list2JsonString(null);
    	
    	System.out.println(str);

		
	}
    
}
