package com.weiwork.common.utils.ftp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;


/**
 * FTP 工具类
 * 
 * @date 2016-02-09
 */
public class FtpUtil {

	// org.apache.commons.net.ftp.FTPClient
	private FTPClient ftpClient = null;

	private int port = 21; // 默认端口
	private String host;
	private String user;
	private String passwd;
	private String remoteDir; // 远程目录

	/**
	 * 初始化配置
	 * 
	 * @param host
	 *            ftp服务域名
	 * @param user
	 *            用户
	 * @param passwd
	 *            密码
	 * @param remoteDir
	 *            远程目录
	 */
	public FtpUtil(String host, String user, String passwd, String remoteDir) {
		this.host = host;
		this.user = user;
		this.passwd = passwd;
		this.remoteDir = remoteDir;
		if (remoteDir == null || "".equals(remoteDir)) {
			remoteDir = "/";
		}
	}

	/**
	 * 设置FTP端口
	 * 
	 * @param port
	 *            端口
	 */
	public void setPort(int port) {
		this.port = port;
	}

	/**
	 * 连接FTP服务端
	 * 
	 * @throws IOException
	 */
	public void login() throws Exception {
		ftpClient = new FTPClient();
		ftpClient.configure(getFTPClientConfig());
		ftpClient.setDefaultPort(port);
		ftpClient.setControlEncoding("UTF-8");
		ftpClient.connect(host);
		if (!ftpClient.login(user, passwd)) {
			throw new Exception("FTP login fail ...");
		}
		ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);

		// 切换到默认工作目录
		ftpClient.changeWorkingDirectory(remoteDir);

