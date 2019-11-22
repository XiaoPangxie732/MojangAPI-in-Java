package cn.xiaopangxie732.mojang_api;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import cn.xiaopangxie732.mojang_api.Status.StatusServer;
import cn.xiaopangxie732.mojang_api.util.Net;

/**
 * Get statistics on the sales.
 * @author XiaoPangxie732
 * @since 0.0.5
 */
public class Statistics {
	private JsonObject response;
	private JsonObject request = new JsonObject();
	/**
	 * All valid items.
	 * @author XiaoPangxie732
	 * @since 0.0.5
	 */
	public enum Items {
		ITEM_SOLD_MINECRAFT,
		PREPAID_CARD_REDEEMED_MINECRAFT, 
		ITEM_SOLD_COBALT, 
		ITEM_SOLD_SCROLLS;
		@Override
		public String toString() {
			return name().toLowerCase();
		}
	}
	/**
	 * Construct a {@code Statistics} class.
	 * @param items All items need to get sale statistics.
	 * @since 0.0.5
	 */
	public Statistics(Items... items) {
		JsonArray arr = new JsonArray();
		for(Items i : items) {
			arr.add(i.toString());
		}
		request.add("metricKeys", arr);
	}
	/**
	 * Get sale statistics
	 * @return this object that got sale statistics.
	 * @since 0.0.5
	 */
	public Statistics get() {
		Status.ensureAvailable(StatusServer.API_MOJANG_COM);
		response = JsonParser.parseString(Net.postConnection("https://api.mojang.com/orders/statistics", 
				"application/json", request.toString())).getAsJsonObject();
		return this;
	}
	/**
	 * Get total sale number
	 * @return The total sale number.
	 * @since 0.0.5
	 */
	public int getTotalSaleNumber() {
		return response.get("total").getAsInt();
	}
	/**
	 * Get last 24h sale number
	 * @return The last 24h sale number.
	 * @since 0.0.5
	 */
	public int getLast24hSaleNumber() {
		return response.get("last24h").getAsInt();
	}
	/**
	 * Get sale velocity per seconds.
	 * @return The sale velocity per seconds.
	 * @since 0.0.5
	 */
	public float getSaleVelocityPerSeconds() {
		return response.get("saleVelocityPerSeconds").getAsFloat();
	}
}