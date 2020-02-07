/*
 *  MojangAPI-in-Java--Mojang Public API Java implementation.
 *  Copyright (C) 2019-2020  XiaoPangxie732
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

import cn.xiaopangxie732.mojang_api.Status.StatusServer;
import cn.xiaopangxie732.mojang_api.util.Net;
import cn.xiaopangxie732.mojang_api.util.PathUtil;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

import com.google.gson.*;

/**
 * Used for username(playername) related operations.
 * @author XiaoPangxie732
 * @since 0.0.3
 */
public class UserName {

	private static final String url = "https://api.mojang.com/users/profiles/minecraft/";
	/**
	 * This will return the UUID of the name at the timestamp provided.<br>
	 * {@code timestamp=0} can be used to get the UUID of the original user of that username, 
	 * however, it only works if the name was changed at least once, or if the account is legacy
	 * @param username Playername to get UUID.
	 * @param timestamp UNIX timestamp (without milliseconds).
	 * @return The UUID of the name at the timestamp provided.
	 * @throws IllegalArgumentException When the username is invalid or the timestamp invalid.
	 * @since 0.0.3
	 * @author XiaoPangxie732
	 */
	public static String UUIDAtTime(String username, long timestamp) throws IllegalArgumentException {
		Status.ensureAvailable(StatusServer.API_MOJANG_COM);
		String response = Net.getConnection(url + username + "?at=" + timestamp);
		if(response.equals("")) throw new IllegalArgumentException("Username \"" + username + "\" invalid" + 
				(timestamp == 0 ? " or username wasn't change at least once or account isn't lagacy" : ""));
		JsonObject result = JsonParser.parseString(response).getAsJsonObject();
		if(result.has("error")) throw new IllegalArgumentException(result.get("errorMessage").getAsString());
		return result.get("id").getAsString();
	}

	/**
	 * This will return the UUID of the name at now.
	 * @param username Playername to get UUID.
	 * @return The UUID of the name at now.
	 * @throws IllegalArgumentException When the username is invalid.
	 * @since 0.0.3
	 * @author XiaoPangxie732
	 */
	public static String UUIDAtNow(String username) throws IllegalArgumentException {
		Status.ensureAvailable(StatusServer.API_MOJANG_COM);
		return JsonParser.parseString(Net.getConnection(url + username)).getAsJsonObject().get("id").getAsString();
	}

	/**
	 * This will return the UUID of the name at original time (timestamp=0).<br>
	 * This requires playername change at least once or account is legacy.
	 * @param username Playername to get UUID.
	 * @return The UUID of the name at the original.
	 * @throws IllegalArgumentException When the username is invalid or playername wasn't change at least once or account isn't legacy.
	 * @since 0.0.3
	 * @author XiaoPangxie732
	 */
	public static String UUIDAtOriginal(String username) throws IllegalArgumentException {
		return UUIDAtTime(username, 0);
	}

	/**
	 * This will return player UUIDs.<br>
	 * This will not output the player that non-existing.
	 * @param usernames The playernames to get UUIDs.<br>Note: playernames are case-corrected
	 * @param store Whether to store the output.
	 * @return Player UUIDs.<br>Output as <code>Properties</code> format.
	 * @throws IllegalArgumentException When the request names reached more than 10.
	 * @throws IllegalArgumentException when any of the usernames is <code>null</code> or <code>""</code>.
	 */
	public static String UUIDOfNames(boolean store, String... usernames) throws IllegalArgumentException {
		Status.ensureAvailable(StatusServer.API_MOJANG_COM);
		if(usernames.length > 10) throw new IllegalArgumentException("Too more names! (" + usernames.length + "/10)");
		JsonElement element = JsonParser.parseString(Net.postConnection("https://api.mojang.com/profiles/minecraft", 
				"application/json", new Gson().toJson(usernames)));
		if(!element.isJsonArray())
			throw new IllegalArgumentException(element.getAsJsonObject().get("errorMessage").getAsString());
		JsonArray array = element.getAsJsonArray();
		StringBuffer result = new StringBuffer();
		Properties data = new Properties();
		array.forEach(ele -> {
			JsonObject object = ele.getAsJsonObject();
			result.append(object.get("name").getAsString()).append("=").append(object.get("id").getAsString()).append('\n');
			data.setProperty(object.get("name").getAsString(), object.get("id").getAsString());
		});
		result.setLength(result.length()-1);
		result.trimToSize();
		if(store) {
			try {
				data.store(new FileWriter(PathUtil.getDesktop() + "/PlayerUUIDs.properties"),
						"PlayerUUIDs output.\nMojangAPI-in-Java made by XiaoPangxie732.\nhttps://github.com/XiaoPangxie732/MojangAPI-in-Java");
			} catch (IOException e) {
				System.out.println("File store failed!");
				e.printStackTrace();
				return result.toString();
			}
			System.out.println("File store complete.\nFile stored at desktop.(UUIDOfNames.properties)");
		}
		return result.toString();
	}

	/**
	 * This will return player UUIDs.<br>
	 * This will not output the player that non-existing.
	 * @param usernames The playernames to get UUIDs.<br>Note: playernames are case-corrected
	 * @return Player UUIDs.<br>Output as <code>Properties</code> format.
	 * @throws IllegalArgumentException When the request names reached more than 10.
	 * @throws IllegalArgumentException When any of the usernames is <code>null</code> or <code>""</code>.
	 */
	public static String UUIDOfNames(String... usernames) throws IllegalArgumentException {
		return UUIDOfNames(false, usernames);
	}
}