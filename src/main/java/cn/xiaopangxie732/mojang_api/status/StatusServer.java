package cn.xiaopangxie732.mojang_api.status;

/**
 * This class lists all the servers that can check the status.
 * @since 0.0.1
 * @author XiaoPangxie732
 */
public enum StatusServer {
	MINECRAFT_NET,
	SESSION_MINECRAFT_NET,
	ACCOUNT_MOJANG_COM,
	AUTHSERVER_MOJANG_COM,
	SESSIONSERVER_MOJANG_COM,
	API_MOJANG_COM,
	TEXTURES_MINECRAFT_NET,
	MOJANG_COM;

	/**
	 * Let enum to string.<br>
	 * @return E.g MINECRAFT_NET toString() is minecraft.net
	 * @author XiaoPangxie732
	 */
	@Override
	public String toString() {
		return name().toLowerCase().replace("_", ".");
	}
}