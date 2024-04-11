package com.quocanhit.taskuserservice.service;

import com.quocanhit.taskuserservice.model.User;

import java.util.List;

public interface UserService {
    public User getUserProfile(String jwt);
    public List<User> getAllUsers();
}
