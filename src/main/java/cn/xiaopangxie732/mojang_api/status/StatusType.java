package cn.xiaopangxie732.mojang_api.status;

/**
 * This class lists all status type.
 * @author XiaoPangxie732
 */
public enum StatusType {
	GREEN,
	YELLOW,
	RED,
	COULD_NOT_CONNECT;

	/**
	 * The StatusType toString() method.
	 * @return E.g GREEN toString() is green.
	 * @author XiaoPangxie732
	 */
	@Override
	public String toString() {
		return name().toLowerCase();
	}
}