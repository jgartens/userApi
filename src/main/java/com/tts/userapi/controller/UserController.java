package com.tts.userapi.controller;

import java.util.List;
import java.util.Optional;

import com.tts.userapi.model.User;
import com.tts.userapi.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {
    
    @Autowired
    UserRepository userRepository;

    @GetMapping(value="/")
    public List<User> getUsers(){
        return (List<User>) userRepository.findAll();
    }
    
    @GetMapping("/user/{id}")
    public User getUser(@PathVariable(value = "id") Long id){
        
        Optional <User> user = userRepository.findById(id);

        if(user.isPresent()){
            return user.get();
        }
        else {
            User userError  = new User();
            userError.setFirstName("Error");
            userError.setLastName("Error");
            return user.get();
        }
    }
    
    @PostMapping("/users")
    public void createUser(@RequestBody User user){
        userRepository.save(user);
    }
    
}
