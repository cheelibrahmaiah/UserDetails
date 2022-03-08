package com.coding.challenge.app.controller;

import com.coding.challenge.app.entity.User;
import com.coding.challenge.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping
    public ResponseEntity createUser(@RequestBody User user) {
        User savedUser = userService.saveUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }

    @GetMapping
    public ResponseEntity findUser(@RequestParam("search") String search) {
        List<User> users = userService.findUser(search);
        return ResponseEntity.status(HttpStatus.OK).body(users);
    }

    @PutMapping
    public ResponseEntity updateUser(@RequestBody User user) {
        User updatedUser = userService.updateUser(user);
        return ResponseEntity.status(HttpStatus.OK).body(updatedUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteUser(@PathVariable("id") Integer userId) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.deleteUser(userId));
    }

}
