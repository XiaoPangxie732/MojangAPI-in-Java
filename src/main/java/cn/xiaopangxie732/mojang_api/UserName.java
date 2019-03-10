package cn.xiaopangxie732.mojang_api;

import java.util.Properties;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import cn.xiaopangxie732.mojang_api.exceptions.UsernameOrTimestampInvalidException;
import cn.xiaopangxie732.mojang_api.util.Net;

/**
 * To get UUID of username.
 * @author XiaoPangxie732
 */
public class UserName {

	private static String url = "https://api.mojang.com/users/profiles/minecraft/";
	private static Gson json = new Gson();

	public static String UUIDAtTime(String username, long timestamp) {
		String furl = url + username + "?at=" + timestamp;
		String response = null;
		try {
			response = Net.getConnection(furl);
		} catch(IllegalArgumentException ex) {
			throw new UsernameOrTimestampInvalidException("Username \"" + username + "\" is invalid or on timestamp \"" + Long.toString(timestamp).replace("0", "Original") + "\" the username wasn't change.");
		}
		Properties result = json.fromJson(response, Properties.class);
		if(!result.getProperty("error", "noError").equals("noError"))
			throw new IllegalArgumentException(result.getProperty("errorMessage"));
		return result.getProperty("id");
	}

	public static String UUIDAtNow(String username) {
		String furl = url + username;
		String response;
		try {
			response = Net.getConnection(furl);
		} catch (IllegalArgumentException e) {
			throw new UsernameOrTimestampInvalidException("Username \"" + username + "\" is invalid");
		}
		Properties result = json.fromJson(response, Properties.class);
		return result.getProperty("id");
	}

	public static String UUIDAtOriginal(String username) {
		return UUIDAtTime(username, 0);
	}

	public static String UUIDOfNames(String... usernames) {
		if(usernames.length > 100) throw new IllegalArgumentException("Too more names! (" + usernames.length + "/100)");
		String response = Net.postConnection("https://api.mojang.com/profiles/minecraft", "application/json", json.toJson(usernames));
		Properties[] names = null;
		try {
			names = json.fromJson(response, Properties[].class);
		} catch (JsonSyntaxException e) {
			throw new IllegalArgumentException(json.fromJson(response, Properties.class).getProperty("errorMessage"));
		}
		String result = "";
		for(Properties output : names) {
			result += output.getProperty("name") + "=" + output.getProperty("id") + "\n";
		}
		return result;
	}
}