package tinyurl.rest;

import org.springframework.beans.factory.annotation.Autowired;
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
	
	@GetMapping("/tinyurl/{tinyurl}")
	public String getLongURL(@PathVariable("tinyurl") int tinyurl) throws TinyURLException {
		
		return tinyURLService.getLongURL(tinyurl);
	}
	
	@PostMapping("/tinyurl")
	public int createTinyURL(@RequestBody String longURL) {
		
		return tinyURLService.createTinyURL(longURL);
	}
	
	@DeleteMapping("/tinyurl/{tinyURL}") 
	public int deleteTinyURL(@PathVariable("tinyURL") int key){
		
		return tinyURLService.deleteTinyURL(key);
	}
	
}
