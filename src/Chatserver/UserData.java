package Chatserver;
import java.util.HashMap;
import java.util.Map;
import java.io.*;
public class UserData {

	private static UserData instance = new UserData();
	
	/**
	 * Map of usernames to their password hashes.
	 */
	private Map<String, Integer> userMap = new HashMap<>();
	
	public static UserData getInstance(){
		return instance;
	}
	
	// This class is a singleton. Call getInstance() instead.
	private UserData(){}
	
	public boolean isUsernameTaken(String username){
		return userMap.containsKey(username);
	}
	
	public void registerUser(String username, String password){
		int passwordHash = password.hashCode();
		userMap.put(username, passwordHash);
	}

	public boolean isLoginCorrect(String username, String password) {
		
		// username isn't registered
		if(!userMap.containsKey(username)){
			return false;
		}
		
		int passwordHash = password.hashCode();
		int storedPasswordHash = userMap.get(username);
		
		return passwordHash == storedPasswordHash;
	}
}