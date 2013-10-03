package se.enbohms.hhcib.service.api;

/**
 * Represents an exception when a notification fails
 */
public class NotificationException extends Exception {

	private static final long serialVersionUID = 8613136311820967789L;

	public NotificationException(Throwable cause) {
		super(cause);
	}
}
