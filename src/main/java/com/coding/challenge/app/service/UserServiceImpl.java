package com.coding.challenge.app.service;

import com.coding.challenge.app.entity.Email;
import com.coding.challenge.app.entity.PhoneNumber;
import com.coding.challenge.app.entity.User;
import com.coding.challenge.app.repository.UserRepository;
import com.coding.challenge.app.utils.ResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.IntStream;

import static com.coding.challenge.app.utils.Constants.INVALID_USER_ID_PROVIDED;
import static com.coding.challenge.app.utils.Constants.USER_DELETED;

@Service
@Slf4j
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public List<User> findUser(String search) {
        log.info("Entering into findUser method of UserServiceImpl class");
        List<User> list = new ArrayList<>();
        if(StringUtils.hasLength(search)) {
            //Checks whether search string consists of alphabets or numbers
            if (search.matches(".*[a-zA-Z]+.*")) {
                log.info("search key consists of user name like {}", search);
                return userRepository.findUserByFirstNameOrLastName(search);
            } else {
                log.info("search key consists of user id {}", search);
                Optional<User> optionalUser = userRepository.findById(Integer.valueOf(search));
                if(optionalUser.isPresent()) list.add(optionalUser.get());
            }
        }
        log.info("Execution completed - findUser method of UserController class");
        return list;
    }

    @Override
    public User updateUser(User user) {
        log.info("Entering into updateUser method of UserServiceImpl class");
        User dbUser = userRepository.findById(user.getId()).get();
        //Update the users firstname and lastname
        dbUser.setFirstName(StringUtils.hasLength(user.getFirstName())? user.getFirstName() : dbUser.getFirstName());
        dbUser.setLastName(StringUtils.hasLength(user.getLastName())? user.getLastName() : dbUser.getLastName());

        //Update or add the users emails, if email object have id then we need to update the properties / attributes of the object
        //otherwise create new record for new email object
        for(Email email: user.getEmails()) {
            if(email.getId() != null) {
                //find the index of the current email object and update the rest of the fields based on index
                OptionalInt indexOpt = IntStream.range(0, dbUser.getEmails().size())
                        .filter(i -> email.getId() == dbUser.getEmails().get(i).getId())
                        .findFirst();
                if(indexOpt.isPresent())
                    dbUser.getEmails().get(indexOpt.getAsInt()).setMail(email.getMail());
            } else {
                dbUser.getEmails().add(email);
            }
        }

        //Update or add the users phones, if phone object have id then we need to update the properties / attributes of the object
        //otherwise create new record for new phone object
        for(PhoneNumber phone: user.getPhoneNumbers()) {
            if(phone.getId() != null) {
                //find the index of the current phone object and update the rest of the fields based on index
                OptionalInt indexOpt = IntStream.range(0, dbUser.getPhoneNumbers().size())
                        .filter(i -> phone.getId() == dbUser.getPhoneNumbers().get(i).getId())
                        .findFirst();
                if(indexOpt.isPresent())
                    dbUser.getPhoneNumbers().get(indexOpt.getAsInt()).setNumber(phone.getNumber());
            } else {
                dbUser.getPhoneNumbers().add(phone);
            }
        }
        log.info("Execution completed - updateUser method of UserController class");
        return userRepository.save(dbUser);
    }


    @Override
    public ResponseDto deleteUser(Integer userId) {
        log.info("Entering into deleteUser method of UserServiceImpl class");
        //First checks the is there any object is exists with given userId, if yes remove that object
        //otherwise send error message as response

        //TODO: as of now I deleted the user as hard delete,
        // better approach is soft delete if required need to work on it..
        Optional<User> optionalUser = userRepository.findById(userId);
        if(optionalUser.isPresent()) {
            log.info("user exists with given userId");
            userRepository.delete(optionalUser.get());
        }
        log.info("Execution completed - deleteUser method of UserController class");
        return optionalUser.isPresent() ? new ResponseDto(USER_DELETED) : new ResponseDto(INVALID_USER_ID_PROVIDED);
    }
}
