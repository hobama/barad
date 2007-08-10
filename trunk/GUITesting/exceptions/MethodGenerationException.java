package exceptions;

public class MethodGenerationException extends Exception{
	private static final long serialVersionUID = 1;
	public MethodGenerationException() {
		super();
	}
	public MethodGenerationException(String message, Throwable cause) {
		super(message, cause);
	}
	public MethodGenerationException(String message) {
		super(message);
	}
	public MethodGenerationException(Throwable cause) {
		super(cause);
	}
}
