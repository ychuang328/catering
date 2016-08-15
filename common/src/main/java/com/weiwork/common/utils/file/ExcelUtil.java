package com.weiwork.common.utils.file;

import java.io.FileInputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * poi 读取excel 支持2003 --2007 及以上文件
 * 
 */
public class ExcelUtil {


	/**
	 * 合并方法，读取excel文件
	 * 根据文件名自动识别读取方式
	 * 支持97-2013格式的excel文档
	 * 
	 * @param filePath
	 *           文件名路径（包含文件名）
	 * @return 返回列表内容格式：
	 *  每一行数据都是以对应列的表头为key 内容为value 比如 excel表格为：
	 * ===============
	 *  A | B | C | D
	 * ===|===|===|===
	 *  1 | 2 | 3 | 4
	 * ---|---|---|--- 
	 *  a | b | c | d
	 * ---------------
	 * 返回值 map：
	 *   map1:   A:1 B:2 C:3 D:4
	 *   map2:   A:a B:b C:d D:d
	 * @throws java.io.IOException
	 */
	@SuppressWarnings("rawtypes")
	public static List<Map> readExcel(String filePath) throws Exception{
		//准备返回值列表
		List<Map> valueList=new ArrayList<Map>();
		String ExtensionName=getExtensionName(filePath);
		Integer sheetIndex = null;
		if(ExtensionName.equalsIgnoreCase("xls")){
			valueList=readExcel2003(filePath, sheetIndex);
		}else if(ExtensionName.equalsIgnoreCase("xlsx")) {
			valueList=readExcel2007(filePath, sheetIndex);
		}
		return valueList;
		
	}
	
	/**
	 * 合并方法，读取excel文件
	 * 根据文件名自动识别读取方式
	 * 支持97-2013格式的excel文档
	 * 
	 * @param filePath
	 *           文件名路径（包含文件名）
	 * @param sheetIndex 指定读取的表格序号，从0开始；为空null或-1,表示全部读取
	 * @return 返回列表内容格式：
	 *  每一行数据都是以对应列的表头为key 内容为value 比如 excel表格为：
	 * ===============
	 *  A | B | C | D
	 * ===|===|===|===
	 *  1 | 2 | 3 | 4
	 * ---|---|---|--- 
	 *  a | b | c | d
	 * ---------------
	 * 返回值 map：
	 *   map1:   A:1 B:2 C:3 D:4
	 *   map2:   A:a B:b C:d D:d
	 * @throws java.io.IOException
	 */
	@SuppressWarnings("rawtypes")
	public static List<Map> readExcel(String filePath, Integer sheetIndex) throws Exception{
		//准备返回值列表
		List<Map> valueList=new ArrayList<Map>();
		String ExtensionName=getExtensionName(filePath);
		if(ExtensionName.equalsIgnoreCase("xls")){
			valueList=readExcel2003(filePath, sheetIndex);
		}else if(ExtensionName.equalsIgnoreCase("xlsx")) {
			valueList=readExcel2007(filePath, sheetIndex);
		}
		return valueList;
		
	}
	
	
	/**
	 * 合并方法，读取excel文件
	 * 根据文件名自动识别读取方式
	 * 支持97-2013格式的excel文档
	 * 
	 * @param filePath
	 *           文件名路径（包含文件名）
	 * @param sheetIndex 指定读取的表格序号，从0开始；为空null或-1,表示全部读取
	 * @param cellIndex  读取的某表格指定的列序号，从0开始；为空null或-1,表示全部读取
	 * @param rowNum  	   读取的某表格指定的行数，从0开始；为空null或-1,表示全部读取， 10-表示只读取前10行数据
	 * 
	 * @return 返回列表内容格式：
	 *  每一行数据都是以对应列的表头为key 内容为value 比如 excel表格为：
	 * ===============
	 *  A | B | C | D
	 * ===|===|===|===
	 *  1 | 2 | 3 | 4
	 * ---|---|---|--- 
	 *  a | b | c | d
	 * ---------------
	 * 返回值 map：
	 *   map1:   A:1 B:2 C:3 D:4
	 *   map2:   A:a B:b C:d D:d
	 * @throws java.io.IOException
	 */
	@SuppressWarnings("rawtypes")
	public static List<Map> readExcel(String filePath, Integer sheetIndex, Integer cellIndex, Integer rowNum) throws Exception{
		//准备返回值列表
		List<Map> valueList=new ArrayList<Map>();
        String ExtensionName=getExtensionName(filePath);
		if(ExtensionName.equalsIgnoreCase("xls")){
			valueList=readExcel2003(filePath, sheetIndex, cellIndex, rowNum);
		}else if(ExtensionName.equalsIgnoreCase("xlsx")) {
			valueList=readExcel2007(filePath, sheetIndex, cellIndex, rowNum);
		}
        return valueList;
	 			
	}
	
