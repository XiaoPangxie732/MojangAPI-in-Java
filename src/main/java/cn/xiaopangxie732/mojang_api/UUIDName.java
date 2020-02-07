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

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Base64;
import java.util.Date;

import javax.imageio.ImageIO;

/**
 * Used for UUID related operations.
 * @author XiaoPangxie732
 * @since 0.0.4
 */
public class UUIDName {

    /**
     * Get name history.
     * @param uuid The player's UUID. can be get by using {@link UserName#UUIDAtNow(String)}
     * @return The name history of the UUID.
     * @since 0.0.4
     */
    public static String nameHistory(String uuid) {
    	Status.ensureAvailable(StatusServer.API_MOJANG_COM);
        JsonArray response = JsonParser.parseString(Net.getConnection(
        		"https://api.mojang.com/user/profiles/" + uuid + "/names")).getAsJsonArray();
        StringBuffer result = new StringBuffer();
        response.forEach(ele -> {
        	JsonObject obj = ele.getAsJsonObject();
        	if(obj.has("changedToAt")) 
        		result.append(DateFormat.getInstance().format(new Date(obj.get("changedToAt").getAsLong())))
        				.append('=').append(obj.get("name").getAsString());
        	else result.append("Original=").append(obj.get("name").getAsString());
        	result.append('\n');
        });
        result.setLength(result.length()-1);
        result.trimToSize();
        return result.toString();
    }
    /**
     * Get skin URL.<br>
     * Requires custom skin has been set.
     * @param uuid The UUID of player. can be get by using {@link UserName#UUIDAtNow(String)}
     * @return The skin URL of given UUID.
     * @throws IllegalArgumentException If no custom skin has been set.
     * @since 0.0.5
     */
    public static String getSkinURL(String uuid) throws IllegalArgumentException {
    	Status.ensureAvailable(StatusServer.SESSIONSERVER_MOJANG_COM);
    	JsonObject obj = JsonParser.parseString(new String(Base64.getDecoder().decode(JsonParser.parseString(
    			Net.getConnection("https://sessionserver.mojang.com/session/minecraft/profile/" + uuid))
    			.getAsJsonObject().get("properties").getAsJsonArray().get(0).getAsJsonObject().get("value").getAsString())))
    			.getAsJsonObject().get("textures").getAsJsonObject();
    	if(!obj.has("SKIN")) throw new IllegalArgumentException("No custom skin has been set");
    	return obj.get("SKIN").getAsJsonObject().get("url").getAsString();
    }
    /**
     * Get cape URL.<br>
     * Requires the account has cape.
     * @param uuid The UUID of player. can be get by using {@link UserName#UUIDAtNow(String)}
     * @return The skin URL of given UUID.
     * @throws IllegalArgumentException If the account has no cape.
     * @since 0.1
     */
    public static String getCapeURL(String uuid) throws IllegalArgumentException {
    	Status.ensureAvailable(StatusServer.SESSIONSERVER_MOJANG_COM);
    	JsonObject obj = JsonParser.parseString(new String(Base64.getDecoder().decode(JsonParser.parseString(
    			Net.getConnection("https://sessionserver.mojang.com/session/minecraft/profile/" + uuid))
    			.getAsJsonObject().get("properties").getAsJsonArray().get(0).getAsJsonObject().get("value").getAsString())))
    			.getAsJsonObject().get("textures").getAsJsonObject();
    	if(!obj.has("CAPE")) throw new IllegalArgumentException("No custom skin has been set");
    	return obj.get("CAPE").getAsJsonObject().get("url").getAsString();
    }
    /**
     * Store skin image to desktop.<br>
     * Requires custom skin has been set.
     * @since 0.0.5
     * @param uuid The UUID of player. can be get by using {@link UserName#UUIDAtNow(String)}
     * @throws IllegalArgumentException If no custom skin has been set.
     */
    public static void storeSkinImageToDesktop(String uuid) throws IllegalArgumentException {
    	try {
			ImageIO.createImageOutputStream(new FileOutputStream(PathUtil.getDesktop() + "/Skin.png")).writeBytes(Net.getConnection(getSkinURL(uuid)));
			System.out.println("File store complete");
		} catch (IOException e) {
			System.out.println("File stored failed!");
			e.printStackTrace();
		}
    }
    /**
     * Store cape image to desktop.<br>
     * Requires the account has cape.
     * @since 0.1
     * @param uuid The UUID of player. can be get by using {@link UserName#UUIDAtNow(String)}
     * @throws IllegalArgumentException If the account has no cape.
     */
    public static void storeCapeImageToDesktop(String uuid) throws IllegalArgumentException {
    	try {
			ImageIO.createImageOutputStream(new FileOutputStream(PathUtil.getDesktop() + "/Cape.png")).writeBytes(Net.getConnection(getCapeURL(uuid)));
			System.out.println("File store complete");
		} catch (IOException e) {
			System.out.println("File stored failed!");
			e.printStackTrace();
		}
    }
}