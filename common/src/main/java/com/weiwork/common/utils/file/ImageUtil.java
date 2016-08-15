package com.weiwork.common.utils.file;


import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

import javax.imageio.ImageIO;

import org.apache.commons.io.IOUtils;


/**
 * 图片工具类
 * <p>
 * 用于图片剪切和缩放
 * 新增：下载网络图片功能
 * 
 */
public class ImageUtil {
	public static void resizeImage(InputStream is, OutputStream os, int newWidth,int newHeight, String format) throws IOException {  
	    BufferedImage prevImage = ImageIO.read(is);  
	    
	    BufferedImage image = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_BGR);  
	    Graphics graphics = image.createGraphics();  
	    graphics.drawImage(prevImage, 0, 0, newWidth, newHeight, null);  
	    ImageIO.write(image, format, os);  
	    os.flush();  
	    is.close();  
	    os.close();  
	}  
	
	
	
	/**
	 * 裁剪页面
	 * @param srcFile 源图片
	 * @param destFile 目的图片
	 * @param x x位置
	 * @param y y位置
	 * @param width 宽度
	 * @param height 高度
	 * @param formatName 图片格式化类别
	 */
	public static void cut(final File srcFile, final String destFile, final int x, final int y, final int width,
			final int height, final String formatName) {
		BufferedImage image = read(srcFile);

		// 如果源图片的宽度或高度小于目标图片的宽度或高度，则直接返回出错
		if (image.getWidth() < width || image.getHeight() < height) {
			throw new RuntimeException("图片宽度或高度小于设定的宽高！");
		}
		BufferedImage destImage = image.getSubimage(x, y, width, height);
		write(destImage, destFile, formatName);
	}

	private static BufferedImage read(final File srcFile) {
		BufferedImage image = null;
		try {
			image = ImageIO.read(srcFile);
		} catch (IOException e) {
			throw new RuntimeException("图片读取错误！", e);
		}
		if (image == null) {
			throw new RuntimeException("图片读取错误！");
		}
		return image;
	}

	public static void zoom(final File srcFile, final String destFile, final int width, final int height,
			final String formatName) {
		BufferedImage image = read(srcFile);
		BufferedImage destImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB); // 缩放图像
		Image zoomedImage = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		Graphics g = destImage.getGraphics();
		g.drawImage(zoomedImage, 0, 0, null); // 绘制目标图
		g.dispose();
		write(destImage, destFile, formatName);
	}

	public static void write(final BufferedImage image, final String fileName, final String formatName) {
		// 写文件
		FileOutputStream out = null;
		try {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			ImageIO.write(image, formatName, bos);// 输出到bos
			//Files.createParentDirs(fileName);
			out = new FileOutputStream(fileName);
			out.write(bos.toByteArray()); // 写文件
		} catch (IOException e) {
			new RuntimeException("文件保存失败", e);
		} finally {
			try {
				if (out != null) {
					out.close();
				}
			} catch (IOException e) {

			}
		}
	}
	
	/**
	 * 下载网络图片
	 * 
	 * @param netUrl 网络图片URL (全路径)
	 * @param localRootPath 需要下载到的本地路径 （如：/home/static/content/tk/150w）
	 */
	public static void downloadImgByURL(String netUrl, String localRootPath){
		InputStream in = null;
		OutputStream out = null;
		try {
			File f = new File(localRootPath+(netUrl.replace("http://", "").substring(netUrl.replace("http://", "").indexOf("/"))));
			if (f != null) {
				if (f.exists()) {
					return;
				}
				URL url = new URL(netUrl);
				in = url.openStream();
				out = new FileOutputStream(f);
				IOUtils.copy(in, out);
				out.flush();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * 下载网络图片
	 * 
	 * @param netUrl 网络图片URL (全路径)
	 * @param File 需要下载到的本地文件路径 （如：/home/static/content/tk/150w/1.jpg）
	 */
	public static void downloadImgByURL(String netUrl, File f){
		InputStream in = null;
		OutputStream out = null;
		try {
			if (f != null) {
				if (f.exists()) {
					return;
				}
				URL url = new URL(netUrl);
				in = url.openStream();
				out = new FileOutputStream(f);
				IOUtils.copy(in, out);
				out.flush();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
