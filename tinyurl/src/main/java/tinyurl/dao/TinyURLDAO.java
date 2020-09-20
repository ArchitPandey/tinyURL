package tinyurl.dao;

import java.io.IOException;
import java.util.List;

public interface TinyURLDAO {
	
	public String getLongURL(int key);
	
	public int deleteTinyURL(int key);
	
	public int createTinyURL(String longURL, int key) throws NumberFormatException, IOException;
	
	public List<Integer> fetchUnusedKeys() throws NumberFormatException, IOException;
	
}
