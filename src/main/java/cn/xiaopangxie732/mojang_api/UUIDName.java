package cn.xiaopangxie732.mojang_api;

import cn.xiaopangxie732.mojang_api.util.Net;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;

import java.text.DateFormat;
import java.util.Date;

/**
 * Used for UUID operating.
 * @author XiaoPangxie732
 */
public class UUIDName {

    private static Gson json = new Gson();

    /**
     * To get UUID's name history.
     * @param uuid The player's UUID, can be get be using {@link UserName#UUIDAtNow(String)}
     * @return The name history of the UUID.
     */
    public static String NameHistory(String uuid) {
        String url = "https://api.mojang.com/user/profiles/" + uuid + "/names";
        JsonArray response = new JsonParser().parse(Net.getConnection(url)).getAsJsonArray();
        StringBuilder result = new StringBuilder("Original=" + response.get(0).getAsJsonObject().get("name").getAsString());
        for(int var = 1; var < response.size(); ++var) {
            result.append("\n" + DateFormat.getDateInstance().format(new Date(Long.parseLong(response.get(var).getAsJsonObject().get("changedToAt").getAsString()))) + "="
                    + response.get(var).getAsJsonObject().get("name").getAsString());
        }
        return result.toString();
    }
}