	/**
	 * 读取97-2003格式
	 * @param filePath 文件路径
	 * @param sheetIndex 指定读取的表格序号，从0开始；为空null或-1,表示全部读取
	 * @throws java.io.IOException
	 */
	@SuppressWarnings("rawtypes")
	public static List<Map> readExcel2003(String filePath, Integer sheetIndex) throws IOException{
		return readExcel2003(filePath, sheetIndex, null, null);
	}
	
	/**
	 * 读取97-2003格式
	 * @param filePath 文件路径
	 * @param sheetIndex 指定读取的表格序号，从0开始；为空null或-1,表示全部读取
	 * @param cellIndex  读取的某表格指定的列序号，从0开始；为空null或-1,表示全部读取
	 * @param rowNum  	   读取的某表格指定的行数，从0开始；为空null或-1,表示全部读取， 10-表示只读取前10行数据
	 * @throws java.io.IOException
	 */
	@SuppressWarnings("rawtypes")
	public static List<Map> readExcel2003(String filePath, Integer sheetIndex, Integer cellIndex, Integer rowNum) throws IOException{
		//返回结果集
		List<Map> valueList=new ArrayList<Map>();
        FileInputStream fis=null;
        HSSFWorkbook wookbook = null;
		try {
            fis=new FileInputStream(filePath);
			wookbook = new HSSFWorkbook(fis);	// 创建对Excel工作簿文件的引用
			Map<Integer,String> keys=new HashMap<Integer, String>();
			//while Read the Sheet
    		for (int numSheet = 0; numSheet < wookbook.getNumberOfSheets(); numSheet++) {
    			// 指定读取的表格序号，从0开始；为空null或-1,表示全部读取
    			if(null != sheetIndex && sheetIndex>-1 && sheetIndex.intValue()!=numSheet){
    				continue;
    			}
    			
    			HSSFSheet sheet = wookbook.getSheetAt(numSheet);
				if (sheet == null) {
					continue;
				}
//				int rows = sheet.getPhysicalNumberOfRows();	// 获取到Excel文件中的所有行数­物理行数，也就是不包括那些空行（隔行）的情况.
	            int rows = sheet.getLastRowNum();//获取的是最后一行的编号（编号从0开始)
				int cells=0;
				// 遍历行­（第1行  表头） 准备Map里的key
				HSSFRow firstRow = sheet.getRow(0);
				if (firstRow != null) {
					// 获取到Excel文件中的所有的列
					cells = firstRow.getLastCellNum();
					// 遍历列
					for (int j = 0; j < cells; j++) {
						// 读取的某表格指定的列序号，从0开始；为空null或-1,表示全部读取
		    			if(null != cellIndex && cellIndex>-1 && cellIndex.intValue()>j){
		    				continue;
		    			}
						
						// 获取到列的值­
						try {
							HSSFCell cell = firstRow.getCell(j);
							String cellValue = getCellValue(cell);
							keys.put(j,cellValue);						
						} catch (Exception e) {
							e.printStackTrace();	
						}
					}
				}
				// 遍历行­（从第二行开始）
				for (int i = 1; i < rows; i++) {
					// 读取的某表格指定的行数，从0开始；为空null或-1,表示全部读取， 10-表示只读取前10行数据
	    			if(null != rowNum && rowNum>-1 && rowNum.intValue()<i){
	    				break;
//	    				continue;
	    			}
					
					// 读取左上端单元格(从第二行开始)
					HSSFRow row = sheet.getRow(i);
					// 行不为空
					if (row != null) {
						//准备当前行 所储存值的map
						Map<String, Object> val=new HashMap<String, Object>();
						
						boolean isValidRow = false;
						
						// 遍历列
						for (int j = 0; j < cells; j++) {
							// 读取的某表格指定的列序号，从0开始；为空null或-1,表示全部读取
			    			if(null != cellIndex && cellIndex>-1 && cellIndex.intValue()>j){
			    				continue;
			    			}
							
							// 获取到列的值­
							try {
								HSSFCell cell = row.getCell(j);
								String cellValue = getCellValue(cell);
								val.put(keys.get(j),cellValue.trim());	
								if(!isValidRow && cellValue!=null && cellValue.trim().length()>0){
									isValidRow = true;
								}
							} catch (Exception e) {
								e.printStackTrace();		
							}
						}
						//第I行所有的列数据读取完毕，放入valuelist
						if(isValidRow){
							valueList.add(val);
						}
					}
				}
    		}
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			if(null != wookbook){
				wookbook.close();
			}
			if(null != fis){
				fis.close();
			}
        }
        return valueList;
	}
	
