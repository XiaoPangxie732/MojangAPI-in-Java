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

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import cn.xiaopangxie732.mojang_api.util.Net;
import cn.xiaopangxie732.mojang_api.util.PathUtil;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Objects;
import java.util.Properties;

/**
 * Check status of Mojang servers.
 * @author XiaoPangxie732
 * @since 0.0.1
 */
public class Status {

	/**
	 * Mojang servers.
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
	 * Status types.
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
		 * red(or could not connect to status server)
		 */
		RED;
		@Override
		public String toString() {
			return name().toLowerCase().replace("_", " ");
		}
	}

	private static final String url = "https://status.mojang.com/check";
	private static Properties response;
	/*
	 * Used for server status checking in MojangAPI-in-Java
	 */
	private static Status status = new Status();
	static void ensureAvailable(StatusServer server) {
		if(status.getStatus(server) == StatusType.RED) throw new ServiceException("Server service unavailable");
	}

	/**
	 * Construct a <code>Status</code> class.
	 * @since 0.0.1
	 */
	public Status() {
		if(Objects.isNull(response)) response = new Properties();
		JsonParser.parseString(Net.getConnection(url)).getAsJsonArray().forEach(element -> element.getAsJsonObject()
				.entrySet().forEach(entry -> response.setProperty(entry.getKey(), entry.getValue().getAsString())));
	}

	/**
	 * Check server status.
	 * @throws NullPointerException When the server is <code>null</code>.
	 * @param server The server to check status.
	 * @return The status of this server.<br>When failed to connect the status server, will return {@link StatusType#RED}.
	 * @since 0.0.1
	 * @author XiaoPangxie732
	 */
	public StatusType getStatus(StatusServer server) throws NullPointerException {
		String value = response.getProperty(String.valueOf(Objects.requireNonNull(server, "The server is null")));
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
	 * Check server statuses and list them.
	 * @param store Whether to store the output.
	 * @return The all server statuses list as <code>Properties</code> format.
	 * @since 0.0.4
	 * @author XiaoPangxie732
	 */
	public String getAllStatus(boolean store) {
		StringBuffer buf = new StringBuffer(198);
		response.forEach((key, value) -> {
			buf.append(key).append('=').append(value);
			if(buf.length()<198) buf.append('\n');
		});
		if(store) getAllStatus(PathUtil.getDesktop() + "/ServerStatus.properties");
		return buf.toString();
	}

	/**
	 * Check server statuses and list them.<br>
	 * @return The all server statuses list as <code>Properties</code> format.
	 * @since 0.0.4
	 * @author XiaoPangxie732
	 */
	public String getAllStatus() {
		return getAllStatus(false);
	}
	
	/**
	 * Check server statuses and store them.
	 * @param path Path to store the output, {@code null} for default path(ServerStatus.properties on desktop)
	 * @return Store outcome.
	 * @since 0.1
	 * @author XiaoPangxie732
	 */
	public boolean getAllStatus(String path) {
		try {
			response.store(new FileWriter(path),
				"Server statuses output.\nMojangAPI-in-Java made by XiaoPangxie732.\nhttps://github.com/XiaoPangxie732/MojangAPI-in-Java");
		} catch (IOException e) {
			System.err.println("File store failed!");
			System.err.print("Stacktrace: ");
			e.printStackTrace();
			return false;
		}
		System.out.println("File store complete.\nFile stored at desktop.(" + new File(path).getName() + ")");
		return true;
	}

	/**
	 * Refresh server statuses.
	 * @since 0.0.2
	 * @author XiaoPangxie732
	 */
	public void refresh() {
		new Status();
	}
}