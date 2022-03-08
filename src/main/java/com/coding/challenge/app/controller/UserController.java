package com.coding.challenge.app.controller;

import com.coding.challenge.app.entity.User;
import com.coding.challenge.app.service.UserService;
import com.coding.challenge.app.utils.ResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
@Slf4j
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping
    public ResponseEntity createUser(@RequestBody User user) {
        log.info("Entering into createUser method of UserController class");
        User savedUser = userService.saveUser(user);
        log.info("Execution completed - createUser method of UserController class");
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }

    @GetMapping
    public ResponseEntity findUser(@RequestParam("search") String search) {
        log.info("Entering into findUser method of UserController class");
        List<User> users = userService.findUser(search);
        log.info("Execution completed - findUser method of UserController class");
        return ResponseEntity.status(HttpStatus.OK).body(users);
    }

    @PutMapping
    public ResponseEntity updateUser(@RequestBody User user) {
        log.info("Entering into updateUser method of UserController class");
        User updatedUser = userService.updateUser(user);
        log.info("Execution completed - updateUser method of UserController class");
        return ResponseEntity.status(HttpStatus.OK).body(updatedUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteUser(@PathVariable("id") Integer userId) {
        log.info("Entering into deleteUser method of UserController class");
        ResponseDto responseDto = userService.deleteUser(userId);
        log.info("Execution completed - deleteUser method of UserController class");
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

}
