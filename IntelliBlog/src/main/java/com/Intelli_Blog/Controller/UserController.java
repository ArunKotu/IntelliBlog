package com.Intelli_Blog.IntelliBlog.Controller;

import com.Intelli_Blog.IntelliBlog.DTO.RegisterDto;
import com.Intelli_Blog.IntelliBlog.Model.User;
import com.Intelli_Blog.IntelliBlog.Service.UserService;
import org.springframework.security.web.webauthn.api.CredentialPropertiesOutput;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping("/login")
    public String login(){
        return "Login Success!";
    }

    @PostMapping("/register")
    public String register(@RequestBody RegisterDto dto) {
        try {
            userService.saveUser(dto);
            return "User registered successfully";
        } catch (Exception e) {
            return "User already exists or registration failed";
        }
    }
    @PutMapping("/update/{id}")
    public String updateUser(@PathVariable String id, @RequestBody User user){
        return userService.updateUser(id,user);
    }
    @DeleteMapping("/delete/{id}")
    public User deletedUser(@PathVariable String id){
        return userService.deleteUser(id);
    }
}

