package com.quocanhit.tasksubmissionservice.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/submissions")
public class HomeController {

    @GetMapping("/ok")
    public ResponseEntity<String> homeController(){
        return new ResponseEntity<>("Welcome to submission service", HttpStatus.OK);
    }

}
