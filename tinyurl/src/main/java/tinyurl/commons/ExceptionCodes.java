package tinyurl.commons;

import org.springframework.http.HttpStatus;

public enum ExceptionCodes {

	
	WEBSITE_NOT_FOUND("The requested website was not found", HttpStatus.NOT_FOUND),
	TINYURL_EXPIRED("This tinyURL has expired", HttpStatus.BAD_REQUEST),
	USER_LIMIT_EXCEEDED("This user has exceeded his quota of create tinyURL requests", HttpStatus.BAD_REQUEST),
	ILLEGAL_APP_CONFIG("Illegal App Configuration", HttpStatus.INTERNAL_SERVER_ERROR);
	ExceptionCodes(String desc, HttpStatus status) {
		this.exceptionDesc = desc;
	}
	
	private String exceptionDesc;
	private HttpStatus status;
	
	public String getExceptionDesc() {
		return this.exceptionDesc;
	}
	
	public HttpStatus getHttpStatus() {
		return this.status;
	}
}
