/*
 *  MojangAPI-in-Java--Mojang Public API Java implementation.
 *  Copyright (C) 2019  XiaoPangxie732
 * 
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 * 
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 * 
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package cn.xiaopangxie732.mojang_api;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import cn.xiaopangxie732.mojang_api.util.Net;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

/**
 * Check the status of servers.
 * @author XiaoPangxie732
 * @since 0.0.1
 */
public class Status {

	/**
	 * Lists all the servers.
	 * @since 0.0.1
	 * @author XiaoPangxie732
	 */
	public enum StatusServer {
		/**
		 * minecraft.net
		 */
		MINECRAFT_NET,
		/**
		 * session.minecraft.net
		 */
		SESSION_MINECRAFT_NET,
		/**
		 * account.mojang.com
		 */
		ACCOUNT_MOJANG_COM,
		/**
		 * authserver.mojang.com
		 */
		AUTHSERVER_MOJANG_COM,
		/**
		 * sessionserver.mojang.com
		 */
		SESSIONSERVER_MOJANG_COM,
		/**
		 * api.mojang.com
		 */
		API_MOJANG_COM,
		/**
		 * textures.minecraft.net
		 */
		TEXTURES_MINECRAFT_NET,
		/**
		 * mojang.com
		 */
		MOJANG_COM;

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
		/**
		 * green
		 */
		GREEN,
		/**
		 * yellow
		 */
		YELLOW,
		/**
		 * red(or could not connect)
		 */
		RED;

		@Override
		public String toString() {
			return name().toLowerCase().replace("_", " ");
		}
	}

	private final String url = "https://status.mojang.com/check";
	private String response;

	/**
	 * Construct a <code>Status</code> class.<br>
	 * To get the servers status.
	 * @since 0.0.1
	 */
	public Status() {
		response = Net.getConnection(url);
	}

	/**
	 * To check server status.
	 * @throws NullPointerException When the server is <code>null</code>.
	 * @param server The server to check status.
	 * @return The status of this server.<br>When couldn't connect, it will return {@link StatusType#RED}.
	 * @since 0.0.1
	 * @author XiaoPangxie732
	 */
	public StatusType getStatus(StatusServer server) throws NullPointerException {
		if(server == null) throw new NullPointerException("The server is null");
		JsonArray array = new JsonParser().parse(response).getAsJsonArray();
		Properties result = new Properties();
		for(int var = 0;var < array.size(); ++var) {
			JsonObject object = array.get(var).getAsJsonObject();
			String key = object.keySet().iterator().next();
			result.setProperty(key, object.get(key).getAsString());
		}
		String value = result.getProperty(String.valueOf(server));
		switch (value) {
			case "green":
				return StatusType.GREEN;
			case "yellow":
				return StatusType.YELLOW;
			case "red":
				return StatusType.RED;
			default:
				return StatusType.RED;
		}
	}

	/**
	 * To check server status and list them.
	 * @param store Whether to store the output.
	 * @return The all server status.<br>Return as <code>Properties</code> format.
	 * @since 0.0.4
	 * @author XiaoPangxie732
	 */
	public String getAllStatus(boolean store) {
		JsonArray array = new JsonParser().parse(response).getAsJsonArray();
		Properties data = new Properties();
		for(int var = 0;var < array.size(); ++var) {
			JsonObject object = array.get(var).getAsJsonObject();
			String key = object.keySet().iterator().next();
			data.setProperty(key, object.get(key).getAsString());
		}
		String returned = String.valueOf(data).replace("{", "").replace("}", "").replace(", ", "\n");
		if(store) {
			try {
				data.store(new FileWriter(System.getProperty("user.home") + "\\Desktop\\ServerStatuses.properties"),
						"The server statuses output.\nMojangAPI-in-Java made by XiaoPangxie732.\nhttps://github.com/XiaoPangxie732/MojangAPI-in-Java");
			} catch (IOException e) {
				System.err.println("File store failed!");
				System.err.print("Stacktrace: ");
				e.printStackTrace();
				return returned;
			}
			System.out.println("File store complete.\nFile stored at desktop.(ServerStatuses.properties)");
		}
		return returned;
	}

	/**
	 * To check server status and list them.<br>
	 * @return The all server status.<br>Return as <code>Properties</code> format.
	 * @since 0.0.4
	 * @author XiaoPangxie732
	 */
	public String getAllStatus() {
		return getAllStatus(false);
	}

	/**
	 * To validate servers status.
	 * @since 0.0.2
	 * @author XiaoPangxie732
	 */
	public void validate() {
		response = Net.getConnection(url);
	}
}