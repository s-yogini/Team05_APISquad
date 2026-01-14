package utilities;

public class TokenManager {
	
	private static String token;

	// Method to set the token
	public static void setToken(String generatedToken) {
	token="eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJyb2JpbnJhbmphbkBnbWFpbC5jb20iLCJpYXQiOjE3NjgzNTM3ODMsImV4cCI6MTc2ODM4MjU4M30.P73JxrJcCnYlFS-RTTFejAniz-Oq9cUOTn2nVbsqpTxhlXES5h4iLwYvato8z-RwA8CcKTJrVkOsEbE0BLN6rQ";	
//		token = generatedToken;
	}

	// Method to get the token
	public static String getToken() {
		return token;
	}

}
