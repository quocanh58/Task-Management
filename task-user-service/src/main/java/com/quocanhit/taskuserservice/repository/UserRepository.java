package com.quocanhit.taskuserservice.repository;

import com.quocanhit.taskuserservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    public User findByEmail(String email);

}
