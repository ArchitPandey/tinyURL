package tinyurl.commons;

public class TinyURLException extends RuntimeException {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ExceptionCodes exceptionCode;

	public TinyURLException(ExceptionCodes code) {
		super(code.getExceptionDesc());
		this.exceptionCode = code;
	}
	
	TinyURLException(ExceptionCodes code, Throwable cause) {
		super(code.getExceptionDesc(), cause);	
	}
	
	public String toString() {
		return super.getMessage();
	}
	
	public String getMessage() {
		return super.getMessage();
	}
	
	public ExceptionCodes getExceptionCode() {
		return this.exceptionCode;
	}
	
}
