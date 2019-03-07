package www.loujie.com.excel;

public class ExcelException extends RuntimeException {

	private static final long serialVersionUID = 3583566093089790852L;

	public ExcelException() {
		super();
	}

	public ExcelException(String message) {
		super(message);
	}

	public ExcelException(Throwable cause) {
		super(cause);
	}

	public ExcelException(String message, Throwable cause) {
		super(message, cause);
	}
}