package pojo;

public class SkillMasterPojo {

    private int skillId;
    private String skillName;
    private String creationTime;
    private String lastModTime;

    // Constructor for POST (no skillId)
    public SkillMasterPojo(String skillName, String creationTime, String lastModTime) {
        this.skillName = skillName;
        this.creationTime = creationTime;
        this.lastModTime = lastModTime;
    }

    // Constructor for PUT/GET/DELETE (with skillId)
    public SkillMasterPojo(int skillId, String skillName, String creationTime, String lastModTime) {
        this.skillId = skillId;
        this.skillName = skillName;
        this.creationTime = creationTime;
        this.lastModTime = lastModTime;
    }

    // Getters
    public int getSkillId() { return skillId; }
    public String getSkillName() { return skillName; }
    public String getCreationTime() { return creationTime; }
    public String getLastModTime() { return lastModTime; }

    // Setters
    public void setSkillId(int skillId) { this.skillId = skillId; }
    public void setSkillName(String skillName) { this.skillName = skillName; }
    public void setCreationTime(String creationTime) { this.creationTime = creationTime; }
    public void setLastModTime(String lastModTime) { this.lastModTime = lastModTime; }

    @Override
    public String toString() {
        return "SkillMasterPojo{" +
                "skillId=" + skillId +
                ", skillName='" + skillName + '\'' +
                ", creationTime='" + creationTime + '\'' +
                ", lastModTime='" + lastModTime + '\'' +
                '}';
    }
}
