package com.siddhesh.aiassessmentagent.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.siddhesh.aiassessmentagent.model.AssessmentRequest;
import com.siddhesh.aiassessmentagent.service.AIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.siddhesh.aiassessmentagent.repository.AssessmentRepository;
import com.siddhesh.aiassessmentagent.entity.Assessment;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class AssessmentController {

    @Autowired
    private AIService aiService;

    @Autowired
    private AssessmentRepository assessmentRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();

    //  ASSESS
    @PostMapping("/assess")
    public Map<String, Object> assess(@RequestBody AssessmentRequest request) {

        String prompt = """
You are an AI career assistant.

Analyze the job description and resume.

Return ONLY valid JSON in this format:

{
  "jobSkills": [],
  "resumeSkills": [],
  "matchingSkills": [
    {"name": "", "level": ""}
  ],
  "missingSkills": [],
  "gapAnalysis": "",
  "learningPlan": [
    {
      "skill": "",
      "time": "",
      "resources": ""
    }
  ],
  "estimatedTime": ""
}

Rules:
- No extra text
- No explanation outside JSON
- Keep it clean and valid JSON

Job Description:
%s

Resume:
%s
""".formatted(request.getJobDescription(), request.getResume());

        Map<String, Object> result = aiService.callAI(prompt);

        List<String> jobSkills = (List<String>) result.getOrDefault("jobSkills", List.of());
        List<Map<String, Object>> matchingSkills =
                (List<Map<String, Object>>) result.getOrDefault("matchingSkills", List.of());


        int totalSkills = jobSkills.size();
        int matched = matchingSkills.size();

        double matchPercentage = (totalSkills == 0) ? 0 :
                ((double) matched / totalSkills) * 100;

        result.put("matchPercentage", Math.round(matchPercentage));


        String jsonResult;
        try {
            jsonResult = objectMapper.writeValueAsString(result);
        } catch (Exception e) {
            jsonResult = result.toString();
        }


        Assessment assessment = new Assessment(
                request.getJobDescription(),
                request.getResume(),
                jsonResult
        );

        assessmentRepository.save(assessment);

        return result;
    }

    //  HISTORY
    @GetMapping("/history")
    public List<Assessment> getHistory() {
        return assessmentRepository.findAll();
    }

    //  INTERVIEW
    @PostMapping("/interview")
    public Map<String, Object> startInterview(@RequestBody AssessmentRequest request) {

        String prompt = """
You are a technical interviewer.

Return ONLY valid JSON in this format:

{
  "questions": [
    "",
    "",
    ""
  ]
}

Rules:
- Only JSON
- No extra text

Job Description:
%s

Resume:
%s
""".formatted(request.getJobDescription(), request.getResume());

        return aiService.callAI(prompt);
    }

    // EVALUATE
    @PostMapping("/evaluate")
    public Map<String, Object> evaluate(@RequestBody Map<String, String> request) {

        String question = request.get("question");
        String answer = request.get("answer");

        String prompt = """
You are a technical interviewer.

Evaluate the candidate's answer.

Return ONLY JSON in this format:

{
  "score": 0,
  "feedback": "",
  "improvement": ""
}

Rules:
- Score should be between 1 to 10
- Be concise
- No extra text outside JSON

Question:
%s

Answer:
%s
""".formatted(question, answer);

        return aiService.callAI(prompt);
    }
}