package com.quocanhit.tasksubmissionservice.service;

import com.quocanhit.tasksubmissionservice.model.Submission;

import java.util.List;

public interface SubmissionService {
    Submission submissionTask(Long taskId, String githubLink, Long userId, String jwt) throws Exception;

    Submission getTaskSubmissionById(Long submissionId) throws Exception;

    List<Submission> getAllSubmissions() throws Exception;

    List<Submission> getTaskSubmissionsByTaskId(Long taskId) throws Exception;

    Submission acceptDeadlineSubmission(Long id, String status) throws Exception;
}
