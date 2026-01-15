package payload;

import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import pojo.SkillMasterPojo;
import utilities.CommonUtils;
import utilities.ExcelUtils;

public class SkillMasterPayload extends CommonUtils {

    private Map<String, String> currentRow;
    private final Logger LOGGER = LogManager.getLogger(SkillMasterPayload.class);
    private final String sheetName = "SkillMaster";

    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

    public Map<String, Object> getDataFromExcel(String scenario)
            throws IOException, ParseException, InvalidFormatException {

        currentRow = CommonUtils.getCurrentRow(scenario, sheetName);

        Map<String, Object> skillDetails = new HashMap<>();
        SkillMasterPojo skillPojo = null;

        if (!scenario.contains("Get") && !scenario.contains("Delete")) {

            String randomSkillName = "Skill_" + new Random().nextInt(99999);
            String now = LocalDateTime.now().format(FORMATTER);

            ExcelUtils.writeCell(filePath, sheetName, scenario, "SkillName", randomSkillName);
            ExcelUtils.writeCell(filePath, sheetName, scenario, "CreationTime", now);
            ExcelUtils.writeCell(filePath, sheetName, scenario, "LastModTime", now);

            skillPojo = new SkillMasterPojo(randomSkillName, now, now);
        }

        LOGGER.info("Generated Skill Master Payload: " + skillPojo);

        skillDetails.put("skillPojo", skillPojo);
        skillDetails.put("currentRow", currentRow);

        return skillDetails;
    }
}
