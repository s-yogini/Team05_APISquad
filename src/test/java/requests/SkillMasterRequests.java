package requests;

import static io.restassured.RestAssured.given;

import java.util.Map;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import payload.SkillMasterPayload;
import pojo.SkillMasterPojo;
import utilities.CommonUtils;
import utilities.TokenManager;

public class SkillMasterRequests extends CommonUtils {

    private Map<String, String> currentRow;
    private SkillMasterPojo skillPojo;

    public RequestSpecification setAuth() {
        RestAssured.baseURI = baseURI;
        return given().header("Authorization", "Bearer " + TokenManager.getToken());
    }

    public void createSkill(String scenarioName) throws Exception {
        SkillMasterPayload payload = new SkillMasterPayload();
        Map<String, Object> data = payload.getDataFromExcel(scenarioName);

        this.skillPojo = (SkillMasterPojo) data.get("skillPojo");
        this.currentRow = (Map<String, String>) data.get("currentRow");
    }

    public RequestSpecification buildRequest(RequestSpecification req) {
        return req.header("Content-Type", currentRow.get("ContentType"))
                  .header("Accept", currentRow.get("ContentType"))
                  .body(skillPojo);
    }

    public Response sendRequest(RequestSpecification req) {
        return req.when().post(currentRow.get("EndPoint"));
    }

    public Response sendGetAllSkills(RequestSpecification req) {
        return req.when().get("/allSkillMaster");
    }

    public Response getSkillByName(RequestSpecification req, String skillName) {
        return req.when().get("/skills/" + skillName);
    }

    public Response updateSkill(RequestSpecification req, SkillMasterPojo payload, int skillId) {
        return req.body(payload).when().put("/updateSkills/" + skillId);
    }

    public Response deleteSkill(RequestSpecification req, int skillId) {
        return req.when().delete("/deletebySkillId/" + skillId);
    }

    public String getExpectedStatusText() {
        return currentRow.get("StatusText");
    }
}
