package com.ycyl.weixi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

/**获取微信用户的accessToken
 * @author ycyl
 *
 */
public class GetAccessToken {
	
	static final CacheManager cacheManager = new CacheManager();
    
	static final Cache cahce = cacheManager.getCache("token");
    
	static final String key = "token";
	

	public static final String ACCESSTOKEN_URL= "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=wx192728cdb812e8ba&secret=0b8503956c66bc58aaad5f9d9ed27162";
	
	public static void main(String[] args) throws IOException {
		//先获取accesstoken，不存在重新获取保存
		Element element =  cahce.get(key); 
        if(element!=null) {
        	System.out.println("内存中获取token"+element.getObjectValue());
        }else {
        	URL url = new URL(ACCESSTOKEN_URL);
    		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
    		
    		connection.setRequestProperty("accetp", "*/*");
    		connection.setRequestProperty("connection", "Keep-Alive");
    		connection.setRequestProperty("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.86 Safari/537.36");
    		
    		InputStream in = connection.getInputStream();
    		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
    		StringBuilder builder = new StringBuilder();
    		String s = null;
    		while((s=reader.readLine())!=null) {
    			builder.append(s);
    		}
    		System.out.println(builder.toString());
    		
    		//解析
    		JsonObject jsonObject = (JsonObject) new JsonParser().parse(builder.toString());
    		
    		System.out.println("url请求获取token:"+jsonObject.get("access_token").getAsString());
    		//缓存
    		cahce.put(new Element(key, jsonObject.get("access_token").getAsString()));
    		
        }
		
	}

}