	/**
	 * 读取2007-2013格式
	 * @param filePath 文件路径
	 * @param sheetIndex sheetIndex 指定读取的表格序号，从0开始；为空null或-1,表示全部读取
	 * @return
	 * @throws java.io.IOException
	 */
	@SuppressWarnings("rawtypes")
	public static List<Map> readExcel2007(String filePath, Integer sheetIndex) throws IOException{
		return readExcel2007(filePath, sheetIndex, null, null);
	}
	
	/**
	 * 读取2007-2013格式
	 * @param filePath 文件路径
	 * @param sheetIndex sheetIndex 指定读取的表格序号，从0开始；为空null或-1,表示全部读取
	 * @param cellIndex  读取的某表格指定的列序号，从0开始；为空null或-1,表示全部读取
	 * @param rowNum  	   读取的某表格指定的行数，从0开始；为空null或-1,表示全部读取， 10-表示只读取前10行数据
	 * @return
	 * @throws java.io.IOException
	 */
	@SuppressWarnings("rawtypes")
	public static List<Map> readExcel2007(String filePath, Integer sheetIndex, Integer cellIndex, Integer rowNum) throws IOException{
		List<Map> valueList=new ArrayList<Map>();
        FileInputStream fis =null;
        XSSFWorkbook xwb = null;
        try {
            fis =new FileInputStream(filePath);
            xwb = new XSSFWorkbook(fis);	// 构造 XSSFWorkbook 对象，strPath 传入文件路径
            // 定义 row、cell
            XSSFRow row;
            // 循环输出表格中的第一行内容   表头
            Map<Integer, String> keys=new HashMap<Integer, String>();
            //while Read the Sheet
    		for (int numSheet = 0; numSheet < xwb.getNumberOfSheets(); numSheet++) {
    			// 指定读取的表格序号，从0开始；为空null或-1,表示全部读取
    			if(null != sheetIndex && sheetIndex>-1 && sheetIndex.intValue()!=numSheet){
    				continue;
    			}
    			
				XSSFSheet sheet = xwb.getSheetAt(numSheet);
				if (sheet == null) {
					continue;
				}
				row = sheet.getRow(0);
	            if(row !=null){
	                for (int j = row.getFirstCellNum(); j <=row.getLastCellNum(); j++) {
	                	// 读取的某表格指定的列序号，从0开始；为空null或-1,表示全部读取
		    			if(null != cellIndex && cellIndex>-1 && cellIndex.intValue()>j){
		    				continue;
		    			}
	                	
	                    // 通过 row.getCell(j).toString() 获取单元格内容，
	                    if(row.getCell(j)!=null){
	                        if(!row.getCell(j).toString().isEmpty()){
	                            keys.put(j, row.getCell(j).toString().trim());
	                        }
	                    }else{
	                        keys.put(j, "K-R1C"+j+"E");
	                    }
	                }
	            }
	            // int rows = sheet.getPhysicalNumberOfRows();//获取的是物理行数，也就是不包括那些空行（隔行）的情况.
	            int rows = sheet.getLastRowNum();//获取的是最后一行的编号（编号从0开始
	            // 循环输出表格中的从第二行开始内容
	            for (int i = sheet.getFirstRowNum() + 1; i <= rows; i++) {
	            	// 读取的某表格指定的行数，从0开始；为空null或-1,表示全部读取， 10-表示只读取前10行数据
	    			if(null != rowNum && rowNum>-1 && rowNum.intValue()<i){
	    				break;
//	    				continue;
	    			}
	                row = sheet.getRow(i);
	                if (row != null) {
	                    boolean isValidRow = false;
	                    Map<String, Object> val = new HashMap<String, Object>();
	                    for (int j = row.getFirstCellNum(); j <= row.getLastCellNum(); j++) {
	                    	// 读取的某表格指定的列序号，从0开始；为空null或-1,表示全部读取
			    			if(null != cellIndex && cellIndex>-1 && cellIndex.intValue()>j){
			    				continue;
			    			}
	                    	
	                        XSSFCell cell = row.getCell(j);
	                        if (cell != null) {
	                            String cellValue = null;
	                            if(cell.getCellType()==XSSFCell.CELL_TYPE_NUMERIC){
	                                if(DateUtil.isCellDateFormatted(cell)){
	                                    cellValue = new DataFormatter().formatRawCellContents(cell.getNumericCellValue(), 0, "yyyy-MM-dd HH:mm:ss");
	                                }
	                                else{
	                                    cellValue = String.valueOf(cell.getNumericCellValue());
	                                }
	                            }
	                            else{
	                                cellValue = cell.toString();
	                            }
	                            if(cellValue!=null&&cellValue.trim().length()<=0){
	                                cellValue=null;
	                            }
	                            else{
	                            	cellValue = cellValue.trim();
	                            }
	                            val.put(keys.get(j), cellValue);
	                            if(!isValidRow && cellValue!= null && cellValue.trim().length()>0){
	                                isValidRow = true;
	                            }
	                        }
	                    }
	
	                    // 第i行所有的列数据读取完毕，放入valuelist
	                    if (isValidRow) {
	                        valueList.add(val);
	                    }
	                }
	            }
    		}
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
        	if(null != xwb){
        		xwb.close();
			}
			if(null != fis){
				fis.close();
			}
        }

        return valueList;
	}
	
