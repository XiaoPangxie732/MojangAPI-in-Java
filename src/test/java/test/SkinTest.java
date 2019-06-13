package test;

import cn.xiaopangxie732.mojang_api.Skin;
import cn.xiaopangxie732.mojang_api.UserName;
import cn.xiaopangxie732.mojang_api.util.Auth;

public class SkinTest {
	public static void main(String[] args) {
		try {
			Skin.resetSkin(Auth.getAccessToken("xiaopangabc@icloud.com", "Qazplm10293847562"), 
					UserName.UUIDAtNow("Pangxie_helper"));
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
	}
}