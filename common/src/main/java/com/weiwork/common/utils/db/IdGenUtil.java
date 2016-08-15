package com.weiwork.common.utils.db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.weiwork.common.utils.ApplicationUtil;

/**
 * ID生成工具
 * 
 * @author ychuang328
 *
 */
public class IdGenUtil {
	private static Logger logger = LoggerFactory.getLogger(IdGenUtil.class);

	/**
	 * v8版生成ID
	 * <p>
	 * 	依赖实体：cn.vko.common.utils.dbutils.PrimarykeyGenerator
	 * </p>
	 * 
	 * @return id
	 */
	public static synchronized long genID(String dbName, String tName) {
		PrimarykeyGenerator primarykeyGenerator = (PrimarykeyGenerator) ApplicationUtil.getBean("primarykeyGenerator");
		Long generateId = primarykeyGenerator.generateId(dbName, tName);
		logger.info(String.format("===GenerateID-KEY:===[%s_%s:%s]", dbName, tName, generateId));
		return generateId;
	}
 
	/**************************** 老版（v8之前）生产线上id生成规则  start***********************************/
	private static final int MAX_COUNT = 10000;
	private static final int WIDTH = 4;
	private static int COUNT = 0;

	/** 线上老版（v8之前）ID生成方法 */
	public synchronized static long getId() {
		StringBuilder sb = new StringBuilder();
		sb.append(System.currentTimeMillis());
		COUNT++;
		if (COUNT >= MAX_COUNT) {
			COUNT = COUNT % MAX_COUNT;
		}
		sb.append(alignRight(COUNT, WIDTH, '0'));
		return Long.valueOf(sb.toString());
	}

	private static String alignRight(Object o, int width, char c) {
		if (null == o)
			return null;
		String s = o.toString();
		int len = s.length();
		if (len >= width)
			return s;
		return new StringBuilder().append(dup(c, width - len)).append(s).toString();
	}

	private static String dup(char c, int num) {
		if (c == 0 || num < 1)
			return "";
		StringBuilder sb = new StringBuilder(num);
		for (int i = 0; i < num; i++)
			sb.append(c);
		return sb.toString();
	}
	/**************************** 生产线上id生成规则 end*************************************/
}
