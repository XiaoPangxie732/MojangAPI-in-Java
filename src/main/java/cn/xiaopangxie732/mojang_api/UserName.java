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

import cn.xiaopangxie732.mojang_api.util.Net;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * Used for username(playername) operating.
 * @author XiaoPangxie732
 */
public class UserName {

	private static final String url = "https://api.mojang.com/users/profiles/minecraft/";
	private static Gson json = new Gson();

	/**
	 * Get the UUID of this playername at a timestamp(requires playername change at least once).
	 * @param username The playername needs to get UUID.
	 * @param timestamp A UNIX timestamp (without milliseconds).
	 * @return The UUID of the name at the timestamp provided.
	 * @throws IllegalArgumentException When the timestamp is invalid.
	 * @throws IllegalArgumentException When the username is invalid or the timestamp has some error(or playername wasn't change at least once).
	 * @since 0.0.3
	 * @author XiaoPangxie732
	 */
	public static String UUIDAtTime(String username, long timestamp) throws IllegalArgumentException {
		String furl = url + username + "?at=" + timestamp;
		String response;
		try {
			response = Net.getConnection(furl);
		} catch(IllegalArgumentException ex) {
			throw new IllegalArgumentException("Username \"" + username + "\" is invalid or on timestamp \"" + Long.toString(timestamp).replace("0", "Original") + "\" the username wasn't change.");
		}
		Properties result = json.fromJson(response, Properties.class);
		if(result.getProperty("error", "noError").equals("error"))
			throw new IllegalArgumentException(result.getProperty("errorMessage"));
		return result.getProperty("id");
	}

	/**
	 * Get the UUID of this playername at now.
	 * @param username The playername needs to get UUID.
	 * @return The UUID of the name at now.
	 * @throws UsernameOrTimestampInvalidException When the username is invalid.
	 * @since 0.0.3
	 * @author XiaoPangxie732
	 */
	public static String UUIDAtNow(String username) throws IllegalArgumentException {
		String furl = url + username;
		String response;
		try {
			response = Net.getConnection(furl);
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException("Username \"" + username + "\" is invalid");
		}
		return json.fromJson(response, Properties.class).getProperty("id");
	}

	/**
	 * Get the UUID of this playername at original time (timestamp=0).<br>
	 * This requires playername change at least once.
	 * @param username The playername needs to get UUID.
	 * @return The UUID of the name at the original.
	 * @throws UsernameOrTimestampInvalidException When the username is invalid or playername wasn't change at least once.
	 * @since 0.0.3
	 * @author XiaoPangxie732
	 */
	public static String UUIDAtOriginal(String username) throws IllegalArgumentException {
		return UUIDAtTime(username, 0);
	}

	/**
	 * Get the UUID of the playername(s).<br>
	 * This will not output the player that non-existing.
	 * @param usernames The playername(s) needs to get UUID.
	 * @param store To store the output.
	 * @return The UUID(s) of the playername(s).<br>Return as <code>Properties</code> format.
	 * @throws IllegalArgumentException When the request names reached more than 100.
	 * @throws IllegalArgumentException when any of the usernames is <code>null</code> or <code>""</code>.
	 */
	public static String UUIDOfNames(boolean store, String... usernames) throws IllegalArgumentException {
		if(usernames.length > 100) throw new IllegalArgumentException("Too more names! (" + usernames.length + "/100)");
		String response = Net.postConnection("https://api.mojang.com/profiles/minecraft", "application/json", json.toJson(usernames));
		JsonElement element = new JsonParser().parse(response);
		if(!element.isJsonArray())
			throw new IllegalArgumentException(json.fromJson(response, Properties.class).getProperty("errorMessage"));
		JsonArray array = element.getAsJsonArray();
		StringBuffer result = new StringBuffer();
		Properties data = new Properties();
		for(int var = 0; var < array.size(); ++var) {
			JsonObject object = array.get(var).getAsJsonObject();
			result.append(object.get("name").getAsString() + "=" + object.get("id").getAsString() + "\n");
			data.setProperty(object.get("name").getAsString(), object.get("id").getAsString());
		}
		if(store) {
			try {
				data.store(new FileWriter(System.getProperty("user.home") + "\\Desktop\\UUIDOfNames.properties"),
						"The UUID(s) output.\nMojangAPI-in-Java made by XiaoPangxie732.\nhttps://github.com/XiaoPangxie732/MojangAPI-in-Java");
			} catch (IOException e) {
				System.out.println("File store failed!");
				e.printStackTrace();
				return String.valueOf(result);
			}
			System.out.println("File store complete.\nFile stored at desktop.(UUIDOfNames.properties)");
		}
		return String.valueOf(result);
	}

	/**
	 * Get the UUID of the playername(s).<br>
	 * This will not output the player that non-existing.
	 * @param usernames The playername(s) needs to get UUID.
	 * @return The UUID(s) of the playername(s).<br>Output as <code>Properties</code> format.
	 * @throws IllegalArgumentException When the request names reached more than 100.
	 * @throws IllegalArgumentException when any of the usernames is <code>null</code> or <code>""</code>.
	 */
	public static String UUIDOfNames(String... usernames) throws IllegalArgumentException {
		return UUIDOfNames(false, usernames);
	}
}