package tinyurl.dao;

import java.util.ArrayDeque;
import java.util.List;
import java.util.Queue;

import org.springframework.stereotype.Component;

/*
 * Responsibility - store x keys for each fetch; x is determined by number of keys dao returns
 */
@Component(value="keyStore")
public class KeyStore {
	
	Queue<Integer> keys;
	
	public KeyStore() {
		//arraydeque is better than linkedlist since once loaded size of deque is unlikely to
		//change, as x is fixed
		keys = new ArrayDeque<>();
	}
	
	public int getKey() {
		return keys.poll();
	}
	
	public boolean isEmpty() {
		return keys.isEmpty();
	}
	
	public void loadKeys(List<Integer> unusedKeys) {
		unusedKeys.forEach(e -> keys.offer(e));
	}
}
