package com.coding.challenge.app.utils;

import com.coding.challenge.app.entity.Email;
import com.coding.challenge.app.entity.PhoneNumber;
import com.coding.challenge.app.entity.User;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import static com.coding.challenge.app.utils.TestConstants.*;

public class LoadData {

    public static User loadUserData() {
        User user = new User();
        user.setId(1);
        user.setFirstName(BRAHMAIAH);
        user.setLastName(CHEELI);

        List<Email> emailList = new ArrayList<>();
        Email email = new Email();
        email.setId(1);
        email.setMail(CHEELIBRAHMAIAH_GMAIL_COM);
        emailList.add(email);

        List<PhoneNumber> phoneNumberList = new ArrayList<>();
        PhoneNumber phone = new PhoneNumber();
        phone.setId(1);
        phone.setNumber(EXPECTED_VALUE);
        phoneNumberList.add(phone);

        user.setEmails(emailList);
        user.setPhoneNumbers(phoneNumberList);

        return user;
    }

    public static User loadUpdateUserData() {
        User user = loadUserData();

        Email email = new Email();
        email.setMail(TEST_GMAIL_COM);
        user.getEmails().add(email);

        PhoneNumber phone = new PhoneNumber();
        phone.setNumber(EXPECTED_VALUE1);
        user.getPhoneNumbers().add(phone);

        return user;
    }

    public static String objectToJson() {
        User user = loadUserData();
        Gson gson = new Gson();
        return gson.toJson(user);
    }

    public static String updateObjectToJson() {
        User user = loadUpdateUserData();
        Gson gson = new Gson();
        return gson.toJson(user);
    }

    public static ResponseDto returnResponse(boolean isSuccess) {
        return isSuccess ? new ResponseDto(USER_DELETED) : new ResponseDto(INVALID_USER_ID_PROVIDED);
    }
}
