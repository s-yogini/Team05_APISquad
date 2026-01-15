package requests;

import static io.restassured.RestAssured.given;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.testng.Assert;

import commons.Commons;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import payload.ProgramPayload;
import payload.UserPayload;
import pojo.UserPojo;
import utilities.CommonUtils;
import utilities.TokenManager;

public class UserRequest extends CommonUtils {
	    private List<Map<String, String>> excelData;
	    private Map<String, String> currentRow;
	    private Response response;
	    private UserPojo userPojo;
	    private static final String INVALID_PROGRAM_ID = "goi";
		private static final int INVALID_PROGRAM_NAME = 4567;
		private static final String INVALID_TOKEN = "jbnsjokfi";
	    
<<<<<<< HEAD
	
=======
			
>>>>>>> b0c9134626cd266304813f9f0423581915fbec23
		
		public RequestSpecification setAuth() {

			RestAssured.baseURI = CommonUtils.endpoints.getString("baseUrl");
<<<<<<< HEAD
=======
			TokenManager.setToken("");

			RestAssured.baseURI = endpoints.getString("baseUrl");

>>>>>>> b0c9134626cd266304813f9f0423581915fbec23
			return given()
					.header("Authorization", "Bearer " + TokenManager.getToken());
		}

		public  void createUser(String scenario) 
				throws IOException, InvalidFormatException, ParseException {

			Map<String, Object> UserDetails = new UserPayload().getDataFromExcel(scenario);
			if(UserDetails != null) {
				if(UserDetails.get("userPojo") != null) {
					this.userPojo = (UserPojo) UserDetails.get("userPojo");
				}
				if(UserDetails.get("currentRow") != null) {
					this.currentRow =  (Map<String, String>) UserDetails.get("currentRow");
				}
			}
		}  
	    
		public RequestSpecification buildRequest(RequestSpecification requestSpec) {
			if (requestSpec == null) {
				throw new IllegalStateException("RequestSpecification is not initialized.");
			}
			String scenarioName = currentRow.get("ScenarioName");
			if(scenarioName.contains("NoAuth")) {
				requestSpec = given();
			}
			else if(scenarioName.contains("InvalidToken")) {
				requestSpec = given()
						.header("Authorization", "Bearer " + INVALID_TOKEN);
			}
			else if(scenarioName.contains("InvalidBaseURI")) {

				RestAssured.baseURI = CommonUtils.endpoints.getString("invalidBaseUrl");

				return given()
						.header("Authorization", "Bearer " + TokenManager.getToken());
			}
			else if(scenarioName.contains("User") || scenarioName.contains("Post")) {
		        requestSpec.body(userPojo);
		    }

			// Set content type from currentRow
			requestSpec.contentType(currentRow.get("ContentType"));
			// Conditionally add the request body
			if (!scenarioName.contains("WithoutRequestBody")
					&& !scenarioName.contains("Post")
					&& !scenarioName.contains("Put")
					&& !scenarioName.contains("Get")
					&& !scenarioName.contains("Delete")
					) 
			{
				requestSpec.body(userPojo);
			}
			return requestSpec;
		}
		
	    
		public Response sendRequest(RequestSpecification requestSpec) {

			String endpoint = currentRow.get("EndPoint");
			response = CommonUtils.getResponse(requestSpec,endpoint);
			return response;
		}

		public int getStatusCode() {
			String expectedStatusCodeString = currentRow.get("StatusCode");
			int expectedStatusCode = (int) Double.parseDouble(expectedStatusCodeString); // Convert "201.0" to 201
			return expectedStatusCode;
		}

		public String getStatusText() {
			String scenarioName = currentRow.get("ScenarioName");
			if(!scenarioName.equalsIgnoreCase("Invalid Endpoint")&&
					(!scenarioName.equalsIgnoreCase("Mandatory"))
					&&(!scenarioName.equalsIgnoreCase("Full Details")))
			{
				String expectedStatusText = currentRow.get("StatusText");
				return expectedStatusText;
			}
			else
			{
				return null;
			}
		}
	
		public void updateUser(String scenario) throws Exception {
		    // 1. Load Excel data
		    Map<String, Object> userDetails = new UserPayload().getDataFromExcel(scenario);
		    if(userDetails == null || userDetails.get("userPojo") == null) {
		        throw new IllegalStateException("Failed to load user data from Excel for scenario: " + scenario);
		    }
		    
		    this.userPojo = (UserPojo) userDetails.get("userPojo");
		    this.currentRow = (Map<String, String>) userDetails.get("currentRow");
		    
		    // 2. Extract userId for path parameter (MANDATORY)
		    String userId = currentRow.get("userId");
		    if(userId == null || userId.trim().isEmpty()) {
		        throw new IllegalArgumentException("userId is mandatory for PUT /users/{userId} - check Excel data");
		    }}
		    
		public void getUsersByProgramId(String scenario) throws Exception {
		    // 1. Load Excel data
		    Map<String, Object> userDetails = new UserPayload().getDataFromExcel(scenario);
		    if(userDetails == null || userDetails.get("currentRow") == null) {
		        throw new IllegalStateException("Failed to load data from Excel for scenario: " + scenario);
		    }
		    
		    this.currentRow = (Map<String, String>) userDetails.get("currentRow");
		    
		    // 2. Extract programId as INTEGER for path/query parameter (MANDATORY)
		    String programIdStr = currentRow.get("programId");
		    if(programIdStr == null || programIdStr.trim().isEmpty()) {
		        throw new IllegalArgumentException("programId is mandatory for GET /users/program/{programId} - check Excel data");
		    }
		    
		    int programId;
		    try {
		        programId = Integer.parseInt(programIdStr.trim());
		    } catch (NumberFormatException e) {
		        throw new IllegalArgumentException("programId must be valid integer: " + programIdStr);
		    }}
		
		public void saveResponseBody(Response response) {
			String userId = response.jsonPath().getString("userId");
			Commons.setuserId(userId);
//			JSONschema response is same for both post and put requests
//			String schemaPath = config.getString("createProgramSchemaPath");
//			CommonUtils.validateResponseSchema(response,schemaPath);
		}
		
}
