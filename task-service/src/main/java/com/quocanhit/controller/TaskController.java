package com.quocanhit.controller;

import com.quocanhit.models.DTO.UserDTO;
import com.quocanhit.models.Task;
import com.quocanhit.service.TaskService;
import com.quocanhit.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<Task> getTaskById(@RequestBody Task task,
                                            @RequestHeader("Authorization") String jwt) throws Exception {
        UserDTO user = userService.getUserProfile(jwt);
        Task createTask = taskService.createTask(task, user.getRole());

        return new ResponseEntity<>(createTask, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/ok", method = RequestMethod.GET)
    public String getTask() {
        return "OK";
    }

}
