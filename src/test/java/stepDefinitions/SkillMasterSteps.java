package stepDefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import java.util.List;
import java.util.Map;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import pojo.SkillMasterPojo;
import requests.SkillMasterRequests;
import utilities.TokenManager;

public class SkillMasterSteps {

    private SkillMasterRequests skillReq;
    private RequestSpecification req;
    private Response response;

    // 🔥 API CHAINING VARIABLES
    private int skillId;
    private String skillName;
    private SkillMasterPojo skillPojo;

    // ============================================================
    // BACKGROUND: AUTH
    // ============================================================

    @Given("Admin Authorization to Bearer token")
    public void admin_authorization_to_bearer_token() {
        TokenManager.setToken("init");
        skillReq = new SkillMasterRequests();
        req = skillReq.setAuth();
    }

    // ============================================================
    // POST (Create Skill)
    // ============================================================

    @Given("Admin creates POST Request for the LMS API endpoint")
    public void admin_creates_post_request_for_the_lms_api_endpoint() throws Exception {
        skillReq = new SkillMasterRequests();

        String scenarioName = "Create Skill";  // Excel ScenarioName

        skillReq.createSkill(scenarioName);   // Load Excel + generate payload
        req = skillReq.setAuth();             // Set token + baseURI
        req = skillReq.buildRequest(req);     // Add headers + body
    }

    @When("Admin sends HTTPS Request and request Body with mandatory fields")
    public void admin_sends_https_request_and_request_body_with_mandatory_fields() {
        response = skillReq.sendRequest(req);
        response.then().log().all();

        // 🔥 Capture skillId + skillName for chaining
        skillId = response.jsonPath().getInt("skillId");
        skillName = response.jsonPath().getString("skillName");
    }

    @Then("Admin receives {int} Created Status with response body")
    public void admin_receives_created_status_with_response_body(Integer expectedStatusCode) {
        assertThat(response.getStatusCode(), equalTo(expectedStatusCode));
        assertThat(skillName, startsWith("Skill_"));
        assertThat(skillId, greaterThan(0));
    }

    // ============================================================
    // GET ALL SKILLS
    // ============================================================

    @Given("Admin creates GET Request for the LMS API endpoint")
    public void admin_creates_get_request_for_the_lms_api_endpoint() {
        skillReq = new SkillMasterRequests();
        req = skillReq.setAuth();
    }

    @When("Admin sends HTTPS Request")
    public void admin_sends_https_request() {
        response = skillReq.sendGetAllSkills(req);
        response.then().log().all();
    }

    @Then("Admin receives 200 Status with response body\\(showing all the list of skills)")
    public void admin_receives_200_status_with_response_body_showing_all_the_list_of_skills() {
        assertThat(response.getStatusCode(), equalTo(200));

        List<Map<String, Object>> skills = response.jsonPath().getList("$");
        assertThat(skills.size(), greaterThan(0));

        assertThat(skills.get(0), hasKey("skillId"));
        assertThat(skills.get(0), hasKey("skillName"));
        assertThat(skills.get(0), hasKey("creationTime"));
        assertThat(skills.get(0), hasKey("lastModTime"));
    }

    // ============================================================
    // GET SKILL BY NAME (VALID)
    // ============================================================

    @When("Admin sends HTTPS Request with SkillMasterName")
    public void admin_sends_https_request_with_skill_master_name() {
        response = skillReq.getSkillByName(req, skillName);
        response.then().log().all();
    }

    @Then("Admin receives 200 Status with response body")
    public void admin_receives_200_status_with_response_body() {
        assertThat(response.getStatusCode(), equalTo(200));
        assertThat(response.jsonPath().getString("skillName"), equalTo(skillName));
    }

    // ============================================================
    // GET SKILL BY NAME (INVALID)
    // ============================================================

    @When("Admin sends HTTPS Request with invalid SkillMasterName")
    public void admin_sends_https_request_with_invalid_skill_master_name() {
        response = skillReq.getSkillByName(req, "InvalidSkill_99999");
        response.then().log().all();
    }

    @Then("Admin receives 404 Not Found Status with message")
    public void admin_receives_404_not_found_status_with_message() {
        assertThat(response.getStatusCode(), equalTo(404));
        assertThat(response.jsonPath().getString("message"), containsString("Not Found"));
    }

    // ============================================================
    // PUT UPDATE SKILL (VALID)
    // ============================================================

    @Given("Admin creates  PUT Request for the LMS API endpoint")
    public void admin_creates_put_request_for_the_lms_api_endpoint() {
        skillReq = new SkillMasterRequests();
        req = skillReq.setAuth();
    }

    @When("Admin sends HTTPS Request and  request Body with mandatory")
    public void admin_sends_https_request_and_request_body_with_mandatory_for_put() {

        String updatedName = skillName + "_Updated";

        skillPojo = new SkillMasterPojo(
                updatedName,
                response.jsonPath().getString("creationTime"),
                response.jsonPath().getString("lastModTime")
        );

        response = skillReq.updateSkill(req, skillPojo, skillId);
        response.then().log().all();

        skillName = updatedName; // update for chaining
    }

    @Then("Admin receives 200 Status with updated response body.")
    public void admin_receives_200_status_with_updated_response_body() {
        assertThat(response.getStatusCode(), equalTo(200));
        assertThat(response.jsonPath().getString("skillName"), equalTo(skillName));
    }

    // ============================================================
    // PUT UPDATE SKILL (INVALID)
    // ============================================================

    @When("Admin sends HTTPS Request and request Body with mandatory with wrong skillID")
    public void admin_sends_https_request_and_request_body_with_mandatory_with_wrong_skill_id() {

        skillPojo = new SkillMasterPojo(
                skillName + "_WrongID",
                "2026-01-14T02:14",
                "2026-01-14T02:14"
        );

        response = skillReq.updateSkill(req, skillPojo, 999999);
        response.then().log().all();
    }

    @Then("Admin receives 400 Bad Request with error:Bad Request")
    public void admin_receives_400_bad_request_with_error_bad_request() {
        assertThat(response.getStatusCode(), equalTo(400));
    }

    // ============================================================
    // DELETE SKILL (VALID)
    // ============================================================

    @Given("Admin creates DELETE Request for the LMS API endpoint")
    public void admin_creates_delete_request_for_the_lms_api_endpoint() {
        skillReq = new SkillMasterRequests();
        req = skillReq.setAuth();
    }

    @When("Admin sends HTTPS Request")
    public void admin_sends_https_request_for_delete() {
        response = skillReq.deleteSkill(req, skillId);
        response.then().log().all();
    }

    @Then("Admin receives 200 Status")
    public void admin_receives_200_status() {
        assertThat(response.getStatusCode(), equalTo(200));
    }

    // ============================================================
    // DELETE SKILL (INVALID)
    // ============================================================

    @When("Admin sends HTTPS Request with invalid SkillId")
    public void admin_sends_https_request_with_invalid_skill_id() {
        response = skillReq.deleteSkill(req, 999999);
        response.then().log().all();
    }

    @Then("Admin receives 404 Error with response body no record found with skillId")
    public void admin_receives_404_error_with_response_body_no_record_found_with_skill_id() {
        assertThat(response.getStatusCode(), equalTo(404));
        assertThat(response.jsonPath().getString("message"), containsString("no record found"));
    }
}
