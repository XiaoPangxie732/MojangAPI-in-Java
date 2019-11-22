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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Properties;

import javax.imageio.ImageIO;
import com.google.gson.Gson;
import com.google.gson.JsonParser;

import cn.xiaopangxie732.mojang_api.Status.StatusServer;
import cn.xiaopangxie732.mojang_api.util.Auth;
import cn.xiaopangxie732.mojang_api.util.Net;

/**
 * The class for operating the skin.
 * @author XiaoPangxie732
 * @since 0.0.5
 */
public class Skin {
	/**
	 * This will set the skin for the selected profile, but Mojang's servers will fetch the skin from a URL. This will also work for legacy accounts.
	 * @param access_token The access token of an account. can be get by using {@link Auth#getAccessToken(String, String)}
	 * @param slim Skin is slim or not.
	 * @param uuid UUID of the account. can be get by using {@link UserName#UUIDAtNow(String)}
	 * @param url The skin image path. Cannot be local file.
	 * @throws IllegalArgumentException When change skin failed.
	 * @since 0.0.5
	 */
	public static void changeSkin(String access_token, boolean slim, String uuid, URL url) throws IllegalArgumentException {
		Status.ensureAvailable(StatusServer.API_MOJANG_COM);
		try {
			String response = Auth.postConnection("https://api.mojang.com/user/profile/" + uuid +"/skin", 
					"model=" + (slim ? "slim" : "") + "&url=" + 
			URLEncoder.encode(url.toString(), "UTF-8"), access_token);
			if(!response.equals(""))
				throw new IllegalArgumentException(JsonParser.parseString(response).getAsJsonObject()
						.get("errorMessage").getAsString());
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	/**
	 * This uploads a skin to Mojang's servers. It also sets the users skin. This works on legacy counts as well.
	 * @param access_token The access token of an account. can be get by using {@link Auth#getAccessToken(String, String)}
	 * @param isSlim Skin is slim or not.
	 * @param uuid UUID of the account. can be get be using {@link UserName#UUIDAtNow(String)}
	 * @param uri The skin image path. if it is a local file, it needs add a prefix "file:///" and replace "\\" to "/"(on Windows).
	 * @throws IllegalArgumentException When change skin failed.
	 * @since 0.0.5
	 */
	public static void changeSkinAndUpload(String access_token, boolean isSlim, String uuid, URI uri) 
			throws IllegalArgumentException {
		Status.ensureAvailable(StatusServer.API_MOJANG_COM);
		StringBuffer response = new StringBuffer();
		HttpURLConnection connection = null;
		try {
			connection = (HttpURLConnection)(new URL("https://api.mojang.com/user/profile/" + uuid 
					+"/skin").openConnection());
			connection.setDoOutput(true);
			connection.setRequestMethod("PUT");
			connection.setRequestProperty("Authorization", "Bearer " + access_token);
			/*
			 * The boundary is Base64-encoded of "MojangAPI-in-Java_datatransfer"
			 */
			connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=TW9qYW5nQVBJLWluLUphdmFfZGF0YXRyYW5zZmVy");
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(ImageIO.read(new File(uri)), new File(uri).getName().split("\\.")[1], baos);
			connection.getOutputStream().write(("--TW9qYW5nQVBJLWluLUphdmFfZGF0YXRyYW5zZmVy\r\n" + 
					"Content-Disposition: form-data; name=\"model\"\r\n\r\n" + 
					(isSlim ? "slim" : "") + "\r\n" + 
					"--TW9qYW5nQVBJLWluLUphdmFfZGF0YXRyYW5zZmVy\r\n" + 
					"Content-Disposition: form-data; name=\"file\"; filename=\"skin.png\"\r\n" + 
					"Content-Type: image/png\r\n\r\n").getBytes("UTF-8"));
			baos.writeTo(connection.getOutputStream());
			connection.getOutputStream().write(("\r\n" + "--TW9qYW5nQVBJLWluLUphdmFfZGF0YXRyYW5zZmVy--")
					.getBytes("UTF-8"));
			connection.connect();
			try(InputStream in = connection.getInputStream()) {
				for(int i = in.read(); i != -1; i = in.read()) 
					response.append((char)i);
			}
		} catch(IOException ioe) {
			try(InputStream err = connection.getErrorStream()) {
				for(int i = err.read(); i != -1; i = err.read()) 
					response.append((char)i);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			connection.disconnect();
		}
	}
	/**
	 * Reset the skin.
	 * @param access_token The access token of UUID's account. can be get by using {@link Auth#getAccessToken(String, String)}
	 * @param uuid UUID of the player. can be get be using {@link UserName#UUIDAtNow(String)}
	 * @throws IllegalStateException Throw when change skin failed.
	 * @since 0.0.5
	 */
	public static void resetSkin(String access_token, String uuid) {
		Status.ensureAvailable(StatusServer.API_MOJANG_COM);
		StringBuffer response = new StringBuffer();
		HttpURLConnection connection = null;
		try {
			connection = (HttpURLConnection)(new URL("https://api.mojang.com/user/profile/" + uuid 
					+"/skin").openConnection());
			connection.setDoOutput(true);
			connection.setRequestMethod("DELETE");
			connection.setRequestProperty("Authorization", "Bearer " + access_token);
			connection.connect();
			InputStream in = connection.getInputStream();
			int i;
			while((i = in.read())!= -1) {
				response.append((char)i);
			}
		} catch(IOException ioe) {
			InputStream err = connection.getErrorStream();
			int i;
			try {
				while((i = err.read())!= -1) {
					response.append((char)i);
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			throw new IllegalStateException(new Gson().fromJson(response.toString(), Properties.class).getProperty("errorMessage"));
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			connection.disconnect();
		}
	}
	/**
	 * Used to verify identity when changing skin with untrusted IP.
	 * @author XiaoPangxie732
	 * @since 0.1
	 */
	public static class SecurityQA {
		/**
		 * Check if you need to answer security questions
		 * @param accessToken Player's access token
		 * @return true for need and false for not need
		 * @see Auth#getAccessToken(String, String)
		 * @since 0.1
		 */
		public static boolean checkNeeded(String accessToken) {
			Status.ensureAvailable(StatusServer.API_MOJANG_COM);
			HashMap<String, String> map = new HashMap<>();
			map.put("Authorization", "Bearer " + accessToken);
			String response = Net.getConnection("https://api.mojang.com/user/security/location", map);
			System.out.println(response);
			if(response.length() == 0) return false;
			else return true;
		}
	}
}