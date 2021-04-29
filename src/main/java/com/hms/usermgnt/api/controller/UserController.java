package com.hms.usermgnt.api.controller;


/**
 * User controllers are implements in this class
 * Refer the API end points for each operation
 * @author Devin Kulanjith
 * @Version 1.0
 * @since 2021-04-20
 */

import com.hms.usermgnt.api.converter.UserConverter;
import com.hms.usermgnt.api.model.dto.GetUserWithIdResponse;
import com.hms.usermgnt.api.model.dto.Status;
import com.hms.usermgnt.api.model.dto.UserDto;
import com.hms.usermgnt.api.repo.UserRepository;
import com.hms.usermgnt.api.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.hms.usermgnt.api.exception.ResourceNotFoundException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.hms.usermgnt.api.model.dto.Status.DB_ERROR;
import static com.hms.usermgnt.api.model.dto.Status.SUCCESS;


@RestController
@RequestMapping("/api/v1")
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

     private final UserRepository<User> userRepository;


     private UserConverter userConverter;

    @Autowired
    public void setUserConverter(UserConverter userConverter) {
        this.userConverter = userConverter;
    }

    @Autowired
    public UserController(UserRepository<User> userRepository) {
        this.userRepository = userRepository;
     }

    /**
     * this is the API end point for read users according to different criteria
     * @param firstName first name of the user
     * @param lastName last name of  the user
     * @return List<User>
     */
    @GetMapping("/users")
    public List<UserDto> getAllUsers(@RequestParam(value = "firstName", required = false) String firstName,
                                  @RequestParam(value = "lastName", required = false) String lastName) {

        List<UserDto> returnAll = null;

        if ((firstName == null) && (lastName == null)) {

            LOGGER.info("Request is received for get all users without Parameters");
            returnAll = userConverter.entityToDto(userRepository.getAllUsers());
        } else if ((lastName != null) && (firstName == null)) {

            LOGGER.info("request is received for getting all user with Last name- {}", lastName);
            returnAll = userConverter.entityToDto(userRepository.getAllUsersWithLastName(lastName));
        } else if ((firstName != null) && (lastName == null)) {

            LOGGER.info("request is received for getting all users with first name- {}", firstName);
            returnAll = userConverter.entityToDto(userRepository.getAllUsersWithFirstName(firstName));
        } else if ((firstName != null) && (lastName != null)) {

            LOGGER.info("request is received for getting all users with first name- {} and last name- {}",
                    firstName, lastName);
            returnAll = userConverter.entityToDto(userRepository.getAllUsersWithFnameAndLname(firstName, lastName));
        }
        LOGGER.info("Requested results were fetched");
        return returnAll;
    }


    /**
     * Read a given user with id
     * @param id user id
     * @return return the requested user
     */
    @GetMapping("/users/{id}")
    public GetUserWithIdResponse getUserWithId(@PathVariable int id) throws ResourceNotFoundException {

        LOGGER.info("Request is received to fletch user with userID[{}]", id);

        try {
            Optional<User> user = userRepository.getUserWithId(id);
            if (user != null && user.isPresent()) {
                return new GetUserWithIdResponse(userConverter.entityToDto(user.get()),new Status(SUCCESS,"success"));
            } else {
                LOGGER.info("User not found for [ id: {} ]", id);
            }
        } catch (Exception e) {
            LOGGER.error("Error while getting user with Id", e);
            return new GetUserWithIdResponse(new Status(DB_ERROR,"unsuccessful"));
        }

        LOGGER.info("User INFO return successfully for userId[{}]", id);
        return null;
    }


    /**
     * this is the user create method
     * @param dto data transfer object
     * @return created user is returned
     */
    @PostMapping("/users")
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto createUser(@Validated @RequestBody UserDto dto) {

        User user = userConverter.dtoToEntity(dto);
        LOGGER.info("Request is received to create user [{}]",user.getUserName());
        User userCreated = userRepository.createUser(user);
        LOGGER.info("user [{}] is saved", user.getUserName());
        return userConverter.entityToDto(userCreated);
    }


    /**
     *  This method for creating a new instance
     * @param user
     * @param id  Id of the user
     */
    @PutMapping("/users/{id}")
    public void update(@Validated @RequestBody User user, @PathVariable int id) {
        LOGGER.info("user is updated [{}]",user.getUserName());
        userRepository.updateUser(user,id);
    }

    /**
     * This is the delete operation
     * @param id user id
     * @return response MAP
     */
    @DeleteMapping("users/{id}")
    public Map<String, Boolean> deleteUser(@PathVariable int id)
            throws ResourceNotFoundException{
        LOGGER.info("Delete Request is Received for userId - "+ id);
         User user = userRepository.getUserWithId(id)
                 .orElseThrow(()-> new ResourceNotFoundException("User not found for this Id - " + id));
        userRepository.deleteUser(id);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        LOGGER.info("User deleted successfully for userName [{}]",user.getUserName());
        return response;
    }
}
