package com.weiwork.common.utils.file;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 文件工具类
 */
public class FileUtil {
	/**
	* Logger for this class
	*/
	private static final Logger logger = LoggerFactory.getLogger(FileUtil.class);

	/**
	 * 校验文件是否存在
	 * @param file 文件路径
	 * @return 存在为真。如果路径为空，则为假
	 */
	public static boolean isExist(final String file) {
		if (file == null || file.length() == 0) {
			return false;
		}
		File f = new File(file);
		return f.exists();
	}

	/**  
	 * 拷贝文件到指定文件,如果指定文件不存在,自动生成文件; 如果指定文件存在,则自动覆盖该文件. 该方法能自动捕获异常并输出(使用log4j)  
	 *   
	 * @param srcFileName  
	 *            源文件  
	 * @param tagFileName  
	 *            目标文件(指定文件)  
	 */
	public static boolean copyFile(final String srcFileName, final String tagFileName) {
		File in = new File(srcFileName);
		File out = new File(tagFileName);
		FileInputStream inputStream = null;
		FileChannel inChannel = null;
		MappedByteBuffer inBuffer = null;
		long inputSize = 0;
		FileChannel outChannel = null;
		FileOutputStream outputStream = null;
		try {
			if (!out.exists()) {
				try {
					out.createNewFile();
				} catch (IOException e) {
					logger.error("错误:文件" + tagFileName + "创建失败!", e);
					return false;
				}
			}

			inputStream = new FileInputStream(in);
			inChannel = inputStream.getChannel();
			inputSize = inChannel.size();
			inBuffer = inChannel.map(FileChannel.MapMode.READ_ONLY, 0, inputSize);

			// 使用通道方式复制文件   
			outputStream = new FileOutputStream(out);
			outChannel = outputStream.getChannel();
			outChannel.write(inBuffer);
			// 关闭相关对象   
		} catch (FileNotFoundException e) {
			logger.error("错误:文件" + srcFileName + "未找到!", e);
			return false;
		} catch (IOException e2) {
			logger.error("错误:文件操作出错!", e2);
			return false;
		} finally {
			try {
				if (inChannel != null) {
					inChannel.close();
				}
				if (inputStream != null) {
					inputStream.close();
				}
				if (outChannel != null) {
					outChannel.close();
				}
				if (outputStream != null) {
					outputStream.close();
				}
			} catch (IOException e) {
				logger.error("错误:文件流关闭失败!", e);
				return false;
			}

		}
		return true;
	}

	/**
	 * 创建文件夹
	 * @param dir 文件夹路径
	 * @return 是否创建成功
	 */
	public static boolean makeDir(final File dir) {
		if (null == dir || dir.exists()) {
			return false;
		}
		return dir.mkdirs();
	}

	/**
	 * 创建新文件
	 * @param f 文件路径
	 * @return 是否创建成功
	 * @throws IOException 创建时出错
	 */
	public static boolean createNewFile(final File f) throws IOException {
		if (null == f) {
			return false;
		}
		if (f.exists()) {
			f.delete();
		}
		makeDir(f.getParentFile());
		return f.createNewFile();
	}

	/**
	 * 删除文件
	 * @param file 文件路径
	 * @return 是否删除成功
	 */
	public static boolean deleteFile(final String file) {
		File f = new File(file);
		return f.delete();
	}

	/**
	 * 获取路径中的文件名
	 * @param pathName	路径
	 * @return			文件名
	*/
	public static String getFileName(final String pathName) {
		if (pathName.lastIndexOf("\\") > -1) {
			return pathName.substring(pathName.lastIndexOf("\\") + 1);
		} else if (pathName.lastIndexOf("/") > -1) {
			return pathName.substring(pathName.lastIndexOf("/") + 1);
		}
		return pathName;
	}

	/**
	 * 获取路径中的路径
	 * @param pathName  	路径
	 * @return 	路径
	*/
	public static String getFilePath(final String pathName) {
		if (pathName.indexOf(File.separator) > -1) {
			return pathName.substring(0, pathName.lastIndexOf(File.separator));
		}
		return "";
	}

	public static BufferedInputStream bis(final File f) {
		if (f.exists()) {
			try {
				return new BufferedInputStream(new FileInputStream(f));
			} catch (FileNotFoundException e) {
				//never happen!
			}
		}
		return null;
	}

	public static BufferedOutputStream bos(final File f) {
		if (f.exists()) {
			try {
				return new BufferedOutputStream(new FileOutputStream(f));
			} catch (FileNotFoundException e) {
				//never happen!
			}
		}
		return null;
	}

	/**
	 * 管道方式复制文件
	 * <p>
	 * 支持超大文件复制
	 * @param src 源文件路径 
	 * @param dest 目标文件路径
	 */
	public static void fileChannelCopy(final String src, final String dest) {
		try {
			File in = new File(src);
			if (!in.exists()) {
				return;
			}
			File out = new File(dest);
			if (!out.exists()) {
				createNewFile(out);
			}
			FileInputStream fin = new FileInputStream(in);
			FileOutputStream fout = new FileOutputStream(out);
			FileChannel inc = fin.getChannel();
			FileChannel outc = fout.getChannel();
			int bufferLen = 2097152;
			ByteBuffer bb = ByteBuffer.allocateDirect(bufferLen);
			while (true) {
				int ret = inc.read(bb);
				if (ret == -1) {
					fin.close();
					fout.flush();
					fout.close();
					break;
				}
				bb.flip();
				outc.write(bb);
				bb.clear();
			}
		} catch (Exception e) {
			logger.error("复制文件失败", e);
		}
	}
}
