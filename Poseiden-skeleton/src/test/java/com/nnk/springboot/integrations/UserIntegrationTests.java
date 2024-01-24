package com.nnk.springboot.integrations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.Optional;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.nnk.springboot.controllers.UserController;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;

import jakarta.transaction.Transactional;

@SpringBootTest
@TestPropertySource(locations = "classpath:application.properties")
public class UserIntegrationTests {
    @Autowired
    UserController userController;
    @Autowired
    UserRepository userRepository;

    private MockMvc mvc;

    @Test
    @WithMockUser(username = "quentin", password = "{bcrypt}$2a$10$67CkGABIjJIYPlXoBxPCrOOWKJSuHFZ9UDXlYDTjE2Zatg.9u2ShS")
    @Transactional
    public void userIntegrationTests() throws Exception {

        this.mvc = MockMvcBuilders
                .standaloneSetup(userController)
                .setControllerAdvice()
                .build();

        User user = new User();
        user.setUsername("Jackie");
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        user.setPassword(passwordEncoder.encode("paulette*9chips"));
        user.setFullname("Paulettor");
        user.setRole("ADMIN");

        // Add User :
        mvc.perform(MockMvcRequestBuilders.post("/user/validate")
                .param("username", user.getUsername())
                .param("password", user.getPassword())
                .param("fullname", user.getFullname())
                .param("role", user.getRole())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is3xxRedirection())
                .andReturn();

        // Get saved User :
        List<User> users = userRepository.findAll();
        int lastUser = users.size() - 1;
        User userSaved = users.get(lastUser);

        // Check Values :
        assertEquals(user.getUsername(), userSaved.getUsername());
        assertEquals(user.getFullname(), userSaved.getFullname());
        assertEquals(user.getRole(), userSaved.getRole());

        int userId = userSaved.getId();

        // Update User :
        User userUpdated = new User(userSaved.getId(), "PaulUpdated", passwordEncoder.encode("pauletteUpdated*89"),
                "PaulettorUpdated", "USER");

        mvc.perform(MockMvcRequestBuilders.post("/user/update/" + userId)
                .param("username", userUpdated.getUsername())
                .param("password", userUpdated.getPassword())
                .param("fullname", userUpdated.getFullname())
                .param("role", userUpdated.getRole())
                .param("id", String.valueOf(userId))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is3xxRedirection())
                .andReturn();

        users = userRepository.findAll();
        userSaved = users.get(lastUser);

        // Check Values :
        assertEquals(userUpdated.getUsername(), userSaved.getUsername());
        // assertEquals(userUpdated.getPassword(), userSaved.getPassword());
        assertEquals(userUpdated.getFullname(), userSaved.getFullname());
        assertEquals(userUpdated.getRole(), userSaved.getRole());

        // Delete Curve :
        mvc.perform(MockMvcRequestBuilders.get("/user/delete/" + userId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is3xxRedirection())
                .andReturn();

        Optional<User> optionalUser = userRepository.findById(userId);
        Assert.assertFalse(optionalUser.isPresent());
    }
}
