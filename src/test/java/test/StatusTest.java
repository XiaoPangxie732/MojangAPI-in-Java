package test;

import cn.xiaopangxie732.mojang_api.Status;
import cn.xiaopangxie732.mojang_api.Status.StatusServer;

public class StatusTest {
	public static void main(String[] args) {
		Status stat = new Status();
		System.out.println(stat.getAllStatus(true));
		System.out.println(stat.getAllStatus(false));
		System.out.println(stat.getAllStatus());
		stat.fresh();
		System.out.println(stat.getStatus(StatusServer.MOJANG_COM));
		System.out.println(stat.getStatus(StatusServer.ACCOUNT_MOJANG_COM));
		System.out.println(stat.getStatus(StatusServer.API_MOJANG_COM));
		System.out.println(stat.getStatus(StatusServer.AUTHSERVER_MOJANG_COM));
		System.out.println(stat.getStatus(StatusServer.SESSIONSERVER_MOJANG_COM));
		stat.fresh();
		System.out.println(stat.getStatus(StatusServer.MINECRAFT_NET));
		System.out.println(stat.getStatus(StatusServer.SESSION_MINECRAFT_NET));
		System.out.println(stat.getStatus(StatusServer.TEXTURES_MINECRAFT_NET));
	}
}
