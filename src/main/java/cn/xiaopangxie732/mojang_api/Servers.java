package cn.xiaopangxie732.mojang_api;

import cn.xiaopangxie732.mojang_api.util.Net;

/**
 * The class to get all blocked servers.
 * @author XiaoPangxie732
 * @since 0.0.5
 */
public class Servers {
	/**
	 * Get all blocked servers.
	 * @return String array of all SHA1 hashes.
	 * @since 0.0.5
	 */
	public String[] getBlockedServers() {
		return Net.getConnection("https://sessionserver.mojang.com/blockedservers")
				.split("\n");
	}
}