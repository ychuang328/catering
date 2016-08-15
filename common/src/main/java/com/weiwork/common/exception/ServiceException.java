package com.weiwork.common.exception;

/**
 * 业务层异常
 * @author 杨闯
 * 
 */
@SuppressWarnings("serial")
public class ServiceException extends RuntimeException {
	private int errCode;
	private String errMsg;

	public ServiceException(int errCode, String errMsg) {
		super(errMsg);
		this.errCode = errCode;
		this.errMsg = errMsg;
	}

	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

	public int getErrCode() {
		return errCode;
	}

	public void setErrCode(int errCode) {
		this.errCode = errCode;
	}

	@Override
	public Throwable fillInStackTrace() {
//		System.out.println("--------------");
		return this;
	}
	
//	Exception in thread "main" cn.vko.common.exception.ServiceException: aaa
//	at cn.vko.common.exception.ServiceException.main(ServiceException.java:42)
	
	public static void main(String[] args) {
		Long start = System.currentTimeMillis();
		for(int i = 0; i < 1000000; i++){
			new ServiceException(1,"aaa");
		}
		Long end = System.currentTimeMillis();
		System.out.println(end - start);
		
	}
}
