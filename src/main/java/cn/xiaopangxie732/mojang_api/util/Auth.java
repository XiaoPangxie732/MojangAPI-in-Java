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
package cn.xiaopangxie732.mojang_api.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Properties;

import com.google.gson.Gson;
import com.google.gson.JsonParser;

public class Auth {
	public static String getAccessToken(String email, String password) {
		return new JsonParser().parse(authConnection(email, password)).getAsJsonObject().get("accessToken").getAsString();
	}
	public static String postConnection(String url, String RequestParameters, String accessToken) {
		StringBuffer response = new StringBuffer();
		HttpURLConnection connection = null;
		try {
			connection = (HttpURLConnection)(new URL(url).openConnection());
			connection.setRequestMethod("POST");
			connection.setDoOutput(true);
			connection.setRequestProperty("Content-type", "application/x-www-form-urlencoded");
			connection.setRequestProperty("Authorization", "Bearer " + accessToken);
			connection.getOutputStream().write(RequestParameters.getBytes());
			connection.connect();
			InputStream in = connection.getInputStream();
			int i;
			while((i = in.read())!= -1) {
				response.append((char)i);
			}
		} catch(IOException ioe) {
			InputStream err = connection.getErrorStream();
			StringBuffer errOut = new StringBuffer();
			int i;
			try {
				while((i = err.read())!= -1) {
					errOut.append((char)i);
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			System.out.println(errOut);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			connection.disconnect();
		}
		return String.valueOf(response);
	}
	private static String authConnection(String email, String pwd) {
		StringBuffer response = new StringBuffer();
		HttpURLConnection connection = null;
		try {
			connection = (HttpURLConnection)(new URL("https://authserver.mojang.com/authenticate").openConnection());
			connection.setDoOutput(true);
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type", "application/json");
			connection.getOutputStream().write(new String("{\"agent\": {\"name\": \"Minecraft\",\"version\": 1},"
					+ "\"username\": \""+email+"\","
					+ "\"password\": \""+pwd+"\"}").getBytes());
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
		return String.valueOf(response);
	}
}