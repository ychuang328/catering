package com.weiwork.common.utils.upload;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import com.weiwork.common.utils.ftp.FtpUtil;

/**
 * 上传文件模板
 *
 */
public class UploadTemplate implements UploadOperator {

    /** ftp地址 */
//    @Value("#{configProperties['upload.ftp.host']}")
    String ftpHost;
    /** ftp用户名 */
//    @Value("#{configProperties['upload.ftp.uname']}")
    String ftpUname;
    /** ftp密码 */
//    @Value("#{configProperties['upload.ftp.pwd']}")
    String ftpPwd;
    /** 上传时的本地路径 */
//    @Value("#{configProperties['upload.default.ftp.localPath']}")
    String localPath;
    /** 上传时的ftp路径 */
//    @Value("#{configProperties['upload.default.ftp.remotePath']}")
    String remotePath;
    /** 富文本上传图片的相对路径 */
//    @Value("#{configProperties['upload.richText.relativePath']}")
    String relativePath ;
//    @Value("#{configProperties['upload.richText.accessPath']}")
    String accessPath;
    
    private Logger logger = LoggerFactory.getLogger(this.getClass());
	
    public void setFtpHost(String ftpHost) {
		this.ftpHost = ftpHost;
	}

	public void setFtpUname(String ftpUname) {
		this.ftpUname = ftpUname;
	}

	public void setFtpPwd(String ftpPwd) {
		this.ftpPwd = ftpPwd;
	}

	public void setLocalPath(String localPath) {
		this.localPath = localPath;
	}

	public void setRemotePath(String remotePath) {
		this.remotePath = remotePath;
	}

	public void setRelativePath(String relativePath) {
		this.relativePath = relativePath;
	}

	public void setAccessPath(String accessPath) {
		this.accessPath = accessPath;
	}

	/**
     * 上传文件
     * @param file MultipartFile
     * @param ftpFileName 上传到FTP的文件名
     * @return true/false
     * @throws Exception
     */
	public String upload(MultipartFile file,String ftpFileName) throws Exception {

		File localFile = new File(saveToLocal(file));
		if (FtpUtil.uploadFile(ftpHost,ftpUname, ftpPwd, remotePath,localFile, ftpFileName)) {
			return relativePath + ftpFileName;
		}
		return null;
	}
	
	public String upload(MultipartFile file) throws Exception {
		return this.upload(file,file.getOriginalFilename());
	}
	
	public String upload(File file,String ftpFileName) throws Exception {

//		File localFile = new File(saveToLocal(file));
		if (FtpUtil.uploadFile(ftpHost,ftpUname, ftpPwd, remotePath,file, ftpFileName)) {
			return relativePath + ftpFileName;
		}
		return null;
	}
	
	public String upload(File file) throws Exception {
		return this.upload(file,file.getName());
	}
	
	/**
	 * 保存到本地
	 * @param file MultipartFile
	 * @return 本地文件路径
	 * @throws IOException
	 */
	public String saveToLocal(MultipartFile file) throws IOException {
		String prefix = getFilePrefixName();
		String sub = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
		String fileName = localPath + "/" +  prefix + sub;
		File localFile = new File(fileName);
		file.transferTo(localFile);
		return fileName;
	}
	
	/**
	 * 获取文件名前缀
	 * @return
	 */
    private String getFilePrefixName() {
    	return String.valueOf( new Date().getTime() );
    }

	@Override
	public boolean moveTempFile(String from, String to) throws Exception {
		return FtpUtil.renameFile(ftpHost,ftpUname, ftpPwd, remotePath, from, to);
	}

	@Override
	public boolean delete(String url) throws Exception {
		String ftpPath = url.replace(accessPath, StringUtils.EMPTY).replace(relativePath, StringUtils.EMPTY);
		
		return FtpUtil.delete(ftpHost,ftpUname, ftpPwd, remotePath, ftpPath);
	}
	
	@Override
	public String moveFtpTempFile(final String from,final Object... params) throws Exception {
//		from = relativePath + from;
			StringBuffer moveTo = new StringBuffer();
			for (Object param : params) {
				if (param != null) {
					moveTo.append(param).append("/");
				}
			}
			if (moveTo.length() > 0) {
				String ftpPath = moveTo.append(from).toString();
				boolean moved;
				try {
					moved = moveTempFile(from, ftpPath);
					logger.info(String.format("移动文件:%s|到%s|%s|",from,ftpPath,moved ? "成功" : "失败"));
				} catch (Exception e) {
					e.printStackTrace();
				}
				return ftpPath;
			}
		return null;
	}

	@Override
	public boolean deleteDirectory(String path) throws Exception {
		return FtpUtil.removeDirectory(ftpHost,ftpUname, ftpPwd, remotePath, path);
	}
}
