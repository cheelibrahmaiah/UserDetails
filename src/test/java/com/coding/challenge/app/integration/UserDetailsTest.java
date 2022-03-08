package com.coding.challenge.app.integration;

import com.coding.challenge.app.UserDetailsApplication;
import com.coding.challenge.app.entity.User;
import com.coding.challenge.app.utils.LoadData;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static com.coding.challenge.app.utils.LoadData.loadUpdateUserData;
import static com.coding.challenge.app.utils.LoadData.loadUserData;
import static com.coding.challenge.app.utils.TestConstants.*;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = UserDetailsApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application.properties")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserDetailsTest {
    @Autowired
    private MockMvc mockMvc;

    private User user = loadUserData();

    private User updatedUser = loadUpdateUserData();

    @Test
    public void should1SaveUser() throws Exception {

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
    }

    @Test
    public void should2FindUser() throws Exception{
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
    }

    @Test
    public void should3UpdateUser() throws Exception {
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
    }

    @Test
    public void should4DeleteUser() throws Exception {
        this.mockMvc.perform(delete(API_ENDPOINT + "/" +user.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.message").isNotEmpty())
                .andExpect(jsonPath("$.message").value(USER_DELETED))
                .andReturn();
    }

}
