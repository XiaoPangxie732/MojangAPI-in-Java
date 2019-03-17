package cn.xiaopangxie732.mojang_api;

import cn.xiaopangxie732.mojang_api.util.Net;
import com.google.gson.Gson;

import java.text.DateFormat;
import java.util.Date;
import java.util.Properties;

/**
 * Incomplete
 * @author XiaoPangxie732
 */
public class UUIDName {

    private static Gson json = new Gson();

    public static String NameHistory(String uuid) {
        String url = "https://api.mojang.com/user/profiles/" + uuid + "/names";
        Properties[] response = json.fromJson(Net.getConnection(url), Properties[].class);
        StringBuilder result = new StringBuilder("Original: " + response[0].getProperty("name"));
        for(int var = 1; var < response.length; ++var) {
            result.append("\n" + DateFormat.getDateInstance().format(new Date(Long.parseLong(response[var].getProperty("changedToAt")))) + ": " + response[var].getProperty("name"));
        }
        return result.toString();
    }
}