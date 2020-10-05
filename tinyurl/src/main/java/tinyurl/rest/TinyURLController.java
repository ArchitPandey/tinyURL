package tinyurl.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
	public String createTinyURL(@RequestBody String longURL) {
		
		int key = tinyURLService.createTinyURL(longURL);
		return Integer.toString(key);
	}
	
	@DeleteMapping("/{tinyURL}") 
	public String deleteTinyURL(@PathVariable("tinyURL") int key){
		
		int rowsAffected =  tinyURLService.deleteTinyURL(key);
		return Integer.toString(rowsAffected);
	}
	
}
