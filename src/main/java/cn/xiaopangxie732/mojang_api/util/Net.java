package cn.xiaopangxie732.mojang_api.util;

import java.net.URL;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

public class Net {
	public static String getConnection(String url) {
		String response = null;
		try {
			HttpGet get = new HttpGet(new URL(url).toURI());
			HttpResponse re = HttpClientBuilder.create().build().execute(get);
			response = EntityUtils.toString(re.getEntity());
			get.releaseConnection();
		} catch (Exception e) {e.printStackTrace();}
		return response;
	}
}