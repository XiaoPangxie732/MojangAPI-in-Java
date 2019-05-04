package test;

import cn.xiaopangxie732.mojang_api.UUIDName;
import cn.xiaopangxie732.mojang_api.UserName;

public class UUIDNameTest {
	public static void main(String[] args) {
		System.out.println(UUIDName.NameHistory(UserName.UUIDAtNow("Xiao_Pang_xie")));
	}
}