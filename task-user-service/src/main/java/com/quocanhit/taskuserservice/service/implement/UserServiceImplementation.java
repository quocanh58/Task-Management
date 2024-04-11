package com.quocanhit.taskuserservice.service.implement;

import com.quocanhit.taskuserservice.config.JwtProvider;
import com.quocanhit.taskuserservice.model.User;
import com.quocanhit.taskuserservice.repository.UserRepository;
import com.quocanhit.taskuserservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImplementation implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User getUserProfile(String jwt) {
        String email = JwtProvider.getEmailFromJwtToken(jwt);
        return userRepository.findUserByEmail(email);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
