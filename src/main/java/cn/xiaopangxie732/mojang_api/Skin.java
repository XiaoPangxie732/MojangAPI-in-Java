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

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Properties;

import javax.imageio.ImageIO;
import com.google.gson.Gson;

import cn.xiaopangxie732.mojang_api.util.Auth;

public class Skin {
	/**
	 * To change skin.
	 * @param access_token The access token of UUID's account.
	 * @param isSlim Skin is slim or not.
	 * @param uuid UUID of player. can be get be using {@link UserName#UUIDAtNow(String)}
	 * @param uri The skin image path. if it is a local file, it needs add a prefix "file:///" and replace "\\" to "/"(Windows).
	 */
	public static void changeSkin(String access_token, boolean isSlim, String uuid, URI uri) {
		try {
			String response = Auth.postConnection("https://api.mojang.com/user/profile/" + uuid 
					+"/skin", "model=" + (isSlim ? "slim" : "") + "&url=" + 
			URLEncoder.encode(uri.toURL().toString(), "UTF-8"), access_token);
			if(response != null)
				throw new IllegalArgumentException("Failed to change skin");
		} catch (UnsupportedEncodingException | MalformedURLException e) {
			e.printStackTrace();
		}
	}
	public static void changeSkinAndUpload(String access_token, boolean isSlim, String uuid, URI uri) {
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
			ImageIO.write(ImageIO.read(new File(uri)), new File(uri).getName().split(".")[1], baos);
			
			connection.getOutputStream().write(new String(
					"--TW9qYW5nQVBJLWluLUphdmFfZGF0YXRyYW5zZmVy\r\n" + 
					"Content-Disposition: form-data; name=\"model\"\r\n" + 
					"\r\n" + 
					"\r\n" + 
					"--TW9qYW5nQVBJLWluLUphdmFfZGF0YXRyYW5zZmVy\r\n" + 
					"Content-Disposition: form-data; name=\"file\"; filename=\"skin.png\"\r\n" + 
					"Content-Type: image/png\r\n" + 
					(isSlim ? "slim" : "") + "\r\n" + 
					new String(baos.toByteArray()) + "\r\n" + 
					"--TW9qYW5nQVBJLWluLUphdmFfZGF0YXRyYW5zZmVy--").getBytes("UTF-8"));
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
	public static void resetSkin(String access_token, String uuid) {
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
}