package com.quocanhit.service;

import com.quocanhit.models.Task;
import com.quocanhit.models.Enum.TaskStatus;

import java.util.List;

public interface TaskService {
    Task createTask(Task task, String requesterRole) throws Exception;

    Task getTaskById(Long id) throws Exception;

    List<Task> getAllTasks(TaskStatus status) throws Exception;

    Task updateTask(Long id, Task updateTask, Long userId) throws Exception;

    Boolean deleteTask(Long id) throws Exception;

    Task assignedToUser(Long userId, Long taskId) throws Exception;

    List<Task> assignUserTasks(Long userId, TaskStatus status) throws Exception;

    Task completeTask(Long taskId) throws Exception;
}
