package cn.xiaopangxie732.mojang_api.exceptions;

public class InvalidServerException extends RuntimeException {

	private static final long serialVersionUID = -6624733586870154037L;

	public InvalidServerException(String cause) {
		super(cause);
	}
}