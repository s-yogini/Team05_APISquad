package utilities;

public class TokenManager {
    private static String token;
    private static String userId;

    public static void setToken(String newToken) {
        token = newToken;
    }

    public static String getToken() {
        return token;
    }

    public static void setUserId(String newUserId) {
        userId = newUserId;
    }

    public static String getUserId() {
        return userId;
    }
}