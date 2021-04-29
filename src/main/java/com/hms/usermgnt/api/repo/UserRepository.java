package com.hms.usermgnt.api.repo;

import com.hms.usermgnt.api.model.User;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * This is the user Repository interface
 * All the operations in the API are defined here
 * @param <User>
 */


public interface UserRepository<User>{
    List<User> getAllUsers(int offset);
    List<User> getAllUsersWithFirstName(String firstName);
    List<User> getAllUsersWithLastName(String firstName);
    List<User> getAllUsersWithFnameAndLname(String firstName,String lastName);
    List<User> getAllUsers();
    User createUser(User user);
    Optional<User> getUserWithId(int id);
    User updateUser(User user, int id);
    void deleteUser(int id);

//    List<User> findUser(String userName, String firstName, String lastName);
}

