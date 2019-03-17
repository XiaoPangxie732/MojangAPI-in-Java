package cn.xiaopangxie732.mojang_api;

import java.util.HashMap;
import java.util.Properties;
import com.google.gson.Gson;

import cn.xiaopangxie732.mojang_api.util.Net;

/**
 * Used to check the Mojang's servers status.
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
		 * @author XiaoPangxie732
		 */
		@Override
		public String toString() {
			return name().toLowerCase().replace("_", " ");
		}
	}

	private final String url = "https://status.mojang.com/check";
	private String response;
	private Gson json = new Gson();

	/**
	 * Construct a <code>Status</code> class.
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
		Properties[] parr = json.fromJson(response, Properties[].class);
		HashMap<String, String> result = new HashMap<>();
		for(Properties p : parr) {
			result.put((String)p.keySet().toArray()[0], p.getProperty((String)p.keySet().toArray()[0]));
		}
		String value = result.get(server.toString());
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