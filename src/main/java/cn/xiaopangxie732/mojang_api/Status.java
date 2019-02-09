package cn.xiaopangxie732.mojang_api;

import java.net.URL;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import com.google.gson.Gson;

public class Status {
	private final String url = "https://status.mojang.com/check";
	private String response;
	Status() {
		try {
			HttpGet getm = new HttpGet(new URL(url).toURI());
			HttpResponse response = HttpClientBuilder.create().build().execute(getm);
			getm.releaseConnection();
			this.response = EntityUtils.toString(response.getEntity());
		} catch (Exception e) {
		}
		getAllStat();
	}
	private void getAllStat() {
		Gson json = new Gson();
		Object[] o = json.fromJson(response, Object[].class);
	}
	public StatusType getStatus() {
		return StatusType.RED;
	}
}
