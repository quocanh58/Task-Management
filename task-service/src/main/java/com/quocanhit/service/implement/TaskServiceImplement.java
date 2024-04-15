package com.quocanhit.service.implement;

import com.quocanhit.models.Task;
import com.quocanhit.models.Enum.TaskStatus;
import com.quocanhit.repository.TaskRepository;
import com.quocanhit.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TaskServiceImplement implements TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Override
    public Task createTask(Task task, String requesterRole) throws Exception {
        if (!requesterRole.equals("ROLE_ADMIN")) {
            throw new Exception("Only admins can create tasks");
        }
        task.setStatus(TaskStatus.PENDING);
        task.setCreateAt(LocalDateTime.now());
        return taskRepository.save(task);
    }

    @Override
    public Task getTaskById(Long id) throws Exception {
        return taskRepository.findById(id).orElseThrow(() -> new Exception("Task not found with id " + id));
    }

    @Override
    public List<Task> getAllTasks(TaskStatus status) throws Exception {
        List<Task> tasks = taskRepository.findAll();
        List<Task> filteredTasks = tasks.stream().filter(
                task -> status == null ||
                        task.getStatus().name().equalsIgnoreCase(status.toString())).collect(Collectors.toList());

        return filteredTasks;
    }

    @Override
    public Task updateTask(Long id, Task updateTask, Long userId) throws Exception {
        Task existingTask = getTaskById(id);

        if (updateTask.getTitle() != null) {
            existingTask.setTitle(updateTask.getTitle());
        }
        if (updateTask.getImage() != null) {
            existingTask.setImage(updateTask.getImage());
        }
        if (updateTask.getDescription() != null) {
            existingTask.setDescription(updateTask.getDescription());
        }
        if (updateTask.getStatus() != null) {
            existingTask.setStatus(updateTask.getStatus());
        }
        if (updateTask.getDeadline() != null) {
            existingTask.setDeadline(updateTask.getDeadline());
        }
        return taskRepository.save(existingTask);
    }

    @Override
    public Boolean deleteTask(Long id) throws Exception {
        boolean isDelete = false;
        Optional<Task> findTaskToDelete = taskRepository.findById(id);

        if (findTaskToDelete.isPresent()) {
            taskRepository.deleteById(id);
            isDelete = true;
        }

        return isDelete;
    }


    @Override
    public Task assignedToUser(Long userId, Long taskId) throws Exception {
        Task exsisTask = getTaskById(taskId);
        exsisTask.setAssignedUserId(userId);
        exsisTask.setStatus(TaskStatus.DONE);

        return taskRepository.save(exsisTask);
    }

    @Override
    public List<Task> assignUserTasks(Long userId, TaskStatus status) throws Exception {
        List<Task> allTask = taskRepository.findByAssignedUserId(userId);

        List<Task> filteredTasks = allTask.stream().filter(
                task -> status == null ||
                        task.getStatus().name().equalsIgnoreCase(status.toString())).collect(Collectors.toList());

        return filteredTasks;
    }

    @Override
    public Task completeTask(Long taskId) throws Exception {
        Task task = getTaskById(taskId);
        task.setStatus(TaskStatus.DONE);
        return taskRepository.save(task);
    }
}
