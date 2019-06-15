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
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Base64;
import java.util.Date;

import javax.imageio.ImageIO;

/**
 * The class for operating UUID.
 * @author XiaoPangxie732
 * @since 0.0.4
 */
public class UUIDName {

    /**
     * Get name history.
     * @param uuid The player's UUID. can be get be using {@link UserName#UUIDAtNow(String)}
     * @return The name history of the UUID.
     * @since 0.0.4
     */
    public static String nameHistory(String uuid) {
        String url = "https://api.mojang.com/user/profiles/" + uuid + "/names";
        JsonArray response = new JsonParser().parse(Net.getConnection(url)).getAsJsonArray();
        StringBuilder result = new StringBuilder("Original=" + response.get(0).getAsJsonObject().get("name").getAsString());
        for(int var = 1; var < response.size(); ++var) {
            result.append("\n" + DateFormat.getDateInstance().format(new Date(Long.parseLong(response.get(var).getAsJsonObject().get("changedToAt").getAsString()))) + "="
                    + response.get(var).getAsJsonObject().get("name").getAsString());
        }
        return result.toString();
    }
    /**
     * Get skin URL.
     * @param uuid The UUID of player. can be get be using {@link UserName#UUIDAtNow(String)}
     * @return The skin URL of given UUID.
     * @since 0.0.5
     */
    public static String getSkinURL(String uuid) {
    	return new JsonParser().parse(new String(Base64.getDecoder().decode(new JsonParser()
    			.parse(Net.getConnection("https://sessionserver.mojang.com/session/minecraft/profile/" + uuid))
    			.getAsJsonObject().get("properties").getAsJsonArray().get(0).getAsJsonObject().get("value").getAsString()))).getAsJsonObject()
    			.get("textures").getAsJsonObject().get("SKIN").getAsJsonObject().get("url").getAsString();
    }
    /**
     * Store skin image to desktop.
     * @param uuid The UUID of player. can be get be using {@link UserName#UUIDAtNow(String)}
     */
    public static void storeSkinImageToDesktop(String uuid) {
    	try {
			ImageIO.createImageOutputStream(new FileOutputStream(System.getProperty("user.home") + "\\Desktop\\Skin.png")).writeBytes(Net.getConnection(getSkinURL(uuid)));
			System.out.println("File store complete");
		} catch (IOException e) {
			System.out.println("File stored failed!");
			e.printStackTrace();
		}
    }
}