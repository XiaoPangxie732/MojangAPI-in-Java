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

/**
 * The class to get all blocked servers.
 * @author XiaoPangxie732
 * @since 0.0.5
 */
public class Servers {
	/**
	 * Get all blocked servers.
	 * @return String array of all SHA1 hash values.
	 * @since 0.0.5
	 */
	public static String[] getBlockedServers() {
		Status.ensureAvailable(StatusServer.SESSIONSERVER_MOJANG_COM);
		return Net.getConnection("https://sessionserver.mojang.com/blockedservers")
				.split("\n");
	}
}