package com.quocanhit.controller;

import com.quocanhit.models.DTO.UserDTO;
import com.quocanhit.models.Enum.TaskStatus;
import com.quocanhit.models.Task;
import com.quocanhit.service.TaskService;
import com.quocanhit.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private UserService userService;

    @PostMapping()
    public ResponseEntity<Task> createTasks(@RequestBody Task task,
                                            @RequestHeader("Authorization") String jwt) throws Exception {
        UserDTO user = userService.getUserProfile(jwt);
        Task createTask = taskService.createTask(task, user.getRole());

        return new ResponseEntity<>(createTask, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long id,
                                            @RequestHeader("Authorization") String jwt) throws Exception {
        UserDTO user = userService.getUserProfile(jwt);
        Task task = taskService.getTaskById(id);

        return new ResponseEntity<>(task, HttpStatus.OK);
    }

    @GetMapping("/user")
    public ResponseEntity<List<Task>> getAssignedUsersTask(
            @RequestParam(required = false) TaskStatus status,
            @RequestHeader("Authorization") String jwt) throws Exception {

        UserDTO user = userService.getUserProfile(jwt);
        List<Task> tasks = taskService.assignUserTasks(user.getId(), status);

        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<List<Task>> getAllTask(
            @RequestParam(required = false) TaskStatus status,
            @RequestHeader("Authorization") String jwt) throws Exception {

        UserDTO user = userService.getUserProfile(jwt);
        List<Task> tasks = taskService.getAllTasks(status);

        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    @PutMapping("/{id}/user/{userId}/assigned")
    public ResponseEntity<Task> assignedTaskToUser(
            @PathVariable Long id,
            @PathVariable Long userId,
            @RequestHeader("Authorization") String jwt) throws Exception {

        UserDTO user = userService.getUserProfile(jwt);
        Task task = taskService.assignedToUser(userId, id);

        return new ResponseEntity<>(task, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(
            @PathVariable Long id,
            @PathVariable Task req,
            @RequestHeader("Authorization") String jwt) throws Exception {

        UserDTO user = userService.getUserProfile(jwt);
        Task task = taskService.updateTask(id, req, user.getId());

        return new ResponseEntity<>(task, HttpStatus.OK);
    }

    @PutMapping("/{id}/complete")
    public ResponseEntity<Task> completeTask(
            @PathVariable Long id,
            @RequestHeader("Authorization") String jwt) throws Exception {

        Task task = taskService.completeTask(id);
        return new ResponseEntity<>(task, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTask(
            @PathVariable Long id,
            @RequestHeader("Authorization") String jwt) throws Exception {

        taskService.deleteTask(id);

        return new ResponseEntity<>("Delete task successfully.", HttpStatus.NO_CONTENT);
    }

}
