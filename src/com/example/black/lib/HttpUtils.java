package com.example.black.lib;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpUtils {
	public static InputStream getInputStream(String Url) {
		InputStream is = null;
		try {
			URL url = new URL(Url);
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			connection.setReadTimeout(5000);
			connection.setConnectTimeout(5000);
			is = connection.getInputStream();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return is;
	}
	//String proxy=Proxy.getDefaultHost(); int port = Proxy.getDefaultPort() == -1 80 : Proxy.getDefaultPort();
	//if (networkInfo!=null && networkInfo.getType() == 0)
	// {//0表示是wap请求，你可以去检查一下0表示的是哪个宏 return true; }else{ return false; } }
	// if(isWapNetwork){//注意，is if (proxy !=null) { HttpHost httpHost = new
	// HttpHost(proxy, port, "http");
	// ().setParameter(";, httpHost); } else { ().removeParameter(";); }
	// //HttpHost host=new HttpHost(proxy,port);
	// //request.getParams().setParameter(ConnRouteParams.DEFAULT_PROXY, host);
	// }
}