	/**
	 * 文件操作 获取文件扩展名
	 * 
	 * @Author: sunny
	 * @param filename
	 *            文件名称包含扩展名
	 * @return
	 */
	public static String getExtensionName(String filename) {
		if ((filename != null) && (filename.length() > 0)) {
			int dot = filename.lastIndexOf('.');
			if ((dot > -1) && (dot < (filename.length() - 1))) {
				return filename.substring(dot + 1);
			}
		}
		return filename;
	}

	private static String getCellValue(HSSFCell cell) {
		DecimalFormat df = new DecimalFormat("#");
		String cellValue=null;
		if (cell == null)
			return null;
		switch (cell.getCellType()) {
			case HSSFCell.CELL_TYPE_NUMERIC:
				if(HSSFDateUtil.isCellDateFormatted(cell)){
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					cellValue=sdf.format(HSSFDateUtil.getJavaDate(cell.getNumericCellValue()));
					break;
				}
				cellValue=df.format(cell.getNumericCellValue());
				break;
			case HSSFCell.CELL_TYPE_STRING:			
				cellValue=String.valueOf(cell.getStringCellValue());
				break;
			case HSSFCell.CELL_TYPE_FORMULA:
				cellValue=String.valueOf(cell.getCellFormula());
				break;
			case HSSFCell.CELL_TYPE_BLANK:
				cellValue=null;
				break;
			case HSSFCell.CELL_TYPE_BOOLEAN:
				cellValue=String.valueOf(cell.getBooleanCellValue());
				break;
			case HSSFCell.CELL_TYPE_ERROR:
				cellValue=String.valueOf(cell.getErrorCellValue());
				break;
		}
		if(cellValue!=null&&cellValue.trim().length()<=0){
			cellValue=null;
		}else{
			cellValue=cellValue.trim();
		}
		
		return cellValue;
	}
	
