package utilities;

public class TokenManager {
    private static String token;
    private static String userId;


	// Method to set the token
	public static void setToken(String generatedToken) {
	token="eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJyb2JpbnJhbmphbkBnbWFpbC5jb20iLCJpYXQiOjE3NjgzNTM3ODMsImV4cCI6MTc2ODM4MjU4M30.P73JxrJcCnYlFS-RTTFejAniz-Oq9cUOTn2nVbsqpTxhlXES5h4iLwYvato8z-RwA8CcKTJrVkOsEbE0BLN6rQ";	
//		token = generatedToken;
	}

	// Method to get the token
	public static String getToken() {
		token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZWFtNUBnbWFpbC5jb20iLCJpYXQiOjE3NjgzOTkyODYsImV4cCI6MTc2ODQyODA4Nn0.uNz099lY0MWRkorogrtwWwucp9WzC_D84Vgnjh4a-SLxiDzgE425KohuPVWo5cwxYC7AH224CNQ2UCDNDtC6Kw";
		return token;
	}


    public static void setUserId(String newUserId) {
        userId = newUserId;
    }

    public static String getUserId() {
        return userId;
    }
}