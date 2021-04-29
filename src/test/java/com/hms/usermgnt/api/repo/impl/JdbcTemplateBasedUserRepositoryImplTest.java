package com.hms.usermgnt.api.repo.impl;

import com.hms.usermgnt.api.model.User;

import static org.assertj.core.api.Assertions.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Service;
import org.springframework.test.context.ActiveProfiles;


import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class JdbcTemplateBasedUserRepositoryImplTest {

    @Autowired
    JdbcTemplateBasedUserRepositoryImpl jdbcTemplateBasedUserRepository;

    @DisplayName(" Testing search users with firstname")
    @Test
    void getAllUsersWithFirstName() {
        assertTrue(jdbcTemplateBasedUserRepository.getAllUsersWithFirstName("kasun")
                .stream().findAny().isPresent());
        assertThat(jdbcTemplateBasedUserRepository.getAllUsersWithFirstName("kasun")).hasSize(1);
    }


    @DisplayName(" Testing search users with lastname")
    @Test
    void getAllUsersWithLastName(){
        assertThat(jdbcTemplateBasedUserRepository.getAllUsersWithLastName("guruge")
                .stream().findAny().isPresent());
        assertThat(jdbcTemplateBasedUserRepository.getAllUsersWithLastName("guruge")).hasSize(1);

    }

    @DisplayName(" Testing search users with firstname and lastname")
    @Test
    void getAllUsersWithFnameAndLname() {
        assertThat(jdbcTemplateBasedUserRepository.getAllUsersWithFnameAndLname("kasun", "kalhara")).hasSize(1);
        assertThat(jdbcTemplateBasedUserRepository.getAllUsersWithFnameAndLname("kasun","kalhara")
                .stream().findAny().isPresent());

    }

    @DisplayName("Testing getting all users")
    @Test
    void getAllUsers() {
       assertFalse(jdbcTemplateBasedUserRepository.getAllUsers().isEmpty());
       assertEquals(6,jdbcTemplateBasedUserRepository.getAllUsers().size());

    }

    @Test
    @DisplayName("Testing User Creation")
    void createUser() {
        User user = new User("devingallage", "devin", "gallage", "devin@gmail.com");
         jdbcTemplateBasedUserRepository.createUser(user);
        assertFalse(jdbcTemplateBasedUserRepository.getAllUsers().isEmpty());
        assertEquals(7,jdbcTemplateBasedUserRepository.getAllUsers().size());
        assertTrue(jdbcTemplateBasedUserRepository.getAllUsers().stream().
                anyMatch(user2-> user2.getUserName().equals("devingallage")&&
                user2.getFirstName().equals("devin") &&
                user2.getLastName().equals("gallage")&&
                user2.getEmailId().equals("devin@gmail.com")));
    }

    @DisplayName("Testing for get user with Id")
    @Test
    void getUserWithId() {
        assertTrue(jdbcTemplateBasedUserRepository
                .getUserWithId(2).stream().findAny().isPresent());

    }

    @DisplayName("Update User Testing")
    @Test
    void updateUser() {
        User user = new User("devingallage", "devin", "gallage", "devin@gmail.com");
        jdbcTemplateBasedUserRepository.updateUser(user,6);
        assertEquals(6,jdbcTemplateBasedUserRepository.getAllUsers().size());
        assertTrue(jdbcTemplateBasedUserRepository.getAllUsers().stream().
                anyMatch(user2-> user2.getUserName().equals("devingallage")&&
                        user2.getFirstName().equals("devin") &&
                        user2.getLastName().equals("gallage")&&
                        user2.getEmailId().equals("devin@gmail.com")));

    }
    @DisplayName("Delete User Testing")
    @Test
    void deleteUser() {
        User user = new User("devingallage", "devin", "gallage", "devin@gmail.com");
        jdbcTemplateBasedUserRepository.createUser(user);
        assertFalse(jdbcTemplateBasedUserRepository.getAllUsers().isEmpty());
        assertEquals(7,jdbcTemplateBasedUserRepository.getAllUsers().size());
        assertTrue(jdbcTemplateBasedUserRepository.getAllUsers().stream().
                anyMatch(user2-> user2.getUserName().equals("devingallage")&&
                        user2.getFirstName().equals("devin") &&
                        user2.getLastName().equals("gallage")&&
                        user2.getEmailId().equals("devin@gmail.com")));

        jdbcTemplateBasedUserRepository.deleteUser(7);
        assertEquals(6,jdbcTemplateBasedUserRepository.getAllUsers().size());
        assertFalse(jdbcTemplateBasedUserRepository.getAllUsers().stream().
                anyMatch(user2-> user2.getUserName().equals("devingallage")&&
                        user2.getFirstName().equals("devin") &&
                        user2.getLastName().equals("gallage")&&
                        user2.getEmailId().equals("devin@gmail.com")));


    }
}