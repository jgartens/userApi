package com.tts.userapi.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import com.tts.userapi.model.User;
import com.tts.userapi.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.PutMapping;


@Controller
public class UserController {
    
    @Autowired
    UserRepository userRepository;

    @GetMapping(value="/")
    public ResponseEntity<List<User>> getUsers(){
        return new ResponseEntity<List<User>>((List<User>) userRepository.findAll(), HttpStatus.OK);
    }
    
    @GetMapping("/user/{id}")
    public ResponseEntity<Optional<User>> getUserById(@PathVariable(value = "id") Long id){
        
        Optional <User> user = userRepository.findById(id);

        if(user.isPresent()){
            return new ResponseEntity<Optional<User>>(user, HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @PostMapping("/users")
    public ResponseEntity<Void> createUser(@RequestBody @Valid User user, BindingResult bindingResult){

        if (bindingResult.hasErrors() ){
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        }
        else{
            userRepository.save(user);
            return new ResponseEntity<Void>(HttpStatus.CREATED);
        }
        
    }

    @PutMapping(value="/users/{id}")
    public ResponseEntity<Void> editUser(@PathVariable Long id, @RequestBody @Valid User user, 
    BindingResult bindingResult) {
        Optional<User> requestedUser = userRepository.findById(id);
        if (!requestedUser.isPresent()){
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        }
        if (bindingResult.hasErrors() ){
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        }
        else{
            userRepository.save(user);
            return new ResponseEntity<Void>(HttpStatus.CREATED);
        }
    }

    @DeleteMapping(value="/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id, @RequestBody @Valid User user, 
    BindingResult bindingResult){
        Optional<User> requestedUser = userRepository.findById(id);
        if (!requestedUser.isPresent()){
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        }
        else{
            userRepository.delete(user);
            return new ResponseEntity<Void>(HttpStatus.OK);
        }

    }



    
}
