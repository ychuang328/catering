package com.weiwork.common.utils.upload;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import com.sun.image.codec.jpeg.ImageFormatException;
import com.weiwork.common.utils.ftp.FtpUtil;
import com.weiwork.common.utils.img.ScaleImageUtils;

//@Component
public class ImageUploadTemplate extends UploadTemplate implements ImageUploadOperator {
	static final String IMAGE_SUFFIX = ".jpg";
	
    private Logger logger = LoggerFactory.getLogger(this.getClass());
	/**
	 * 需要几种分辨率的图片
	 */
	private List<ResizeAttr> resizeAttrs = new ArrayList<ResizeAttr>();
	
	public List<ResizeAttr> getResizeAttrs() {
		return resizeAttrs;
	}

	public void setResizeAttrs(List<ResizeAttr> resizeAttrs) {
		this.resizeAttrs = resizeAttrs;
	}

	//	static {
//		resizeAttrs.add(new ResizeAttr(200, 200, "_thumb"));
//		resizeAttrs.add(new ResizeAttr(1920, 1920, "_max"));
//	}
	/**
     * 上传文件
     * @param file MultipartFile
     * @return ftp路径
     * @throws Exception
     */
	public String upload(MultipartFile file) throws Exception {
		File localOriginalFile = new File(saveToLocal(file));
		//处理原图
		boolean result = doOriginal(localOriginalFile);
		logger.info("上传图片：" + localOriginalFile.getName() + "," + (result ? "成功" : "失败"));
		if (result) {//生成不同分辨率的图片并上传
			doResize(localOriginalFile);
		}
		return relativePath + localOriginalFile.getName();
	}
	
	/**
	 * 保存到本地
	 * @param file MultipartFile
	 * @return 本地文件路径
	 * @throws IOException
	 */
	public String saveToLocal(MultipartFile file) throws IOException {
		String prefix = getFilePrefixName();
		String fileName = localPath + "/" +  prefix + IMAGE_SUFFIX;
		return ScaleImageUtils.resize(1,fileName, file.getInputStream());
	}
	
	/**
	 * 获取文件名前缀
	 * @return
	 */
    private String getFilePrefixName() {
    	long fileName = new Date().getTime();
    	logger.info("生成文件名：" + fileName);
    	return String.valueOf(fileName);
    }

	/**
	 * 处理原图
	 * @param file 上传的原图
	 * @param ftpFileName 在ftp上存放的名称
	 * @return
	 */
	private boolean doOriginal(File file, String ftpFileName) throws Exception {
		return FtpUtil.uploadFile(ftpHost,ftpUname, ftpPwd, remotePath,file, ftpFileName);
	}
	
	private boolean doOriginal(File file) throws Exception {
		return doOriginal(file, file.getName());
	}

	/**
	 * 根据原图生成不同分辨率的图片
	 * @param originalFile
	 * @return
	 */
	private boolean doResize(final File originalFile) throws Exception {
//		new Thread(){
//			@SuppressWarnings("restriction")
//			public void run() {
				for (ResizeAttr arrt : resizeAttrs) {
					
					String localResizeFile;
					try {
						localResizeFile = ScaleImageUtils.resize(arrt.getWidth(), arrt.getWidth(),
								getResizeImgName(originalFile.getPath(),arrt.getSuffix()), originalFile);
						
						File localFile = new File(localResizeFile);
						if (localFile.exists()) {
							boolean success = FtpUtil.uploadFile(ftpHost,ftpUname, ftpPwd, remotePath,localFile, localFile.getName());
							logger.info("上传图片：" + localFile.getName() + "," + (success ? "成功" : "失败"));
						}
					} catch (ImageFormatException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
//			}
//		}.start();
		return true;
	}
	
	private String getResizeImgName(String originalFile,String suffix) {
		String ext = originalFile.substring(originalFile.lastIndexOf("."));
		originalFile = originalFile.replace(ext, StringUtils.EMPTY);
		return originalFile + suffix + ext;
	}

	@Override
	public String moveFtpTempFile(final String from,final boolean fromOnly, final Object... params) throws Exception {
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
						if (moved) {
							String sourceFile = from.substring(from.lastIndexOf("/") + 1);
							String toPath = moveTo.substring(0, moveTo.lastIndexOf("/"));
							if(!fromOnly) {
								moveFtpResizeFile(null,toPath,sourceFile);
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
					return ftpPath;
				}
		return null;
	}
	
	
	public String moveFtpTempFile2(String from,boolean fromOnly,String path) throws Exception{
		if(!StringUtils.isEmpty(path)){
			StringBuffer moveTo = new StringBuffer();
			moveTo.append(path);
			String ftpPath = moveTo.append(from).toString();
			boolean moved;
			try {
				moved = moveTempFile(from, ftpPath);
				logger.info(String.format("移动文件:%s|到%s|%s|",from,ftpPath,moved ? "成功" : "失败"));
				if (moved) {
					String sourceFile = from.substring(from.lastIndexOf("/") + 1);
					String toPath = moveTo.substring(0, moveTo.lastIndexOf("/"));
					if(!fromOnly) {
						moveFtpResizeFile(null,toPath,sourceFile);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return ftpPath;
		}
		return null;
	}
	
	
	/**
	 * 根据fromPath移动Resize的文件到目标路径下，文件名不变
	 * @param fromPath
	 * @param moveToPath
	 * @throws Exception 
	 */
	private void moveFtpResizeFile(final String fromPath,final String moveToPath,final String sourceFileName) throws Exception {
		new Thread(){
			public void run() {
				String prefix = sourceFileName.substring(0,sourceFileName.lastIndexOf("."));
				String suffix = sourceFileName.replace(prefix, StringUtils.EMPTY);
				for (ResizeAttr attr : resizeAttrs) {
//					String fromFile = fromPath + sourceFileName;
					String fromFile = sourceFileName.replace(suffix, StringUtils.EMPTY) + attr.getSuffix() + suffix;
					String toFile = moveToPath + "/" + prefix + attr.getSuffix() + suffix;
					boolean moved;
					try {
						moved = moveTempFile(fromFile, toFile);
						logger.info(String.format("移动文件:%s|到%s|%s|",fromFile,toFile,moved ? "成功" : "失败"));
					} catch (Exception e) {
						e.printStackTrace();
					}
				}

			}
		}.start();
	}

}
