package com.siddhesh.aiassessmentagent.model;

import java.util.List;

public class AssessmentResponse {

    private List<String> jobSkills;
    private List<String> resumeSkills;
    private List<MatchingSkill> matchingSkills;
    private List<String> missingSkills;
    private String gapAnalysis;
    private List<LearningItem> learningPlan;
    private String estimatedTime;



    public List<String> getJobSkills() { return jobSkills; }
    public void setJobSkills(List<String> jobSkills) { this.jobSkills = jobSkills; }

    public List<String> getResumeSkills() { return resumeSkills; }
    public void setResumeSkills(List<String> resumeSkills) { this.resumeSkills = resumeSkills; }

    public List<MatchingSkill> getMatchingSkills() { return matchingSkills; }
    public void setMatchingSkills(List<MatchingSkill> matchingSkills) { this.matchingSkills = matchingSkills; }

    public List<String> getMissingSkills() { return missingSkills; }
    public void setMissingSkills(List<String> missingSkills) { this.missingSkills = missingSkills; }

    public String getGapAnalysis() { return gapAnalysis; }
    public void setGapAnalysis(String gapAnalysis) { this.gapAnalysis = gapAnalysis; }

    public List<LearningItem> getLearningPlan() { return learningPlan; }
    public void setLearningPlan(List<LearningItem> learningPlan) { this.learningPlan = learningPlan; }

    public String getEstimatedTime() { return estimatedTime; }
    public void setEstimatedTime(String estimatedTime) { this.estimatedTime = estimatedTime; }



    public static class MatchingSkill {
        private String name;
        private String level;

        public String getName() { return name; }
        public void setName(String name) { this.name = name; }

        public String getLevel() { return level; }
        public void setLevel(String level) { this.level = level; }
    }

    public static class LearningItem {
        private String skill;
        private String time;
        private String resources;

        public String getSkill() { return skill; }
        public void setSkill(String skill) { this.skill = skill; }

        public String getTime() { return time; }
        public void setTime(String time) { this.time = time; }

        public String getResources() { return resources; }
        public void setResources(String resources) { this.resources = resources; }
    }
}
