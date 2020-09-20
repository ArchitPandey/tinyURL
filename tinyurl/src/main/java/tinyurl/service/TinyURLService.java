package tinyurl.service;

public interface TinyURLService {
	
	public String getLongURL(int key);
	
	public int createTinyURL(String longURL);
	
	public int deleteTinyURL(int key);
}
