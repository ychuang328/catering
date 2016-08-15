package com.weiwork.common.utils.upload;


public interface ImageUploadOperator extends UploadOperator {
	/**
	 * 把文件从临时目录移动到正式目录
	 * @param from 源文件路径
	 * @param fromOnly 是否只操作源文件
	 * @param params 自定义参数列表，多个参数之间通过"/"拼接成多级目录，
	 * 					拼接后的路径作为要移动到的路径
	 * @return 移动后的路径
	 */
	String moveFtpTempFile(String from,boolean fromOnly,Object... params) throws Exception;
	
	/**
	 * 把文件从临时目录移动到正式目录
	 * @param from 源文件路径
	 * @param fromOnly 是否只操作源文件
	 * @param path  拼接后的路径作为要移动到的路径 用"/"区分目录
	 * @return 移动后的路径
	 */
	String moveFtpTempFile2(String from,boolean fromOnly,String path) throws Exception;
}
