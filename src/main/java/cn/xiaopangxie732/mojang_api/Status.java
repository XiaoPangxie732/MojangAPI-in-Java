package cn.xiaopangxie732.mojang_api;

import java.util.Properties;

import com.google.gson.Gson;

import cn.xiaopangxie732.mojang_api.status.StatusServer;
import cn.xiaopangxie732.mojang_api.status.StatusType;
import cn.xiaopangxie732.mojang_api.util.Net;

/**
 * Used to check the Mojang's servers status.
 * @author XiaoPangxie732
 */
public class Status {
	private final String url = "https://status.mojang.com/check";
	private String response;
	private Gson json = new Gson();
	public Status() {
		response = Net.getConnection(url);
	}
	/**
	 * To check server status.
	 * @throws NullPointerException When the server is <code>null</code>.
	 * @param server Which server needs to check the status.
	 * @return The status of this server.
	 * @author XiaoPangxie732
	 */
	public StatusType getStatus(StatusServer server) throws NullPointerException {
		if(server == null) throw new NullPointerException("The server is null");
		Properties[] result = json.fromJson(response, Properties[].class);
		String value = result[server.ordinal()].getProperty(server.toString());
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
	/**
	 * To fresh Mojang's servers status.
	 * @author XiaoPangxie732
	 */
	public void fresh() {
		response = Net.getConnection(url);
	}
}