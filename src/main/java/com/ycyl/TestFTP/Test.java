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

		HashMap<String, Object> s = new HashMap<String, Object>();
		s.put("a", "123");
		s.put("s", "123");
		s.put("time", System.currentTimeMillis() + "");
		System.out.println(SHA1.SHA1(s));
		System.out.println(UUID.randomUUID().toString().replaceAll("-", ""));
		System.out.println(Sha1Util.getSha1("123" + " 123" + " " + System.currentTimeMillis()));
		System.out.println("System.naotime");
//1.
		Random r = new Random();
		for (int i = 0; i < 100; i++) {
			String n = System.nanoTime() + "" + r.nextInt();
			System.out.println(n);
		}
	}

}
