package com.weiwork.common;

import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.junit.Test;

import com.weiwork.common.utils.ftp.FtpUtil;

/**
 * FTP 上传
 */
public class FtpTest{

	// ftp 连接参数
	private String hostname = "192.168.1.37";
	private int port = 21;
	private String username = "pengfeizhang";
	private String password = "aaaaaa";
	private String remoteDir = "videos";

	// 本地上传路径
	private String localPath = "d:/home/videos";

	private FtpUtil ftp;

	@Test
	public void testUpload() throws Exception {
		assertNotNull("本地文件路径参数空", localPath);

		File dir = new File(localPath);
		if (dir.isDirectory()) {
			File[] fileArray = dir.listFiles();
			if (fileArray.length == 0) {
				System.out.println(String.format("目录下没文件可传输 ", localPath));
				return;
			}
			long start = System.currentTimeMillis();
			for (File file : fileArray) {
				if (checkFile(file)) {
					System.out.println(String.format("准备传输文件 %s",
							file.getName()));

					boolean suc = ftp.upload(file.getAbsolutePath(),
							file.getName());

					System.out.println(String.format("文件 %s 上传结果： %b",
							file.getAbsoluteFile(), suc));
				} else {
					System.out.println(String.format("文件  %s 不需要传输",
							file.getName()));
				}
			}
			long end = System.currentTimeMillis();
			
			System.out.println(end - start);
		}

	}

	/**
	 * 校验是否需要ftp传输
	 * 
	 * @param file
	 *            文件
	 * @return
	 */
	private boolean checkFile(File file) {
		List<Pattern> filePats = new ArrayList<Pattern>();
		filePats.add(Pattern.compile("^uvs[\\d]{6}-[\\d]{3}.mpg$"));
		filePats.add(Pattern.compile("^T[\\d]+(-H)?.mp4$"));

		for (Pattern pat : filePats) {
			if (pat.matcher(file.getName()).find()) {
				return true;
			}
		}

		return false;
	}
	
	@Test
	public void testStaticUploadFile() throws Exception{
		FtpUtil.uploadFile("192.168.1.16", "skyline", "vko999999", "/home/skyline/zpftest", new File("D:\\test.sql"), "test1.sql");
	}
	
	@Test
	public void testRename() throws Exception{
		boolean b = FtpUtil.renameFile("192.168.1.16", "skyline", "vko999999", "/home/skyline/zpftest","test2.sql","test3.sql");
		System.out.println(b);
	}
}
