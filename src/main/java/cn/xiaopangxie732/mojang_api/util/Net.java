package cn.xiaopangxie732.mojang_api.util;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

public final class Net {
	private Net() {}
	public static String getConnection(String url) throws IllegalArgumentException {
		String response = null;
		HttpGet get = null;
		try {
			get = new HttpGet(new URL(url).toURI());
			HttpResponse re = HttpClientBuilder.create().build().execute(get);
			response = EntityUtils.toString(re.getEntity());
			get.releaseConnection();
		} catch(MalformedURLException e){e.printStackTrace();}catch(URISyntaxException e){e.printStackTrace();}catch(ClientProtocolException e){e.printStackTrace();}catch (IOException e) {e.printStackTrace();
		} finally {
			if(get != null){
				get.releaseConnection();
			}
		}
		return response;
	}
	public static String postConnection(String url, String ContentType, String RequestParameters) {
		HttpPost post = null;
		String retur = null;
		try {
			post = new HttpPost(new URL(url).toURI());
			post.setHeader("Content-Type", ContentType);
			StringEntity se = new StringEntity(RequestParameters,
					Charset.forName("UTF-8"));
			post.setEntity(se);
			HttpResponse re = HttpClientBuilder.create().build().execute(post);
			retur = EntityUtils.toString(re.getEntity());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(post != null){
				post.releaseConnection();
			}
		}
		return retur;
	}
}