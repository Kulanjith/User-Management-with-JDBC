package com.hms.usermgnt.api.repo.impl;
/**
 * This class use the JDBC template
 * override the methode implementation in UserRepository
 * @author Devin Kulanjith
 * @Version 1.0
 * @since 2021-04-20
 */

import com.hms.usermgnt.api.model.User;
import com.hms.usermgnt.api.repo.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;


import java.util.List;
import java.util.Optional;


@Component
public class JdbcTemplateBasedUserRepositoryImpl implements UserRepository<User> {

    private static final Logger LOGGER = LoggerFactory.getLogger(JdbcTemplateBasedUserRepositoryImpl.class);

    private JdbcTemplate jdbcTemplate;

    private RowMapper<User> rowMapper = (rs,rowNum)-> {
            User user = new User();
            user.setId(rs.getInt("id"));
            user.setUserName(rs.getString("userName"));
            user.setFirstName(rs.getString("firstName"));
            user.setLastName(rs.getString("lastName"));
            user.setEmailId(rs.getString("emailId"));
            return user;

    };

    public JdbcTemplateBasedUserRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /* *******************************************************    */

    /* define the sql queries for CRUD operations*/

    private static final String SELECT_WITH_OFFSET_SQL = "SELECT id, userName, firstName, lastName, emailId FROM users offset ?";
    private static final String SELECT_ALL_WITH_FIRST_NAME_SQL = "SELECT id, userName, firstName, lastName, emailId FROM users WHERE firstName =?";
    private static final String SELECT_ALL_WITH_FIRST_LAST_NAME_SQL = "SELECT id, userName, firstName, lastName, emailId FROM users WHERE firstName =? AND lastName =?";
    private static final String SELECT_ALL_WITH_LAST_NAME_SQL = "SELECT id, userName, firstName, lastName, emailId FROM users WHERE lastName = ?";
    private static final String SELECT_ALL_SQL = "SELECT id, userName, firstName, lastName, emailId FROM users";
    private static final String SELECT_USER_WITH_GIVEN_ID_SQL = "SELECT id, userName, firstName, lastName, emailId FROM users WHERE id = ?";
    private static final String CREATE_USER_SQL = "INSERT INTO users(userName, firstName, lastName, emailId) VALUES (?,?,?,?)";
    private static final String UPDATE_USER_SQL = "UPDATE users SET userName = ?, firstName = ?, lastName = ?, emailId = ? WHERE id = ?";
    private static final String DELETE_USER_SQL = "DELETE FROM users WHERE id = ?";

    /* *******************************************************    */


    /**
     * override the getAll user method with first name
     * @param firstName first name of the user
     * @return all the users which matches with given parameters
     */
    public List<User> getAllUsersWithFirstName(String firstName) {
        return jdbcTemplate.query(SELECT_ALL_WITH_FIRST_NAME_SQL,rowMapper,firstName);
    }

    /**
     * override the getAll user method with last name
     * @param lastName last name of the user
     * @return all the users which matches with given parameters
     */
    @Override
    public List<User> getAllUsersWithLastName(String lastName) {
        return jdbcTemplate.query(SELECT_ALL_WITH_LAST_NAME_SQL,rowMapper, lastName);
    }

    /**
     * override the getAll user method with first name and last name
     * @param firstName first name of the user
     * @param lastName last name of the user
     * @return all the users which matches with given parameters
     */
    @Override
    public List<User> getAllUsersWithFnameAndLname(String firstName, String lastName) {
        return jdbcTemplate.query(SELECT_ALL_WITH_FIRST_LAST_NAME_SQL,rowMapper, firstName,lastName);
    }

    /**
     * override the getAll user method
     * @return all the users
     */
    @Override
    public List<User> getAllUsers() {
        return jdbcTemplate.query(SELECT_ALL_SQL,rowMapper);
    }

    /**
     *
     * @param offset
     * @return
     */
    @Override
    public List<User> getAllUsers(int offset) {
        return jdbcTemplate.query(SELECT_WITH_OFFSET_SQL,rowMapper, offset);
    }

    /**
     * override the  create user method
     * @param user
     * @return created user instance
     */
    @Override
    public User createUser (User user) {
        int insert = jdbcTemplate.update(CREATE_USER_SQL,
                user.getUserName(),user.getFirstName(),user.getLastName(),user.getEmailId());
        if(insert == 1){
            LOGGER.info("New User is CREATED [{}]", user.getUserName());
        }
        return user;
    }


    /**
     * override the getUser with Id method
     * @param id user id
     * @return user object which is requested
     */
    @Override
    public Optional<User> getUserWithId(int id) {
        User user = null;
        user= jdbcTemplate.queryForObject(SELECT_USER_WITH_GIVEN_ID_SQL,rowMapper,id);
        return Optional.ofNullable(user);
    }

    /**
     * override the update user method
     * @param user
     * @param id user Id
     * @return updated user
     */
    @Override
    public User updateUser(User user, int id) {
        int update = jdbcTemplate.update(UPDATE_USER_SQL,
                user.getUserName(), user.getFirstName(), user.getLastName(), user.getEmailId(), id);
        if(update != 1){
            LOGGER.error("Update operation was not successful for user [{}]", user.getUserName());
        }
        return user;
    }

    /**
     * override the delete user method
     * @param id
     */
    @Override
    public void deleteUser(int id) {
        int delete = jdbcTemplate.update(DELETE_USER_SQL, id);
        if(delete != 1){
            LOGGER.error("User is NOT deleted successfully");
        }
    }
    }
