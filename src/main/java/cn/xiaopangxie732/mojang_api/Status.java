package cn.xiaopangxie732.mojang_api;

import java.net.URL;
import java.util.Properties;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import com.google.gson.Gson;

import cn.xiaopangxie732.mojang_api.enums.StatusServer;
import cn.xiaopangxie732.mojang_api.enums.StatusType;
import cn.xiaopangxie732.mojang_api.exceptions.InvalidServerException;

/**
 * Use this class to check Mojang's server status
 * @author XiaoPangxie732
 */
public class Status {
	private final String url = "https://status.mojang.com/check";
	private String response;
	private Gson json = new Gson();
	Status() {
		try {
			HttpGet get = new HttpGet(new URL(url).toURI());
			HttpResponse re = HttpClientBuilder.create().build().execute(get);
			this.response = EntityUtils.toString(re.getEntity());
			get.releaseConnection();
		} catch (Exception e) {e.printStackTrace();}
	}
	/**
	 * To check server status
	 * @throws InvalidServerException When the server is invalid or null.
	 * @param server Which server needs to check the status.
	 * @return The status of this server.
	 */
	public StatusType getStatus(StatusServer server) throws InvalidServerException{
		if(server == null) throw new InvalidServerException("null");
		Properties[] pros = json.fromJson(response, Properties[].class);
		String value = pros[server.ordinal()].getProperty(server.toString());
		switch (value) {
		case "green":
			return StatusType.GREEN;
		case "yellow":
			return StatusType.YELLOW;
		case "red":
			return StatusType.RED;
		}
		return StatusType.COULD_NOT_CONNECT;
	}
}