package com.weiwork.common.utils.file;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 操作excel 2007
 * 
 */
public class Excel2007Util {
	private static final Logger logger = LoggerFactory.getLogger(Excel2007Util.class);

	/**
	 * 生成excel内容并写入输出流
	 * 
	 * @param rows 行数据
	 * @param stream 输出流
	 */
	public static void write(final List<List<String>> rows, final OutputStream stream) {
		if (rows == null || rows.isEmpty() || stream == null) {
			return;
		}
		XSSFWorkbook wb = new XSSFWorkbook();
		XSSFSheet sheet = wb.createSheet("sheet1");
		for (int index = 0; index < rows.size(); index++) {
			XSSFRow row = sheet.createRow(index);
			List<String> data = rows.get(index);
			for (int cols = 0; cols < data.size(); cols++) {
				XSSFCell cell = row.createCell(cols);
				cell.setCellType(Cell.CELL_TYPE_STRING);
				cell.setCellValue(data.get(cols));
			}
		}
		try {
			wb.write(stream);
		} catch (IOException e) {
			logger.error("将生成的excel数据写入流时出错", e);
		}
	}

	/**
	 * 生成excel内容并写入文件
	 * 
	 * @param rows 行数据
	 * @param path 文件路径
	 */
	public static void write(final List<List<String>> rows, final String path) {
		if (rows == null || rows.isEmpty()) {
			return;
		}
		FileOutputStream output = null;
		try {
			File excelFile = new File(path);
			if (excelFile.exists()) {
				FileUtil.deleteFile(path);
			}
			FileUtil.createNewFile(excelFile);
			output = new FileOutputStream(new File(path));
			write(rows, output);
		} catch (FileNotFoundException e) {
			logger.error("文件不存在，路径：" + path, e);
		} catch (IOException e) {
			logger.error("写excel文件时出错", e);
		} finally {
			if (output != null) {
				try {
					output.close();
				} catch (IOException e) {
					logger.error("关闭文件流时出错", e);
				}
			}
		}
	}
}
