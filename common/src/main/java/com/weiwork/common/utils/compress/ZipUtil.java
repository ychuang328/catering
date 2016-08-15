package com.weiwork.common.utils.compress;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;

import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.Expand;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;

import com.weiwork.common.utils.Util;
 
/**
 * 文件夹压缩，支持win和linux
 * 
 * @author ychuang328
 *
 */
public class ZipUtil
{
    /**
     * @param inputFileName
     *            输入一个文件夹
     * @param zipFileName
     *            输出一个压缩文件夹，打包后文件名字
     * @throws Exception
     */
    public static OutputStream zip(String inputFileName, String zipFileName) throws Exception
    {
        return zip(zipFileName, new File(inputFileName));
    }
 
    private static OutputStream zip(String zipFileName, File inputFile) throws Exception
    {
        ZipOutputStream out = new ZipOutputStream(new FileOutputStream(
                zipFileName));
        out.setEncoding("UTF-8");//解决linux乱码  根据linux系统的实际编码设置,可以使用命令 vi/etc/sysconfig/i18n 查看linux的系统编码
        zip(out, inputFile, "");
        out.close();
        return out;
    }
 
    private static void zip(ZipOutputStream out, File f, String base) throws Exception
    {
        if (f.isDirectory())
        { // 判断是否为目录
            File[] fl = f.listFiles();
            // out.putNextEntry(new org.apache.tools.zip.ZipEntry(base + "/"));
            // out.putNextEntry(new ZipEntry(base + "/"));
            ZipEntry zipEntry=new ZipEntry(base + System.getProperties().getProperty("file.separator"));
            zipEntry.setUnixMode(755);//解决linux乱码
            out.putNextEntry(zipEntry);
            // base = base.length() == 0 ? "" : base + "/";
            base = base.length() == 0 ? "" : base + System.getProperties().getProperty("file.separator");
            for (int i = 0; i < fl.length; i++)
            {
                zip(out, fl[i], base + fl[i].getName());
            }
        }
        else
        { // 压缩目录中的所有文件
            // out.putNextEntry(new org.apache.tools.zip.ZipEntry(base));
            ZipEntry zipEntry=new ZipEntry(base);
            zipEntry.setUnixMode(644);//解决linux乱码
            out.putNextEntry(zipEntry);
            FileInputStream in = new FileInputStream(f);
            int b;
            while ((b = in.read()) != -1)
            {
                out.write(b);
            }
            in.close();
        }
    }
 
    private static void unzip(String sourceZip,String destDir, String encode) throws Exception{  
    	
    	try{  
    		Project p = new Project();  
    		Expand e = new Expand();  
    		
    		e.setProject(p);  
    		e.setSrc(new File(sourceZip));  
    		e.setOverwrite(false);  
    		e.setDest(new File(destDir));  
    		
    		/* 
            ant下的zip工具默认压缩编码为UTF-8编码， 
		            而winRAR软件压缩是用的windows默认的GBK或者GB2312编码 
		            所以解压缩时要制定编码格式 
    		 */ 
    		
    		if(encode == null || encode.length()==0){
    			encode = "UTF-8";
    		}
    		
    		e.setEncoding(encode);  //根据linux系统的实际编码设置
    		e.execute();  
    		
    	}catch(Exception e){  
    		throw e;  
    	}  
    	
    }
    
    // linux 解压-（utf-8）, window 解压-（gbk）
    public static void unzip(String sourceZip,String destDir) throws Exception{  
    	if(Util.isWinOs()){
    		unzipToGBK(sourceZip, destDir);
    		return ;
    	}
    	unzipToUTF8(sourceZip, destDir);
    }
    
    // linux 解压-（utf-8）
    public static void unzipToUTF8(String sourceZip,String destDir) throws Exception{  
    	unzip(sourceZip, destDir, null);
    }
    
    // window 解压-（gbk）
    public static void unzipToGBK(String sourceZip,String destDir) throws Exception{  
    	unzip(sourceZip, destDir, "GBK");
    }
    
    // 测试
    public static void main(String[] args) {
    	String rootPathZip = "C:\\Users\\ychuang328\\Pictures\\temp";
		try {
			// 在window下解压
			ZipUtil.unzipToGBK(rootPathZip+"\\圆与圆的位置关系+赵丽岩.zip", rootPathZip);
			// 在linux下解压
			// ZipUtil.unzip(rootPathZip+"\\圆与圆的位置关系+赵丽岩.zip", rootPathZip);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}