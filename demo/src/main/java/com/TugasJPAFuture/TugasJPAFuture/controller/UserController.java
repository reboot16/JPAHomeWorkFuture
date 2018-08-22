package com.TugasJPAFuture.TugasJPAFuture.controller;


import com.TugasJPAFuture.TugasJPAFuture.exception.ResourceNotFoundException;
import com.TugasJPAFuture.TugasJPAFuture.model.User;
import com.TugasJPAFuture.TugasJPAFuture.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/users")
    public Page<User> getUsers(Pageable pageable){
        return userRepository.findAll(pageable);
    }

    @PostMapping("/users")
    public User createUser(@Valid @RequestBody User user){
        return userRepository.save(user);
    }

    @PutMapping("/users/{userId}")
    public User updateUser(@PathVariable Long userId, @Valid @RequestBody User userRequest){
        return userRepository.findById(userId)
                .map(user ->{
                    user.setName(userRequest.getName());
                    user.setAddress(userRequest.getAddress());
                    return userRepository.save(user);
                }).orElseThrow(()-> new ResourceNotFoundException("User Not Found By Id "+userId));
    }

    @DeleteMapping("/users/{userId}")
    public ResponseEntity<?> deleteQuestion(@PathVariable Long userId){
        return userRepository.findById(userId)
                .map(user -> {
                    userRepository.delete(user);
                    return ResponseEntity.ok().build();
                }).orElseThrow(()-> new ResourceNotFoundException("User NOt Found By Id "+userId));
    }

}
