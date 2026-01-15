package stepDefinitions;

import io.cucumber.java.en.*;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import requests.UserLoginRequest;
import utilities.CommonUtils;
import static org.testng.Assert.assertEquals;

public class UserLoginSteps {

    UserLoginRequest loginReq = new UserLoginRequest();
    RequestSpecification requestSpec;
    Response response;

    @Given("Admin sets No Auth")
    public void admin_sets_no_auth() {
        requestSpec = loginReq.setNoAuth();
    }

    @Given("Admin sets authorization to Bearer Token")
    public void admin_sets_auth_bearer() {
        requestSpec = loginReq.setAuth();
    }

    @Given("Admin creates request with {string}")
    public void admin_creates_request(String scenario) throws Exception {
        loginReq.createLoginRequest(scenario);
        requestSpec = loginReq.buildRequest(requestSpec);
    }

    @When("Admin calls login HTTPS method with endpoint")
    public void admin_calls_login_https_method_with_endpoint() {
    	// Logging the request before sending
        System.out.println("------- REQUEST DETAILS -------");
    	response = loginReq.sendRequest(requestSpec);
    	// Logging the response after receiving
        System.out.println("------- RESPONSE DETAILS -------");
        response.then().log().all();
        loginReq.saveToken(response);
    }

    @Then("Admin validates response")
    public void admin_validates_response() {
        int expectedCode = loginReq.getStatusCode();
        assertEquals(response.getStatusCode(), expectedCode, "Status Code Mismatch!");

        // Validate Schema for successful login
        if (response.getStatusCode() == 200) {
            CommonUtils.validateResponseSchema(response, "schema/UserLogin.json");
        }

        loginReq.validateResponseMessage(loginReq.getStatusText(), response.getStatusCode(), "scenario", response);
        loginReq.saveToken(response);
     // 2. Pull the token from the TokenManager to verify it was saved
        String savedToken = utilities.TokenManager.getToken();
        System.out.println("--- TOKEN CAPTURED SUCCESSFULLY ---");
        System.out.println(savedToken);
        System.out.println("------------------------------------");
    
    }

    @Given("Admin creates forgot password request with {string}")
    public void admin_creates_forgot_request(String scenario) {
        // 1. Initialize headers FIRST
        requestSpec = loginReq.setNoAuth(); 
        
        // 2. Load the row data from the "Login" sheet
        loginReq.createGenericRequest(scenario, "Login"); 
        
        // 3. Attach the JSON body (MUST be after setNoAuth)
        requestSpec = loginReq.buildForgotPasswordRequest(requestSpec);
    }
 
    @Given("Admin sets authorization {string} and creates logout request")
    public void admin_logout_setup(String authType) {
        if (authType.equalsIgnoreCase("Bearer Token")) requestSpec = loginReq.setAuth();
        else requestSpec = loginReq.setNoAuth();
        loginReq.createGenericRequest("Admin logout", "Logout");
    }
    @Given("Admin sets authorization to Bearer Token")
    public void admin_sets_authorization_to_bearer_token() {
        // Uses the TokenManager.getToken() inside your UserLoginRequest.setAuth()
        requestSpec = loginReq.setAuth(); 
    }

    @Given("Admin creates reset password request with {string}")
    public void admin_creates_reset_password_request_with(String scenarioName) {
        // 1. Load data from Excel (Sheet: "Login")
        loginReq.createGenericRequest(scenarioName, "Login");
        
        // 2. Map Excel values to the Request Body
        requestSpec = loginReq.buildResetPasswordRequest(requestSpec);
    }
    @Given("Admin sets {string}")
    public void admin_sets_auth(String authType) {
        if (authType.equalsIgnoreCase("Bearer Token")) {
            requestSpec = loginReq.setAuth();
        } else {
            requestSpec = loginReq.setNoAuth();
        }
    }

    
}