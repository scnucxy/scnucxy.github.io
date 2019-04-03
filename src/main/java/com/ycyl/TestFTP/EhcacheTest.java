package com.ycyl.TestFTP;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

public class EhcacheTest {

	public static void main(String[] args) throws Exception {
//           final CacheManager cacheManager = new CacheManager();
//           
//           final Cache cahce = cacheManager.getCache("helloworld");
//           
//           final String key = "greeting";
//           
//           final Element element = new Element(key, "123");
//           
//           cahce.put(element);
//           
//           final Element getElement  = cahce.get(key);
//           
//           System.out.println(getElement.getObjectValue());
		URL url = new URL("https://blog.csdn.net/qq_38023748/article/details/88577545");
		URLConnection connection =  url.openConnection();
		InputStream in = connection.getInputStream();
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
		String s = null;
		while((s=bufferedReader.readLine())!=null) {
			System.out.println(s);
		}
	}

}
