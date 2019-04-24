package com.ycyl.httpClient;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

/**
 * httpClient网络请求测试
 * 
 * @author ycyl
 *
 */
public class HttpClientTest {

	public static void main(String[] args) throws ClientProtocolException, IOException, URISyntaxException {
//		CloseableHttpClient httpClient = HttpClients.createDefault();
//		HttpGet httpGet = new HttpGet("https://www.baidu.com");
//		CloseableHttpResponse response = httpClient.execute(httpGet);
//		
//        HttpEntity entity =  response.getEntity();
//        if(entity!=null) {
//        	InputStream inputStream =  entity.getContent();
//        	byte[] s = new byte[1024];
//        	StringBuilder builder = new StringBuilder();
//        	int len = -1;
//        	while((len = inputStream.read(s))!=-1) {
//        		builder.append(new String(s,0,len));
//        	}
//        	System.out.println(builder);
//        	
//        }
		
		
		boolean status = false;
		String s = null;
		String regex = "^[a-z0-9A-Z]{" + "6" + "}+$";
		status = s.matches(regex);
		System.out.println(status);
	}
}
