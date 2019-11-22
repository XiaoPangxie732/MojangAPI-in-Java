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
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.HashMap;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.net.URL;
import java.net.Proxy.Type;

public final class Net {
	private Net() {}

	public static String getConnection(String url, HashMap<String, String> headers) {
		StringBuffer response = new StringBuffer();
		HttpURLConnection connection = null;
		try {
			connection = (HttpURLConnection)new URL(url).openConnection(
//					new Proxy(Type.HTTP, new InetSocketAddress(8081))
					);
			AtomicReference<HttpURLConnection> ref = new AtomicReference<>(connection);
			Optional.ofNullable(headers).ifPresent(map -> map.forEach((k, v) -> ref.get().setRequestProperty(k, v)));
			connection.connect();
			try(InputStream in = connection.getInputStream()) {
				for(int i = in.read(); i != -1; i = in.read()) response.append((char)i);
			}
		} catch (Exception e) {
			try(InputStream s = connection.getErrorStream()) {
				for(int i = s.read(); i != -1; i = s.read()) response.append((char)i);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			return response.toString();
		} finally {
			connection.disconnect();
		}
		return response.toString();
	}
	public static String getConnection(String url) {
		return getConnection(url, null);
	}
	public static String postConnection(String url, String ContentType, String RequestParameters) {
		StringBuffer response = new StringBuffer();
		HttpURLConnection connection = null;
		try {
			connection = (HttpURLConnection)new URL(url).openConnection(
//					new Proxy(Type.HTTP, new InetSocketAddress(8081))
					);
			connection.setDoOutput(true);
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type", ContentType);
			connection.getOutputStream().write(RequestParameters.getBytes());
			connection.connect();
			try(InputStream in = connection.getInputStream()) {
				for(int i = in.read(); i != -1; i = in.read()) response.append((char)i);
			}
		} catch (Exception e) {
			try(InputStream in = connection.getErrorStream()) {
				for(int i = in.read(); i != -1; i = in.read()) response.append((char)i);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			return response.toString();
		} finally {
			connection.disconnect();
		}
		return response.toString();
	}
	public static String postConnection(String url, String ContentType, String RequestParameters, HashMap<String, String> headers) {
		StringBuffer response = new StringBuffer();
		HttpURLConnection connection = null;
		try {
			connection = (HttpURLConnection)(new URL(url).openConnection());
			connection.setDoOutput(true);
			connection.setRequestMethod("POST");
			AtomicReference<HttpURLConnection> ref = new AtomicReference<>(connection);
			Optional.ofNullable(headers).ifPresent(map -> map.forEach((k, v) -> ref.get().setRequestProperty(k, v)));
			connection.setRequestProperty("Content-Type", ContentType);
			connection.getOutputStream().write(RequestParameters.getBytes());
			connection.connect();
			try(InputStream in = connection.getInputStream()) {
				for(int i = in.read(); i != -1; i = in.read()) response.append((char)i);
			}
		} catch (Exception e) {
			try(InputStream in = connection.getErrorStream()) {
				for(int i = in.read(); i != -1; i = in.read()) response.append((char)i);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			return response.toString();
		} finally {
			connection.disconnect();
		}
		return response.toString();
	}
}