package com.ycyl.TestFTP;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.SocketException;
import java.util.Properties;
import java.util.logging.Logger;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

/**
 * Hello world!
 *
 */
public class App {
	private static Logger logger = Logger.getLogger(App.class.getName());

	public static void main(String[] args) {
//		int ftpPort = 0;
//		String ftpUserName = "";
//		String ftpPassword = "";
//		String ftpHost = "";
//		String ftpPath = "";
//		String writeTempFielPath = "D:/";
//		try {
//			 System.out.println(System.getProperty("java.class.path"));  
//			String fileName = App.class.getClassLoader().getResource("env.properties").getPath();//获取文件路径  
//			InputStream in = App.class.getClassLoader().getResourceAsStream("env.properties");
//			if (in == null) {
//				logger.info("配置文件env.properties读取失败!");
//			} else {
//				Properties properties = new Properties();
//				properties.load(in);
//				ftpUserName = properties.getProperty("ftpUserName");
//				ftpPassword = properties.getProperty("ftpPassword");
//				ftpHost = properties.getProperty("ftpHost");
//				ftpPort = Integer.valueOf(properties.getProperty("ftpPort"));
//				ftpPath = properties.getProperty("ftpPath");
//				
//				String result = readFileFromFTP(ftpUserName, ftpPassword, ftpPath, ftpHost, ftpPort, "pom.xml");
//				System.out.println("读取配置文件结果为：" + result);
//				
//			
//				ftpPath = ftpPath + "/" + "pomnew.xml";
//				upload(ftpPath, ftpUserName, ftpPassword, ftpHost, ftpPort, result, writeTempFielPath);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}

		for (int i = 0; i < 100; i++) {
			if (i == 20) {
             int x = 20/0;
               
			}
			System.out.println(i);
		}
	}

	/**
	 * 获取FTPClient对象
	 * 
	 * @param ftpHost
	 * @param ftpPort
	 * @param ftpUserName
	 * @param ftpPassword
	 * @return
	 */
	public static FTPClient getFTPClient(String ftpHost, int ftpPort, String ftpUserName, String ftpPassword) {
		FTPClient ftpClient = null;

		try {
			ftpClient = new FTPClient();
			ftpClient.connect(ftpHost, ftpPort);// 连接ftp服务器
			ftpClient.login(ftpUserName, ftpPassword);// 登录FTP服务器
			if (!FTPReply.isPositiveCompletion(ftpClient.getReplyCode())) {
				logger.info("未连接到FTP，用户名或密码错误");
			} else {
				logger.info("FTP连接成功");
			}
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ftpClient;
	}

	public static void upload(String ftpPath, String ftpUserName, String ftpPassword, String ftpHost, int ftpPort,
			String fileContent, String writeTempFielPath) {
		logger.info("开始上传文件到FTP...");
		FTPClient ftpClient = null;

		// 设置二进制方式传输
		try {
			ftpClient = App.getFTPClient(ftpHost, ftpPort, ftpUserName, ftpPassword);
			// 设置PassiveMode传输
			ftpClient.enterLocalPassiveMode();
			// 设置二进制方式处理
			ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
			// 对远程目录进行处理
			String remoteFileName = ftpPath;
			if (ftpPath.contains("/")) {
				remoteFileName = ftpPath.substring(ftpPath.lastIndexOf("/") + 1);
			}

			boolean writeResult = write(remoteFileName, fileContent, writeTempFielPath);
			if (writeResult) {
				File f = new File(writeTempFielPath + "/" + remoteFileName);
				InputStream in = new FileInputStream(f);
				ftpClient.storeFile(remoteFileName, in);
				in.close();
				logger.info("上传文件" + remoteFileName + "到FTP成功！");
				f.delete();
			} else {
				logger.info("上传文件失败");
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				ftpClient.disconnect();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	public static boolean write(String fileName, String fileContext, String writeTempFielPath) {
		try {
			logger.info("写文件到本地缓存");
			File f = new File(writeTempFielPath + "/" + fileName);
			if (!f.exists()) {

				if (!f.createNewFile()) {
					logger.info("文件不存在，创建失败");
				}
				BufferedWriter bw = new BufferedWriter(new FileWriter(f));
				bw.write(fileContext.replaceAll("\n", "\r\n"));
				bw.flush();
				bw.close();
				return true;

			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return false;

	}

	public static String readFileFromFTP(String ftpUserName, String ftpPassword, String ftpPath, String ftpHost,
			int ftpPort, String fileName) {
		StringBuffer resultBuffer = new StringBuffer();
		InputStream in = null;
		FTPClient ftpClient = null;
		try {

			logger.info("开始从绝对路径" + ftpPath + "读取文件");
			ftpClient = App.getFTPClient(ftpHost, ftpPort, ftpUserName, ftpPassword);
			ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
			ftpClient.setControlEncoding("UTF-8");

			ftpClient.enterLocalPassiveMode();
			ftpClient.changeWorkingDirectory(ftpPath);
			in = ftpClient.retrieveFileStream(fileName);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (in != null) {
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String data = null;

			try {
				while ((data = br.readLine()) != null) {
					resultBuffer.append(data + "\n");
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				try {
					ftpClient.disconnect();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return resultBuffer.toString();
	}

}
