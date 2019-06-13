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

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public final class Net {
	private Net() {}

	public static String getConnection(String url) throws IllegalArgumentException {
		StringBuffer response = new StringBuffer();
		HttpURLConnection connection = null;
		try {
			connection = (HttpURLConnection)(new URL(url).openConnection());
			connection.setRequestMethod("GET");
			connection.connect();
			InputStream in = connection.getInputStream();
			int i;
			while((i = in.read())!= -1) {
				response.append((char)i);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			connection.disconnect();
		}
		return String.valueOf(response);
	}
	public static String postConnection(String url, String ContentType, String RequestParameters) {
		StringBuffer response = new StringBuffer();
		HttpURLConnection connection = null;
		try {
			connection = (HttpURLConnection)(new URL(url).openConnection());
			connection.setDoOutput(true);
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type", ContentType);
			connection.getOutputStream().write(RequestParameters.getBytes());
			connection.connect();
			InputStream in = connection.getInputStream();
			int i;
			while((i = in.read())!= -1) {
				response.append((char)i);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			connection.disconnect();
		}
		return String.valueOf(response);
	}
}