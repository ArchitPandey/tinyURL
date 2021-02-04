package tinyurl.rest;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import tinyurl.commons.TinyURLException;
import tinyurl.service.TinyURLService;

//using rest controller means @ReponseBody is already applied to methods
@RestController
public class TinyURLController {
	
	@Autowired
	public TinyURLService tinyURLService;
	
	@GetMapping("/{tinyurl}")
	public ResponseEntity<Void> getLongURL(@PathVariable("tinyurl") int tinyurl) throws TinyURLException {
		
		String longURL = tinyURLService.getLongURL(tinyurl);
		
		HttpHeaders httpHeader = new HttpHeaders();
		httpHeader.add(HttpHeaders.LOCATION, longURL);
		
		return new ResponseEntity<Void>(httpHeader, HttpStatus.TEMPORARY_REDIRECT);
	}
	
	@PostMapping("/")
	public ResponseEntity<String> createTinyURL(@RequestHeader(name=HttpHeaders.ORIGIN, required=false) String origin, @RequestBody String longURL) {
		
		if(Objects.isNull(origin)) {
			String body = "origin header not found";
			return new ResponseEntity<String>(body, HttpStatus.BAD_REQUEST);
		}
		
		int key = tinyURLService.createTinyURL(longURL);
		MultiValueMap<String, String> headers = new HttpHeaders();
		headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, origin);
		return new ResponseEntity<String>(Integer.toString(key), headers, HttpStatus.OK);
	}
	
	@DeleteMapping("/{tinyURL}") 
	public String deleteTinyURL(@PathVariable("tinyURL") int key){
		
		int rowsAffected =  tinyURLService.deleteTinyURL(key);
		return Integer.toString(rowsAffected);
	}
	
}
