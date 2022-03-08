package com.coding.challenge.app.controller;

import com.coding.challenge.app.entity.User;
import com.coding.challenge.app.service.UserService;
import com.coding.challenge.app.utils.LoadData;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static com.coding.challenge.app.utils.TestConstants.*;
import static com.coding.challenge.app.utils.LoadData.*;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    private User user = loadUserData();

    private User updatedUser = loadUpdateUserData();

    @Test
    public void shouldSaveUser() throws Exception {
        //given
        given(userService.saveUser(user)).willReturn(user);

        //under test
        this.mockMvc.perform(post(API_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(LoadData.objectToJson())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.firstName").value(BRAHMAIAH))
                .andExpect(jsonPath("$.lastName").value(CHEELI))
                .andExpect(jsonPath("$.emails", hasSize(1)))
                .andExpect(jsonPath("$.phoneNumbers", hasSize(1)))
                .andExpect(jsonPath("$.emails[0].mail").value(CHEELIBRAHMAIAH_GMAIL_COM))
                .andExpect(jsonPath("$.phoneNumbers[0].number").value(EXPECTED_VALUE))
                .andReturn();

        //verify that person service was called
        verify(userService, times(1)).saveUser(user);
    }

    @Test
    public void shouldFindUser() throws Exception{
        //given
        given(userService.findUser(String.valueOf(user.getId()))).willReturn(Arrays.asList(user));

        //under test
        this.mockMvc.perform(get(API_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param(SEARCH, String.valueOf(user.getId()))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id").exists())
                .andExpect(jsonPath("$[0].firstName").value(BRAHMAIAH))
                .andExpect(jsonPath("$[0].lastName").value(CHEELI))
                .andExpect(jsonPath("$[0].emails", hasSize(1)))
                .andExpect(jsonPath("$[0].phoneNumbers", hasSize(1)))
                .andExpect(jsonPath("$[0].emails[0].mail").value(CHEELIBRAHMAIAH_GMAIL_COM))
                .andExpect(jsonPath("$[0].phoneNumbers[0].number").value(EXPECTED_VALUE))
                .andReturn();

        //verify that person service was called
        verify(userService, times(1)).findUser(String.valueOf(user.getId()));
    }

    @Test
    public void shouldUpdateUser() throws Exception {
        //given
        given(userService.updateUser(updatedUser)).willReturn(updatedUser);

        //under test
        this.mockMvc.perform(put(API_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(LoadData.updateObjectToJson())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.firstName").value(BRAHMAIAH))
                .andExpect(jsonPath("$.lastName").value(CHEELI))
                .andExpect(jsonPath("$.emails", hasSize(2)))
                .andExpect(jsonPath("$.phoneNumbers", hasSize(2)))
                .andExpect(jsonPath("$.emails[1].mail").value(TEST_GMAIL_COM))
                .andExpect(jsonPath("$.phoneNumbers[1].number").value(EXPECTED_VALUE1))
                .andReturn();

        //verify that person service was called
        verify(userService, times(1)).updateUser(updatedUser);
    }

    @Test
    public void shouldDeleteUser() throws Exception {
        //given
        given(userService.deleteUser(user.getId())).willReturn(returnResponse(true));

        //under test
        this.mockMvc.perform(delete(API_ENDPOINT + "/" +user.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.message").isNotEmpty())
                .andExpect(jsonPath("$.message").value(USER_DELETED))
                .andReturn();

        //verify that service was called
        verify(userService, times(1)).deleteUser(user.getId());
    }
}
