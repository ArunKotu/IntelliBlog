package com.Intelli_Blog.IntelliBlog.Service;

import com.Intelli_Blog.IntelliBlog.DTO.RegisterDto;
import com.Intelli_Blog.IntelliBlog.Model.User;
import com.Intelli_Blog.IntelliBlog.Repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final MyUserDetailsService userDetailsService;
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    public UserService(MyUserDetailsService userDetailsService, UserRepository userRepository, PasswordEncoder encoder) {
        this.userDetailsService = userDetailsService;
        this.userRepository = userRepository;
        this.encoder = encoder;
    }
    public User saveUser(RegisterDto dto){

        if (userRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }

        if (userRepository.findByUsername(dto.getUsername()).isPresent()) {
            throw new RuntimeException("Username already exists");
        }

        User user = new User();
        user.setEmail(dto.getEmail());
        user.setUsername(dto.getUsername());
        user.setPassword(encoder.encode(dto.getPassword()));

        return userRepository.save(user);
    }

    public RegisterDto getUser(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        RegisterDto dto = new RegisterDto();
        dto.setUsername(user.get().getUsername());
        dto.setEmail(user.get().getEmail());
        return dto;
    }

    public String updateUser(String id,User user){
        Optional<User> u = userRepository.findById(id);
        if(u.isPresent()){
            User oldUser = u.get();
            oldUser.setUsername(!user.getUsername().isEmpty()?user.getUsername(): oldUser.getUsername());
            oldUser.setPassword(!user.getPassword().isEmpty()?user.getPassword():oldUser.getPassword());
            oldUser.setEmail(!user.getEmail().isEmpty()?user.getEmail():oldUser.getEmail());
            userRepository.save(oldUser);
            return "Update User SucessFully!"+oldUser.getUsername();
        }
        else {
            throw new RuntimeException("User Not Found! ");
        }
    }
    public User deleteUser(String id){
        Optional<User> user = userRepository.findById(id);
        if(user.isPresent()) {
            userRepository.deleteById(id);
            return user.get();
        }
        else {
            throw new RuntimeException("User Not Found !");
        }
    }
}
