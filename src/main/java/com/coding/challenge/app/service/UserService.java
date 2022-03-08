package com.coding.challenge.app.service;

import com.coding.challenge.app.entity.User;
import com.coding.challenge.app.utils.ResponseDto;

import java.util.List;

public interface UserService {
    User saveUser(User user);
    List<User> findUser(String search);
    ResponseDto deleteUser(Integer userId);
    User updateUser(User user);
}
