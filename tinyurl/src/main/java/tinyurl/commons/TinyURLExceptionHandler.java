package tinyurl.commons;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class TinyURLExceptionHandler {

		@ExceptionHandler({TinyURLException.class})
		public ResponseEntity<TinyURLExceptionResponse> handleException(TinyURLException e) {
			TinyURLExceptionResponse exceptionResponse = new TinyURLExceptionResponse(e);
			return new ResponseEntity<TinyURLExceptionResponse>(exceptionResponse, exceptionResponse.getHttpStatusCode());
		}
}
