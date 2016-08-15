package com.weiwork.common.mybatis;

/**
 * 线上老版ID生成工具
 * <p>
 * 目前老版项目使用的ID策略
 * 
 * @author   ychuang328
 * @Date	 2015-11-17 	 
 */
public class GenIdByTimesUtil {
	/**************************** 生产线上id生成规则  ****************************************/
	private static final int MAX_COUNT = 10000;
	private static final int WIDTH = 4;
	private static int COUNT = 0;

	public synchronized long getId() {
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
	
	public static void main(String[] args) {
		GenIdByTimesUtil onlineGenIdUtil = new GenIdByTimesUtil();
		System.out.println(onlineGenIdUtil.getId());
		System.out.println(onlineGenIdUtil.getId());
		System.out.println(new GenIdByTimesUtil().getId());
	}
}

