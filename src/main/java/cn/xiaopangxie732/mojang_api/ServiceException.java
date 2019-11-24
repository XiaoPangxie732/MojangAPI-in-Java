package cn.xiaopangxie732.mojang_api;

/**
 * Throw when couldn't connect to service server
 * @author XiaoPangxie732
 * @since 0.1
 */
public class ServiceException extends RuntimeException {
	private static final long serialVersionUID = -5125380999323432788L;

	public ServiceException() {
		super();
	}
	public ServiceException(String message, Throwable cause) {
		super(message, cause);
	}
	public ServiceException(String message) {
		super(message);
	}
}