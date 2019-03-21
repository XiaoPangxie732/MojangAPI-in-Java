package cn.xiaopangxie732.mojang_api;

import cn.xiaopangxie732.mojang_api.util.Net;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.Properties;

/**
 * Used to check the Mojang servers status.
 * @author XiaoPangxie732
 */
public class Status {

	/**
	 * Lists all the servers that can check the status.
	 * @since 0.0.1
	 * @author XiaoPangxie732
	 */
	public enum StatusServer {
		MINECRAFT_NET,
		SESSION_MINECRAFT_NET,
		ACCOUNT_MOJANG_COM,
		AUTHSERVER_MOJANG_COM,
		SESSIONSERVER_MOJANG_COM,
		API_MOJANG_COM,
		TEXTURES_MINECRAFT_NET,
		MOJANG_COM;

		/**
		 * Let enum to string.<br>
		 * @return The lower case of enum name.
		 * @since 0.0.1
		 * @author XiaoPangxie732
		 */
		@Override
		public String toString() {
			return name().toLowerCase().replace("_", ".");
		}
	}

	/**
	 * List all status type.
	 * @since 0.0.1
	 * @author XiaoPangxie732
	 */
	public enum StatusType {
		GREEN,
		YELLOW,
		RED,
		ERROR_TO_CONNECT;

		/**
		 * Let enum to string.
		 * @return The lower case of enum name.
		 * @since 0.0.1
		 * @author XiaoPangxie732
		 */
		@Override
		public String toString() {
			return name().toLowerCase().replace("_", " ");
		}
	}

	private final String url = "https://status.mojang.com/check";
	private String response;

	/**
	 * Construct a <code>Status</code> class.
	 * @since 0.0.1
	 */
	public Status() {
		response = Net.getConnection(url);
	}

	/**
	 * To check server status.
	 * @throws NullPointerException When the server is <code>null</code>.
	 * @param server The server needs to check the status.
	 * @return The status of this server.
	 * @since 0.0.1
	 * @author XiaoPangxie732
	 */
	public StatusType getStatus(StatusServer server) throws NullPointerException {
		if(server == null) throw new NullPointerException("The server is null");
		JsonArray array = new JsonParser().parse(response).getAsJsonArray();
		Properties result = new Properties();
		for(int var = 0;var < array.size(); ++var) {
			JsonObject object = array.get(var).getAsJsonObject();
			Object[] key = object.keySet().toArray();
			result.setProperty((String)key[0], object.get(key[0].toString()).getAsString());
		}
		String value = result.getProperty(server.toString());
		switch (value) {
			case "green":
				return StatusType.GREEN;
			case "yellow":
				return StatusType.YELLOW;
			case "red":
				return StatusType.RED;
		}
		return StatusType.ERROR_TO_CONNECT;
	}

	/**
	 * To fresh Mojang servers status.
	 * @since 0.0.2
	 * @author XiaoPangxie732
	 */
	public void fresh() {
		response = Net.getConnection(url);
	}
}