package cn.xiaopangxie732.mojang_api.exceptions;

/**
 * Thrown when server is invalid or <code>null</code>.
 * @author XiaoPangxie732
 */
public class InvalidServerException extends RuntimeException {

	private static final long serialVersionUID = -6624733586870154037L;

	public InvalidServerException(String cause) {
		super(cause);
	}
}