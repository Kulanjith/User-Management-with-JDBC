package com.hms.usermgnt.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hms.usermgnt.api.model.User;
import com.hms.usermgnt.api.repo.impl.JdbcTemplateBasedUserRepositoryImpl;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ExtendWith(SpringExtension.class)
@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JdbcTemplateBasedUserRepositoryImpl jdbcTemplateBasedUserRepository;

    @Test
    void getAllUsers() throws Exception{
        User user1 = new User();
        user1.setId(1);
        user1.setUserName("roshansilva");
        user1.setFirstName("roshan");
        user1.setLastName("silva");
        user1.setEmailId("roshan@gmail.com");

        User user2 = new User();
        user2.setId(2);
        user2.setUserName("isurugamage");
        user2.setFirstName("isuru");
        user2.setLastName("gamage");
        user2.setEmailId("isuru@gmail.com");


        Mockito.when(jdbcTemplateBasedUserRepository.getAllUsers()).thenReturn(Arrays.asList(user1,user2));

      mockMvc.perform(get("/api/v1/users").contentType(MediaType.APPLICATION_JSON)).andDo(print())
              .andExpect(status().isOk())
              .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
              .andExpect(jsonPath("$[0].firstName").value("roshan"))
              .andExpect(jsonPath("$[0].lastName").value("silva"))
              .andExpect(jsonPath("$[0].userName").value("roshansilva"))
              .andExpect(jsonPath("$",hasSize(2)));
    }
    

    @Test
    void getUserWithId() throws Exception{
        User user = new User("sampathgamage","sampath","gamage", "sampath@gmail.com");
        Mockito.when(jdbcTemplateBasedUserRepository.getUserWithId(7)).thenReturn(Optional.of(user));
                 mockMvc.perform(get("/api/v1/users/7").accept(MediaType.APPLICATION_JSON))
                         .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.firstName").value("sampath"))
                .andExpect(jsonPath("$.lastName").value("gamage"))
                .andExpect(jsonPath("$.userName").value("sampathgamage"));
    }

    @Test
    void createUser() throws Exception {
         User user = new User();
         user.setId(7);
         user.setUserName("roshansilva");
         user.setFirstName("roshan");
         user.setLastName("silva");
         user.setEmailId("roshan@gmail.com");


        Mockito.when(jdbcTemplateBasedUserRepository.createUser(any(User.class))).thenReturn(user);
                              mockMvc.perform(post("/api/v1/users")
                                 .contentType(MediaType.APPLICATION_JSON)
                                 .content("{\"userName\":\"roshansilva\",\"firstName\":\"roshan\",\"lastName\":\"silva\",\"emailId\":\"roshan@gmail.com\"}")
                                 .accept(MediaType.APPLICATION_JSON)).andDo(print())
                                 .andExpect(status().isCreated())
                                 .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                                 .andExpect(jsonPath("$.userName").value("roshansilva"));


    }

//    @Test
//    void update() throws Exception {
//        User user = new User("roshansilva","roshan", "silva", "roshan@gmail.com");
////        user.setUserName("roshansilva");
////        user.setFirstName("roshan");
////        user.setLastName("silva");
////        user.setEmailId("roshan@gmail.com");
//
//
//        Mockito.when(jdbcTemplateBasedUserRepository.updateUser(user, 5)).thenReturn(user);
//        mockMvc.perform(put("/api/v1/users/{id}", 5)
//                .contentType(MediaType.APPLICATION_JSON)
//                .header(HttpHeaders.IF_MATCH,5)
//                .content("{\"userName\":\"roshansilva\",\"firstName\":\"roshan\",\"lastName\":\"silva\",\"emailId\":\"roshan@gmail.com\"}"))
//               // .accept(MediaType.APPLICATION_JSON)).andDo(print())
//
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
//               // .andExpect(jsonPath("$.userName").value("roshansilva"));
//
//    }

    @Test
    void deleteUser() throws Exception{
//        User user1 = new User();
//        user1.setId(1);
//        user1.setUserName("roshansilva");
//        user1.setFirstName("roshan");
//        user1.setLastName("silva");
//        user1.setEmailId("roshan@gmail.com");
//

//        mockMvc.perform(delete("/api/v1/{id}",1)
//                .accept(MediaType.APPLICATION_JSON))
//                .andDo(print())
//                .andExpect(status().isOk());
    }
}