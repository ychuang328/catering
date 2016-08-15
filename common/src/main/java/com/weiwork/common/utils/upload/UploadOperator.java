package com.weiwork.common.utils.upload;

import java.io.File;
import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public interface UploadOperator {
	public String upload(MultipartFile file,String ftpFileName) throws Exception;
	
	public String upload(MultipartFile file) throws Exception;
	
	public String saveToLocal(MultipartFile file) throws IOException;
	
	public String upload(File file,String ftpFileName) throws Exception;
	
	public String upload(File file) throws Exception;
	
	/**
	 * 把文件从临时目录移动到正式目录
	 * @param from
	 * @param to
	 * @return 移动后的路径
	 */
	boolean moveTempFile(String from,String to) throws Exception;
	
	/**
	 * 删除ftp文件
	 * @param ftpPath
	 * @return
	 */
	boolean delete(String ftpPath) throws Exception;
	
	/**
	 * 把文件从临时目录移动到正式目录
	 * @param from 源文件路径
	 * @param params 自定义参数列表，多个参数之间通过"/"拼接成多级目录，
	 * 					拼接后的路径作为要移动到的路径
	 * @return 移动后的路径
	 */
	String moveFtpTempFile(String from,Object... params) throws Exception;
	
	/**
	 * 删除指定路径下的所有文件
	 * @param path
	 * @return
	 * @throws Exception
	 */
	boolean deleteDirectory(String path) throws Exception;
}
