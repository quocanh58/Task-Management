package com.quocanhit.tasksubmissionservice.repository;

import com.quocanhit.tasksubmissionservice.model.Submission;
import jdk.dynalink.linker.LinkerServices;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubmissionRepository extends JpaRepository<Submission, Long> {
    List<Submission> findByTaskId(Long taskId);
}
