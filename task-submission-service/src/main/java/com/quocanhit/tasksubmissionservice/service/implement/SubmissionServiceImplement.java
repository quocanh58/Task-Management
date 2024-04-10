package com.quocanhit.tasksubmissionservice.service.implement;

import com.quocanhit.tasksubmissionservice.model.DTO.TaskDTO;
import com.quocanhit.tasksubmissionservice.model.Submission;
import com.quocanhit.tasksubmissionservice.repository.SubmissionRepository;
import com.quocanhit.tasksubmissionservice.service.SubmissionService;
import com.quocanhit.tasksubmissionservice.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SubmissionServiceImplement implements SubmissionService {

    @Autowired
    private SubmissionRepository submissionRepository;

    @Autowired
    private TaskService taskService;

    @Override
    public Submission submissionTask(Long taskId, String githubLink, Long userId, String jwt) throws Exception {
        TaskDTO task = taskService.getTaskById(taskId, jwt);
        if (task != null) {
            Submission submission = new Submission();
            submission.setTaskId(taskId);
            submission.setUserId(userId);
            submission.setGithubLink(githubLink);
            submission.setSubmissionTime(LocalDateTime.now());
            return submissionRepository.save(submission);
        }
        throw new Exception("Task not found with id " + taskId);
    }

    @Override
    public Submission getTaskSubmissionById(Long submissionId) throws Exception {
        return submissionRepository.findById(submissionId).orElseThrow(() ->
                new Exception("Task not found with id " + submissionId));
    }

    @Override
    public List<Submission> getAllSubmissions() throws Exception {
        return submissionRepository.findAll();
    }

    @Override
    public List<Submission> getTaskSubmissionsByTaskId(Long taskId) throws Exception {
        return submissionRepository.findByTaskId(taskId);
    }

    @Override
    public Submission acceptDeadlineSubmission(Long id, String status) throws Exception {
        Submission submission = getTaskSubmissionById(id);
        submission.setStatus(status);
        if (status != null && status.equals("ACCEPT")) {
            taskService.completeTask(submission.getTaskId());
        }

        return submissionRepository.save(submission);
    }
}
