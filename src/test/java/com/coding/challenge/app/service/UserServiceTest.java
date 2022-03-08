package com.coding.challenge.app.service;

import com.coding.challenge.app.entity.User;
import com.coding.challenge.app.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static com.coding.challenge.app.utils.TestConstants.*;
import static com.coding.challenge.app.utils.LoadData.loadUpdateUserData;
import static com.coding.challenge.app.utils.LoadData.loadUserData;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;
    
    private User user;
    
    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        user = loadUserData();
    }

    @Test
    public void testSaveUser() {
        //given
        given(userRepository.save(user)).willReturn(user);

        //calling method under the test
        User userResult = userService.saveUser(user);

        //assert
        assertUser(userResult);

        //verify that repository was called
        verify(userRepository, times(1)).save(user);
    }

    @Test
    public void testFindUser() {
        //given
        given(userRepository.findById(user.getId())).willReturn(Optional.of(user));

        //calling method under the test
        List<User> usersResult = userService.findUser(String.valueOf(user.getId()));

        //assert
        assertUser(usersResult.get(0));

        //verify that repository was called
        verify(userRepository, times(1)).findById(user.getId());
    }

    @Test
    public void testUpdateUser() {
        User updatedUser = loadUpdateUserData();
        //given
        given(userRepository.findById(user.getId())).willReturn(Optional.of(user));
        given(userRepository.save(updatedUser)).willReturn(updatedUser);

        //calling method under the test
        User resultUser = userService.updateUser(updatedUser);

        //assert
        assertThat(resultUser.getEmails()).hasSize(2);
        assertThat(resultUser.getPhoneNumbers()).hasSize(2);
    }

    @Test
    public void testDeleteUser() {
        //given
        given(userRepository.findById(user.getId())).willReturn(Optional.of(user));

        //calling method under the test
        userService.deleteUser(user.getId());

        //verify that repository was called
        verify(userRepository, times(1)).delete(user);
    }


    private void assertUser(User userResult) {
        assertThat(user.getId()).isInstanceOf(Integer.class);
        assertThat(user.getId()).isEqualTo(1);
        assertThat(user.getFirstName()).isEqualTo(BRAHMAIAH);
        assertThat(user.getLastName()).isEqualTo(CHEELI);
        assertThat(user.getEmails()).hasSize(1);
        assertThat(user.getPhoneNumbers()).hasSize(1);
        assertThat(user.getEmails().get(0).getId()).isInstanceOf(Integer.class);
        assertThat(user.getEmails().get(0).getId()).isEqualTo(1);
        assertThat(user.getEmails().get(0).getMail()).isEqualTo(CHEELIBRAHMAIAH_GMAIL_COM);

        assertThat(user.getPhoneNumbers().get(0).getId()).isInstanceOf(Integer.class);
        assertThat(user.getPhoneNumbers().get(0).getId()).isEqualTo(1);
        assertThat(user.getPhoneNumbers().get(0).getNumber()).isEqualTo(EXPECTED_VALUE);

    }
}


