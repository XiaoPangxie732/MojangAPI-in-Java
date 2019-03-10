package cn.xiaopangxie732.mojang_api.status;

/**
 * List all status type.
 * @author XiaoPangxie732
 */
public enum StatusType {
	GREEN,
	YELLOW,
	RED,
	COULD_NOT_CONNECT;

	/**
	 * Let enum to string.
	 * @return E.g GREEN toString() is green.
	 * @author XiaoPangxie732
	 */
	@Override
	public String toString() {
		return name().toLowerCase().replace("_", " ");
	}
}