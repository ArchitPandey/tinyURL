package tinyurl.service;

import java.io.IOException;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import tinyurl.commons.ExceptionCodes;
import tinyurl.commons.TinyURLException;
import tinyurl.dao.KeyStore;
import tinyurl.dao.TinyURLDAO;

@Service
public class TinyURLServiceImpl implements TinyURLService {
	
	@Autowired
	@Qualifier(value="keyStore")
	KeyStore keyStore;
	
	@Autowired
	TinyURLDAO tinyURLDAO;
	
	@Override
	public String getLongURL(int key) {
		
		String longURL = tinyURLDAO.getLongURL(key);
		
		if(Objects.isNull(longURL) || longURL.isEmpty() || longURL.equals("Expired")) {
			throw new TinyURLException(ExceptionCodes.TINYURL_EXPIRED);
		}
		
		return longURL;
	}

	@Override
	public int createTinyURL(String longURL) {
		try {
			int key = -1;
			//to avoid corruption of keyStore
			synchronized(this) {
				if(keyStore.isEmpty()) {
				keyStore.loadKeys(tinyURLDAO.fetchUnusedKeys());
				}
				key = keyStore.getKey();
			}
			return tinyURLDAO.createTinyURL(longURL, key);
		} catch (NumberFormatException | IOException e) {
			throw new TinyURLException(ExceptionCodes.ILLEGAL_APP_CONFIG);
		}
	}

	@Override
	public int deleteTinyURL(int key) {
		
		return tinyURLDAO.deleteTinyURL(key);
	}

}
