package com.weiwork.common;

import java.io.File;


public class FileUtilsTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		String tNo = "T12"; 

		String fileName = tNo + "-H.mp4";
		String reviseVideoPath = "/home/skyline/vko_revise/"; // mp4流化可审目录
		String waitenVideoPath = "/home/skyline/vko_envideo/" + tNo;// mp4审核通过，准备转码加密目录
		File srcFile = new File(reviseVideoPath + fileName);
		File destDir = new File(waitenVideoPath);

	}

}
