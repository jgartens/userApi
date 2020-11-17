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
import org.springframework.web.bind.annotation.RequestMapping;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ApiResponse;

import org.springframework.web.bind.annotation.PutMapping;

@Api(value = "user", description = "controller for user methods")
@Controller
@RequestMapping("/v2")
public class UserControllerV2 {
    
    @Autowired
    UserRepository userRepository;

    @ApiOperation(value = "Get all users", response = User.class, responseContainer = "List")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Successfully retrieved all users"),
        @ApiResponse(code = 400, message = "Bad Input on User State")
    })
    @GetMapping(value="/")
    public ResponseEntity<List<User>> getUsers(@PathVariable(value = "state") @Valid String state
    , BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        else{
            return new ResponseEntity<List<User>>((List<User>) userRepository.findAll(), HttpStatus.OK);
         }
    }


    @ApiOperation(value = "Get user by ID", response = User.class, responseContainer = "Optional")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Successfully retrieved user by ID"),
        @ApiResponse(code = 404, message = "User does not exist")
    })
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


    @ApiOperation(value = "Create a user")
    @ApiResponses(value = {
        @ApiResponse(code = 201, message = "Successfully created User"),
        @ApiResponse(code = 404, message = "User input has errors, could not create")
    })
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

    @ApiOperation(value = "Edit user")
    @ApiResponses(value = {
        @ApiResponse(code = 201, message = "Successfully edited user"),
        @ApiResponse(code = 404, message = "User does not exist, or input has errors")
    })
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


    @ApiOperation(value = "Delete user")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Successfully deleted User"),
        @ApiResponse(code = 404, message = "User does not exist")
    })
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
