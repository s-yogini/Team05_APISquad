package requests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import payload.UserLoginPayload;
import pojo.UserLoginPojo;
import utilities.CommonUtils;
import utilities.TokenManager;

public class UserLoginRequest extends CommonUtils {

    private UserLoginPojo userLoginPojo;

    public RequestSpecification setNoAuth() {
        return RestAssured.given()
        		.log().all() // This prints EVERYTHING (URL, Headers, Body) to the console
                .header("Content-Type", "application/json")
                .header("Accept", "application/json"); // Specifically ask for JSON
    }

    public RequestSpecification setAuth() {
        return RestAssured.given()
                .header("Authorization", "Bearer " + TokenManager.getToken())
                .header("Content-Type", "application/json");
    }

    public void createLoginRequest(String scenario) throws Exception {
        userLoginPojo = UserLoginPayload.getLoginPayload(scenario);
        currentRow = CommonUtils.getCurrentRow(scenario, "Login");
    }

    public void createGenericRequest(String scenario, String sheetName) {
        currentRow = CommonUtils.getCurrentRow(scenario, sheetName);
    }

    public RequestSpecification buildRequest(RequestSpecification reqSpec) {
        if (userLoginPojo != null) {
            reqSpec.body(userLoginPojo);
        }
        return reqSpec;
    }

    public Response sendRequest(RequestSpecification reqSpec) {
        String endpoint = currentRow.get("EndPoint");
        return CommonUtils.getResponse(reqSpec, endpoint);
    }



    public int getStatusCode() {
    	String statusCodeStr = currentRow.get("StatusCode");
        
        // This handles "200.0" by converting to double first, 
        // then dropping the decimal to return 200 as an int.
        return (int) Double.parseDouble(statusCodeStr);
    }

    public String getStatusText() {
        return currentRow.get("StatusText");
    }
    public void saveToken(Response response) {
        if (response.getStatusCode() == 200) {
            // Extract token from the response JSON body
            String extractedToken = response.jsonPath().getString("token");
            TokenManager.setToken(extractedToken);
            TokenManager.setUserId(response.jsonPath().getString("userId"));
            System.out.println("TOKEN AUTO-SAVED: " + TokenManager.getToken());
        }
        
        
        
        
    
    }
    public RequestSpecification buildForgotPasswordRequest(RequestSpecification reqSpec) {
        java.util.Map<String, String> body = new java.util.HashMap<>();
        
        // CRITICAL: The string "userLoginEmailId" must match the header in Row 1 of your Excel
        String email = currentRow.get("userLoginEmailId");
        
        body.put("userLoginEmailId", email);
        
        // Log for debugging:
        System.out.println("Building request with email: " + email);
        
        return reqSpec.body(body);
    }
    public RequestSpecification buildResetPasswordRequest(RequestSpecification reqSpec) {
        java.util.Map<String, String> body = new java.util.HashMap<>();
        
        // Pulling from currentRow (populated by createGenericRequest)
        String email = currentRow.get("userLoginEmailId");
        String password = currentRow.get("userPassword");
        
        if (email == null || password == null) {
            throw new RuntimeException("Excel data missing for scenario: " + email);
        }

        body.put("userLoginEmailId", email);
        body.put("userPassword", password);
        
        return reqSpec.body(body);
    }
    
    
    
}