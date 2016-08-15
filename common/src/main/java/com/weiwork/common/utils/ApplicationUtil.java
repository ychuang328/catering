package com.weiwork.common.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.context.ConfigurableWebApplicationContext;

/**
 * 静态调用applicationContext
 * <p>
 * @author   杨闯
 * @Date	 2015-5-12 	 
 */
public class ApplicationUtil implements ApplicationContextAware {

	private static ApplicationContext context;

	public static void setApplication(ApplicationContext context) {
		ApplicationUtil.context = context;
	}

	public static <T> T getBean(Class<T> cls) {
		return context.getBean(cls);
	}

	public static Object getBean(String name) {
		return context.getBean(name);
	}

	public static <T> T getBean(String name, Class<T> cls) {
		return context.getBean(name, cls);
	}

	public static boolean containsBean(String name) {
		return context.containsBean(name);
	}

	public static ConfigurableWebApplicationContext getContext() {
		return (ConfigurableWebApplicationContext) context;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		context = applicationContext;
	}
}
