package com.ycyl.TestFTP;

import java.security.DigestException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Random;
import java.util.UUID;

public class Test {

	public static void main(String[] args) throws ParseException, DigestException {
		// TODO Auto-generated method stub
//		System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName());
//
//		BufferedWriter bufferedWriter = null;
//
//		try {
//			bufferedWriter = new BufferedWriter(new FileWriter("C:/Users/ycyl/Desktop/java技术研究.txt", true));
//			bufferedWriter.write("sds");
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} finally {
//			if (bufferedWriter != null) {
//				try {
//					bufferedWriter.close();
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}
//		}

//		java.util.Date date = java.text.SimpleDateFormat.getDateInstance().parse("2009-3-31 12:00:00");
//		System.out.println(date.toString());
         //唯一编号1.数据指纹 2.时间戳 3.UUID
		HashMap<String, Object> s = new HashMap<String, Object>();
		System.out.println(SHA1.SHA1(s));
     
	}

}
