package com.TugasJPAFuture.TugasJPAFuture.controller;

import com.TugasJPAFuture.TugasJPAFuture.exception.ResourceNotFoundException;
import com.TugasJPAFuture.TugasJPAFuture.model.Todo;
import com.TugasJPAFuture.TugasJPAFuture.repository.TodoRepository;
import com.TugasJPAFuture.TugasJPAFuture.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class TodoController {

    @Autowired
    private TodoRepository todoRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/users/{userId}/todos")
    public List<Todo> getTodoByUserId(@PathVariable Long userId)
    {
        return todoRepository.findByUserId(userId);
    }

    @PostMapping("/users/{userId}/todos")
    public Todo addTodo(@PathVariable Long userId, @Valid @RequestBody Todo todo){
        return  userRepository.findById(userId)
                .map(user -> {
                    todo.setUser(user);
                    return todoRepository.save(todo);
                }).orElseThrow(() -> new ResourceNotFoundException("User not Found By Id "+userId));
    }

    @PutMapping("/users/{userId}/todos/{todoId}")
    public Todo updateTodo(@PathVariable Long userId, @PathVariable Long todoId,
                           @Valid @RequestBody Todo todoRequest){
        if(!userRepository.existsById(userId)){
            throw new ResourceNotFoundException("User not found by id "+userId);
        }

        return todoRepository.findById(todoId)
                .map(todo -> {
                    todo.setText(todoRequest.getText());
                    return todoRepository.save(todo);
                }).orElseThrow(()-> new ResourceNotFoundException("Todo Not Found By Id "+todoId));
    }

    @DeleteMapping("users/{userId}/todos/{todoId}")
    public ResponseEntity<?> deleteTodo(@PathVariable Long userId, @PathVariable Long todoId){
        if(!userRepository.existsById(userId)){
            throw new ResourceNotFoundException("User not found by Id "+userId);
        }

        return todoRepository.findById(todoId)
                .map(todo -> {
                    todoRepository.delete(todo);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new ResourceNotFoundException("Todo NotFound By Id "+todoId));
    }


}
