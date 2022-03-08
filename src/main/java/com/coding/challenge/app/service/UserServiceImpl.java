package com.coding.challenge.app.service;

import com.coding.challenge.app.entity.Email;
import com.coding.challenge.app.entity.PhoneNumber;
import com.coding.challenge.app.entity.User;
import com.coding.challenge.app.repository.UserRepository;
import com.coding.challenge.app.utils.ResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.IntStream;

import static com.coding.challenge.app.utils.Constants.INVALID_USER_ID_PROVIDED;
import static com.coding.challenge.app.utils.Constants.USER_DELETED;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public List<User> findUser(String search) {
        List<User> list = new ArrayList<>();
        if(StringUtils.hasLength(search)) {
            if (search.matches(".*[a-zA-Z]+.*")) {
                return userRepository.findUserByFirstNameOrLastName(search);
            } else {
                Optional<User> optionalUser = userRepository.findById(Integer.valueOf(search));
                if(optionalUser.isPresent()) list.add(optionalUser.get());
            }
        }
        return list;
    }

    @Override
    public User updateUser(User user) {
        User dbUser = userRepository.findById(user.getId()).get();

        dbUser.setFirstName(StringUtils.hasLength(user.getFirstName())? user.getFirstName() : dbUser.getFirstName());
        dbUser.setLastName(StringUtils.hasLength(user.getLastName())? user.getLastName() : dbUser.getLastName());

        for(Email email: user.getEmails()) {
            if(email.getId() != null) {
                OptionalInt indexOpt = IntStream.range(0, dbUser.getEmails().size())
                        .filter(i -> email.getId() == dbUser.getEmails().get(i).getId())
                        .findFirst();
                if(indexOpt.isPresent())
                    dbUser.getEmails().get(indexOpt.getAsInt()).setMail(email.getMail());
            } else {
                dbUser.getEmails().add(email);
            }
        }
        for(PhoneNumber phone: user.getPhoneNumbers()) {
            if(phone.getId() != null) {
                OptionalInt indexOpt = IntStream.range(0, dbUser.getPhoneNumbers().size())
                        .filter(i -> phone.getId() == dbUser.getPhoneNumbers().get(i).getId())
                        .findFirst();
                if(indexOpt.isPresent())
                    dbUser.getPhoneNumbers().get(indexOpt.getAsInt()).setNumber(phone.getNumber());
            } else {
                dbUser.getPhoneNumbers().add(phone);
            }
        }
        return userRepository.save(dbUser);
    }


    @Override
    public ResponseDto deleteUser(Integer userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if(optionalUser.isPresent()) {
            userRepository.delete(optionalUser.get());
        }
        return optionalUser.isPresent() ? new ResponseDto(USER_DELETED) : new ResponseDto(INVALID_USER_ID_PROVIDED);
    }
}