		// 设置传输缓冲大小-直接影响传输效率：10M
		ftpClient.setBufferSize(1024 * 1024 * 10);
	}

	/**
	 * 获取默认ftp服务配置
	 * 
	 * @return
	 */
	private FTPClientConfig getFTPClientConfig() {
		FTPClientConfig conf = new FTPClientConfig(FTPClientConfig.SYST_UNIX);
		conf.setServerLanguageCode("zh");
		return conf;
	}

	/**
	 * 退出登录 并 关闭FTP客户端连接
	 */
	public void disconnect() {
		try {
			if (ftpClient != null) {
				ftpClient.logout();
				ftpClient.disconnect();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 是否已连接FTP服务
	 * 
	 * @return true-连接
	 */
	public boolean isConnected() {
		if (ftpClient == null) {
			return false;
		}
		return ftpClient.isConnected();
	}

	/**
	 * 上传
	 * 
	 * @param localFile
	 *            本地文件绝对路径
	 * @param remoteFileName
	 *            要存放到ftp服务端的文件名称
	 * @return true-上传成功
	 * 
	 * @throws Exception
	 */
	public boolean upload(String localFile, String remoteFileName)
			throws Exception {
		File file = new File(localFile);
		return upload(file,remoteFileName);
	}
	
	/**
	 * 修改文件名称
	 * 
	 * @param localFile
	 *            本地文件绝对路径
	 * @param remoteFileName
	 *            要存放到ftp服务端的文件名称
	 * @return true-上传成功
	 * 
	 * @throws Exception
	 */
	public boolean renameFile(String oldFileName, String newFileName)
			throws Exception {
		return ftpClient.rename(oldFileName, newFileName);
	}
	
	/**
	 * 上传
	 * 
	 * @param localFile
	 *            本地文件绝对路径
	 * @param remoteFileName
	 *            要存放到ftp服务端的文件名称
	 * @return true-上传成功
	 * 
	 * @throws Exception
	 */
	public boolean upload(File localFile, String remoteFileName)
			throws Exception {
		boolean state = false;
		// 不处理：文件不存在，不是一个文件，文件大小=0
		if (!localFile.exists()) {
			System.out.println("本地文件["+localFile.getAbsolutePath()+"]不存在");
			return state;
		}
		if (!localFile.isFile()) {
			System.out.println("本地文件["+localFile.getAbsolutePath()+"]不是文件");
			return state;
		}
		if (localFile.length() == 0) {
			System.out.println("本地文件["+localFile.getAbsolutePath()+"]内容为空");
			return state;
		}
		
        String remote = remoteFileName;   
        if(remote.contains("/")){   
        	remoteFileName = remote.substring(remote.lastIndexOf("/")+1);   
            //创建服务器远程目录结构，创建失败直接返回   
            if(!createDirecroty(remote, ftpClient)){   
                return false;   
            }   
        }   
		
		FileInputStream in = new FileInputStream(localFile);
		try {
			state = this.upload(in, remoteFileName);
		} finally {
			in.close();
		}
		return state;
	}


	/**
	 * 上传
	 * 
	 * @param in
	 *            本地文件输入流
	 * @param remoteFileName
	 *            要存放到ftp服务端的文件名称
	 * @return true-上传成功
	 * @throws Exception
	 */
	public boolean upload(InputStream in, String remoteFileName)
			throws Exception {
		return ftpClient.storeFile(remoteFileName, in);
	}

	/**
	 * 进入工作目录，发送 FTP CWD 命令到FTP服务端
	 * 
	 * @param dir
	 *            工作目录
	 * @return true-成功
	 */
	public boolean cwd(String dir) {
		try {
			return FTPReply.isPositiveCompletion(ftpClient.cwd(dir));
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 切换工作目录
	 * 
	 * @param dir
	 *            工作目录
	 */
	public void changeWorkDir(String dir) {
		try {
			ftpClient.changeWorkingDirectory(dir);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 退回上一级工作目录
	 */
	public void changeParentWorkDir() {
		try {
			ftpClient.changeToParentDirectory();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 创建目录
	 * 
	 * @param dir
	 *            目录名称
	 */
	public void mkdir(String dir) {
		try {
			ftpClient.makeDirectory(dir);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @param host ftp服务器地址
	 * @param uname
	 * @param pwd
	 * @param ftpDir
	 * @param localFile
	 * @throws Exception 
	 */
	public static boolean uploadFile(String host,String uname,String pwd,String ftpDir, File localFile,String remoteFileName) throws Exception{
		FtpUtil ftpUtil = new FtpUtil(host,uname,pwd,ftpDir);
		ftpUtil.login();
		return ftpUtil.upload(localFile, remoteFileName);
	}
	
	/** 
     * 递归创建远程服务器目录 
     * @param remote 远程服务器文件绝对路径 
     * @param ftpClient FTPClient对象 
     * @return 目录创建是否成功 
     * @throws IOException 
     */ 
    public static boolean createDirecroty(String remote,FTPClient ftpClient) throws IOException{   
        boolean status = false;   
        String directory = remote.substring(0,remote.lastIndexOf("/")+1);   
        if(!directory.equalsIgnoreCase("/")&&
//        		!ftpClient.changeWorkingDirectory(new String(directory.getBytes("GBK"),"iso-8859-1")))
        		!ftpClient.changeWorkingDirectory(directory))
        {   
            //如果远程目录不存在，则递归创建远程服务器目录   
            int start=0;   
            int end = 0;   
            if(directory.startsWith("/")){   
                start = 1;   
            }else{   
                start = 0;   
            }
            end = directory.indexOf("/",start);   
            while(true){   
                String subDirectory = new String(remote.substring(start,end));
//                		.getBytes("GBK"),"iso-8859-1");   
                if(!ftpClient.changeWorkingDirectory(subDirectory)){   
                    if(ftpClient.makeDirectory(subDirectory)){   
                        ftpClient.changeWorkingDirectory(subDirectory);   
                    }else {   
                        System.out.println("创建目录失败");   
                        return status;   
                    }   
                }   
                
                start = end + 1;   
                end = directory.indexOf("/",start);   
                   
                //检查所有目录是否创建完毕   
                if(end <= start){   
                    break;   
                }   
            }   
        }   
        status = true;
        return status;   
    }   
    
	/**
	 * 重命名文件
	 * @param host ftp服务器地址
	 * @param uname
	 * @param pwd
	 * @param ftpDir
	 * @param localFile
	 * @throws Exception 
	 */
	public static boolean renameFile(String host,String uname,String pwd,String ftpDir, String oldFileName,String newFileName) throws Exception{
		FtpUtil ftpUtil = new FtpUtil(host,uname,pwd,ftpDir);
		ftpUtil.login();
        String remote = newFileName;   
        if(remote.contains("/")){   
//        	newFileName = remote.substring(remote.lastIndexOf("/")+1);   
            //创建服务器远程目录结构，创建失败直接返回   
            if(!createDirecroty(remote, ftpUtil.ftpClient)){   
                return false;   
            }   
            int level = remote.split("/").length - 1;
            while (level-- > 0) {
            	ftpUtil.ftpClient.changeToParentDirectory();
            }
        }
        
		return ftpUtil.renameFile(oldFileName, newFileName);
	}

	public static boolean delete(String host,String uname,String pwd,String ftpDir,String filePath) throws Exception{
		FtpUtil ftpUtil = new FtpUtil(host,uname,pwd,ftpDir);
		ftpUtil.login();
       
		return ftpUtil.ftpClient.deleteFile(filePath);
	}
	
	private static boolean removeDirectory(String pathName,FTPClient ftp) {
		
		try
        {
            FTPFile[] files = ftp.listFiles(pathName);
            if (null != files && files.length > 0)
            {
                for (FTPFile file : files)
                {
                    if (file.isDirectory())
                    {
                    	removeDirectory(pathName + "/" + file.getName(),ftp);
                        
                        // 切换到父目录，不然删不掉文件夹
                        ftp.changeWorkingDirectory(pathName.substring(0, pathName.lastIndexOf("/")));
                        ftp.removeDirectory(pathName);
                    }
                    else
                    {
                        if (!ftp.deleteFile(pathName + "/" + file.getName()))
                        {
                            return true;
                        }
                    }
                }
            }
            // 切换到父目录，不然删不掉文件夹
            ftp.changeWorkingDirectory(pathName.substring(0, pathName.lastIndexOf("/")));
            ftp.removeDirectory(pathName);
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return false;
        }
		
		return true;
	}
	
	public static boolean removeDirectory(String host,String uname,String pwd,String ftpDir,String pathName) throws Exception {
		FtpUtil ftpUtil = new FtpUtil(host,uname,pwd,ftpDir);
		ftpUtil.login();

		removeDirectory(pathName,ftpUtil.ftpClient);
		return true;
	}
	
	public static boolean download(String host,String uname,String pwd,String ftpDir,String remote,String local) throws Exception{   
		FtpUtil ftpUtil = new FtpUtil(host,uname,pwd,ftpDir);
		ftpUtil.login();
		FTPClient ftpClient = ftpUtil.ftpClient;
        //设置被动模式   
        ftpClient.enterLocalPassiveMode();   
        //设置以二进制方式传输   
        ftpClient.setFileType(FTP.BINARY_FILE_TYPE);   
           
        //检查远程文件是否存在   
        FTPFile[] files = ftpClient.listFiles(remote);   
        if(files.length != 1){   
            System.out.println("远程文件不存在");   
            return false;   
        }   
           
        long lRemoteSize = files[0].getSize();   
        File f = new File(local);   
        //本地存在文件，进行断点下载   
        boolean result = false;
        if(f.exists()){   
            long localSize = f.length();   
            //判断本地文件大小是否大于远程文件大小   
            if(localSize >= lRemoteSize){   
                System.out.println("本地文件大于远程文件，下载中止");   
                return false;   
            }   
            //进行断点续传，并记录状态   
            FileOutputStream out = new FileOutputStream(f,true);   
            ftpClient.setRestartOffset(localSize);   
            InputStream in = ftpClient.retrieveFileStream(new String(remote.getBytes("GBK"),"iso-8859-1"));   
            byte[] bytes = new byte[1024];   
            long step = lRemoteSize /100;   
            long process=localSize /step;   
            int c;   
            while((c = in.read(bytes))!= -1){   
                out.write(bytes,0,c);   
                localSize+=c;   
                long nowProcess = localSize /step;   
                if(nowProcess > process){   
                    process = nowProcess;   
                    if(process % 10 == 0)   
                        System.out.println("下载进度："+process);   
                    //TODO 更新文件下载进度,值存放在process变量中   
                }   
            }   
            in.close();   
            out.close();   

            boolean isDo = ftpClient.completePendingCommand();   
            if(isDo){   
                result = true;   
            }else {   
                result = false;   
            }   
        }else {   
            OutputStream out = new FileOutputStream(f);   
            InputStream in= ftpClient.retrieveFileStream(new String(remote.getBytes("GBK"),"iso-8859-1"));   
            byte[] bytes = new byte[1024];   
            long step = lRemoteSize /100;   
            long process=0;   
            long localSize = 0L;   
            int c;   
            while((c = in.read(bytes))!= -1){   
                out.write(bytes, 0, c);   
                localSize+=c;   
                long nowProcess = localSize /step;   
                if(nowProcess > process){   
                    process = nowProcess;   
                    if(process % 10 == 0)   
                        System.out.println("下载进度："+process);   
                    //TODO 更新文件下载进度,值存放在process变量中   
                }   
            }   
            in.close();   
            out.close();   
            boolean upNewStatus = ftpClient.completePendingCommand();   
            if(upNewStatus){   
            	result = true;   
            }else {   
            	result = false;   
            }   
        }   
        return result;   
    }   
	
	/**
	 * 测测测
	 */
	public static void main(String[] args) {
		String host = "static.ftpserver.vko.cn";
		String user = "test";
		String passwd = "vko999999";
		String remoteRootDir = "/home/static_new/richText";

//		FtpUtil ftp = new FtpUtil(host, user, passwd, remoteRootDir);
		try {
//			ftp.login();
//			System.out.println("server open : " + ftp.isConnected());
//			// 切换工作目录
////			ftp.changeWorkDir("/home/skyline/vko_video");
//			// 上传
//			File file = new File("f:\\089.JPG");
//			ftp.upload(file.getAbsolutePath(), "/lizheng/"+file.getName());
//			System.out.println("trans ok");

			
			String url = "http://cdn.vkoimg.cn/richText/1432953519124.png";
	    	Long topicId = 9999L;
	    	Long objId = 228L;
	    	Integer objType = 2;
	    	
	    	
//	    	String file = url.substring(url.indexOf("/richText") ).replace("/richText", "");
////	    	String fromFile = file;
//	    	String fromFile = "1432953519124.png";
//	    	String toFile = topicId + "/" + objId + "/" + objType + file;
//	    	boolean uploadOk = FtpUtil.renameFile(host, user, passwd, remoteRootDir, fromFile, toFile);
//			System.out.println(uploadOk);
	    	
//	    	FtpUtil.removeDirectory(host, user, passwd, remoteRootDir + "richText/", topicId + "/" + objType + "/" + objId);
	    	FtpUtil.download(host, user, passwd, remoteRootDir, "1433143115913.JPG", "/upload/richText/1433143115913.JPG");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
//			ftp.disconnect();
		}
	}
}
