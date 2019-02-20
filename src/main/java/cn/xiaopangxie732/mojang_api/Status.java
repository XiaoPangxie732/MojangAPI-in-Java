package cn.xiaopangxie732.mojang_api;

import java.util.Properties;

import com.google.gson.Gson;

import cn.xiaopangxie732.mojang_api.exceptions.InvalidServerException;
import cn.xiaopangxie732.mojang_api.status.StatusServer;
import cn.xiaopangxie732.mojang_api.status.StatusType;
import cn.xiaopangxie732.mojang_api.util.Net;

/**
 * Use this class to check Mojang's server status
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
	 * To check server status
	 * @throws InvalidServerException When the server is invalid or <code>null</code>.
	 * @param server Which server needs to check the status.
	 * @return The status of this server.
	 */
	public StatusType getStatus(StatusServer server) throws InvalidServerException {
		if(server == null) throw new InvalidServerException("null");
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
	 * To flush Mojang's server status.
	 */
	public void flush() {
		response = Net.getConnection(url);
	}
}