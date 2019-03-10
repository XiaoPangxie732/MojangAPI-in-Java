package cn.xiaopangxie732.mojang_api.exceptions;

/**
 * Throw when the user name on that timestamp has not changed, or the username is invalid.
 * @author XiaoPangxie732
 */
public class UsernameOrTimestampInvalidException extends RuntimeException {

	private static final long serialVersionUID = -3145228876580727833L;

	public UsernameOrTimestampInvalidException() {
		super();
	}

	public UsernameOrTimestampInvalidException(String message) {
		super(message);
	}
}