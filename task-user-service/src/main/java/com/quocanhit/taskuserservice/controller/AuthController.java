package com.quocanhit.taskuserservice.controller;

import com.quocanhit.taskuserservice.config.JwtProvider;
import com.quocanhit.taskuserservice.model.AuthRequest;
import com.quocanhit.taskuserservice.model.AuthResponse;
import com.quocanhit.taskuserservice.model.User;
import com.quocanhit.taskuserservice.repository.UserRepository;
import com.quocanhit.taskuserservice.service.implement.CustomerServiceImplement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private CustomerServiceImplement customerUserDetails;

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> createUserHandler(@RequestBody User user) throws Exception {
        String email = user.getEmail();
        String password = user.getPassword();
        String fullname = user.getFullname();
        String role = user.getRole();
        String mobile = user.getMobile();

        //check email exists
        User isEmailExist = userRepository.findUserByEmail(email);
        if (isEmailExist != null) {
            throw new Exception("Email: { " + email + " } is already. User with another email");
        }

        //Create new account
        User createUser = new User();
        createUser.setEmail(email);
        createUser.setPassword(passwordEncoder.encode(password));
        createUser.setRole(role);
        createUser.setFullname(fullname);
        createUser.setMobile(mobile);
        User saveUser = userRepository.save(createUser);

        Authentication authentication = new UsernamePasswordAuthenticationToken(email, password);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Generate jwt token
        String token = JwtProvider.generateToken(authentication);

        // Give response token
        AuthResponse authResponse = new AuthResponse();
        authResponse.setJwt(token);
        authResponse.setMessage("Register successfully.");
        authResponse.setStatus(true);
        return new ResponseEntity<>(authResponse, HttpStatus.CREATED);
    }

    @PostMapping("/signin")
    public ResponseEntity<AuthResponse> signIn(@RequestBody AuthRequest loginRequest) {
        String username = loginRequest.getEmail();
        String password = loginRequest.getPassword();

        System.out.println(username + "---" + password);

        Authentication authentication = authenticate(username, password);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = JwtProvider.generateToken(authentication);
        AuthResponse authResponse = new AuthResponse();
        authResponse.setJwt(token);
        authResponse.setMessage("Login successful");
        authResponse.setStatus(true);

        return new ResponseEntity<AuthResponse>(authResponse, HttpStatus.OK);
    }

    private Authentication authenticate(String username, String password) {
        UserDetails userDetails = customerUserDetails.loadUserByUsername(username);
        System.out.println("Signing in with " + userDetails);

        if (userDetails == null) {
            System.out.println("sign in userDetails with password - null " + userDetails);
            throw new BadCredentialsException("Invalid username or password.");
        }
        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            System.out.println("sign in userDetails with password - password not match" + userDetails);
            throw new BadCredentialsException("Invalid username or password.");
        }

        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }


}
