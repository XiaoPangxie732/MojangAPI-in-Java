package test;

import cn.xiaopangxie732.mojang_api.UUIDName;
import cn.xiaopangxie732.mojang_api.UserName;

public class UUIDNameTest {
	public static void main(String[] args) {
		System.out.println(UUIDName.nameHistory(UserName.UUIDAtNow("Xiao_Pang_xie")));
		System.out.println(UUIDName.getSkinURL(UserName.UUIDAtNow("Xiao_Pang_xie")));
		UUIDName.storeSkinImageToDesktop(UserName.UUIDAtNow("TRCRedstoner"));
	}
}