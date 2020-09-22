package tinyurl.commons;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import org.springframework.http.HttpStatus;

public class TinyURLExceptionResponse {
	
	private ZonedDateTime time;
	
	private HttpStatus httpStatusCode;
	
	private String message;
	
	TinyURLExceptionResponse(TinyURLException e) {
		this.message = e.getMessage();
		this.time = LocalDateTime.now().atZone(ZoneId.of("Asia/Kolkata"));
		this.httpStatusCode = e.getExceptionCode().getHttpStatus();
		if(this.httpStatusCode == null) {
			this.httpStatusCode = HttpStatus.NOT_FOUND;
		}
	}

	public ZonedDateTime getTime() {
		return time;
	}

	public void setTime(ZonedDateTime time) {
		this.time = time;
	}

	public HttpStatus getHttpStatusCode() {
		return httpStatusCode;
	}

	public void setHttpStatusCode(HttpStatus httpStatusCode) {
		this.httpStatusCode = httpStatusCode;
	}
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}


	@Override
	public String toString() {
		return "TinyURLExceptionResponse [message=" + message + ", time=" + time + ", httpStatusCode=" + httpStatusCode
				+ "]";
	}
	
}
