package cn.xiaopangxie732.mojang_api;

public enum StatusType {
	GREEN,
	YELLOW,
	RED;
	@Override
	public String toString() {
		return name().toLowerCase();
	}
}