	/**
     * 创建excel文档
     * <p>
     *  List<Map<String, Object>> 第一个map, 需要设置sheetName , 如：map.put("sheetName", "sheet1");
     * 	String columnNames[] = {"ID","项目名","销售人","负责人","所用技术","备注"};//列名
     *  String keys[]        = {"id","name","saler","principal","technology","remarks"};//map中的key
     *  
     *  另外， 用于内存级，下载excel  workbook.write(response.getOutputStream());
     * </p>
     * 
     * @param list 数据
     * @param keys list中map的key数组集合
     * @param columnNames excel的列名
     * */
    public static Workbook createWorkBook(List<Map<String, Object>> list,String []keys,String columnNames[]) {
        // 创建excel工作簿
        Workbook wb = new HSSFWorkbook();
        // 创建第一个sheet（页），并命名
        Sheet sheet = wb.createSheet(list.get(0).get("sheetName").toString());
        // 手动设置列宽。第一个参数表示要为第几列设；，第二个参数表示列的宽度，n为列高的像素数。
        for(int i=0;i<keys.length;i++){
            sheet.setColumnWidth((short) i, (short) (35.7 * 150));
        }

        // 创建第一行
        Row row = sheet.createRow((short) 0);

        // 创建两种单元格格式
        CellStyle cs = wb.createCellStyle();
        CellStyle cs2 = wb.createCellStyle();

        // 创建两种字体
        Font f = wb.createFont();
        Font f2 = wb.createFont();

        // 创建第一种字体样式（用于列名）
        f.setFontHeightInPoints((short) 10);
        f.setColor(IndexedColors.BLACK.getIndex());
        f.setBoldweight(Font.BOLDWEIGHT_BOLD);

        // 创建第二种字体样式（用于值）
        f2.setFontHeightInPoints((short) 10);
        f2.setColor(IndexedColors.BLACK.getIndex());

//        Font f3=wb.createFont();
//        f3.setFontHeightInPoints((short) 10);
//        f3.setColor(IndexedColors.RED.getIndex());

        // 设置第一种单元格的样式（用于列名）
        cs.setFont(f);
        cs.setBorderLeft(CellStyle.BORDER_THIN);
        cs.setBorderRight(CellStyle.BORDER_THIN);
        cs.setBorderTop(CellStyle.BORDER_THIN);
        cs.setBorderBottom(CellStyle.BORDER_THIN);
        cs.setAlignment(CellStyle.ALIGN_CENTER);

        // 设置第二种单元格的样式（用于值）
        cs2.setFont(f2);
        cs2.setBorderLeft(CellStyle.BORDER_THIN);
        cs2.setBorderRight(CellStyle.BORDER_THIN);
        cs2.setBorderTop(CellStyle.BORDER_THIN);
        cs2.setBorderBottom(CellStyle.BORDER_THIN);
        cs2.setAlignment(CellStyle.ALIGN_CENTER);
        //设置列名
        for(int i=0;i<columnNames.length;i++){
            Cell cell = row.createCell(i);
            cell.setCellValue(columnNames[i]);
            cell.setCellStyle(cs);
        }
        //设置每行每列的值
        for (short i = 1; i < list.size(); i++) {
            // Row 行,Cell 方格 , Row 和 Cell 都是从0开始计数的
            // 创建一行，在页sheet上
            Row row1 = sheet.createRow((short) i);
            // 在row行上创建一个方格
            for(short j=0;j<keys.length;j++){
                Cell cell = row1.createCell(j);
                cell.setCellValue(list.get(i).get(keys[j]) == null?" ": list.get(i).get(keys[j]).toString());
                cell.setCellStyle(cs2);
            }
        }
        return wb;
    }

	public static void main(String[] args) {
		try {
			String filePath = "E:\\temp\\工作\\官网\\微课校园\\校园用户导入模板.xlsx";
			// 从excel读取数据
			List<Map> re = ExcelUtil.readExcel(filePath, 0, 0, 1);
			for (int i = 0; i < re.size(); i++) {
				String str = "";
				for(Object o: re.get(i).keySet()){
					str += (String.valueOf(o)+":"+re.get(i).get(o)+"==");
				}
				System.out.println("第"+i+"行数据："+str);
			}
			
			// 读取班级信息
			int index = 0;
			List<Map> classesList = ExcelUtil.readExcel(filePath, 2);
			for (Map map : classesList) {
				String str = "";
				for(Object o: map.keySet()){
					str += (String.valueOf(o)+":"+map.get(o)+"==");
				}
				System.out.println("第"+(index++)+"行数据："+str);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
