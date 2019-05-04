package cn.xiaopangxie732.mojang_api.util;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public final class Net {
	private Net() {}
	
	public static String getConnection(String url) throws IllegalArgumentException {
		StringBuffer response = new StringBuffer();
		HttpURLConnection connection = null;
		try {
			connection = (HttpURLConnection)(new URL(url).openConnection());
			connection.setRequestMethod("GET");
			connection.connect();
			InputStream in = connection.getInputStream();
			int i;
			while((i = in.read())!= -1) {
				response.append((char)i);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			connection.disconnect();
		}
		return String.valueOf(response);
	}
	public static String postConnection(String url, String ContentType, String RequestParameters) {
		StringBuffer response = new StringBuffer();
		HttpURLConnection connection = null;
		try {
			connection = (HttpURLConnection)(new URL(url).openConnection());
			connection.setRequestMethod("POST");
			connection.setDoOutput(true);
			connection.setRequestProperty("Content-type", ContentType);
			connection.getOutputStream().write(RequestParameters.getBytes());
			connection.connect();
			InputStream in = connection.getInputStream();
			int i;
			while((i = in.read())!= -1) {
				response.append((char)i);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			connection.disconnect();
		}
		return String.valueOf(response);
	}
}