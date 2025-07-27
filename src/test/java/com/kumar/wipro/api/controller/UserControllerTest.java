package com.kumar.wipro.api.controller;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import com.kumar.wipro.api.model.Role;
import com.kumar.wipro.api.model.RoleName;
import com.kumar.wipro.api.model.User;
import com.kumar.wipro.api.repository.RoleRepository;
import com.kumar.wipro.api.repository.UserRepository;
import com.kumar.wipro.api.security.UserPrincipal;

import java.util.Collections;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private User testUser;
    private UserPrincipal userPrincipal;

    @BeforeEach
    void setUp() {
        // Clean up and set up test data
        userRepository.deleteAll();
        roleRepository.deleteAll();

        // Create roles
        Role userRole = new Role(RoleName.ROLE_USER);
        roleRepository.save(userRole);

        // Create test user
        testUser = new User("Test User", "testuser", "test@example.com", "password123");
        testUser.setPassword(passwordEncoder.encode(testUser.getPassword()));
        testUser.setRoles(Collections.singleton(userRole));
        testUser = userRepository.save(testUser);

        userPrincipal = UserPrincipal.create(testUser);
    }

    @Test
    void testGetCurrentUser_Success() throws Exception {
        mockMvc.perform(get("/api/v1/users/me")
                .with(user(userPrincipal)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("testuser"))
                .andExpect(jsonPath("$.email").value("test@example.com"))
                .andExpect(jsonPath("$.name").value("Test User"));
    }

    @Test
    void testGetCurrentUser_Unauthorized() throws Exception {
        mockMvc.perform(get("/api/v1/users/me"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void testGetUserProfile_Success() throws Exception {
        mockMvc.perform(get("/api/v1/users/{username}", "testuser")
                .with(user(userPrincipal)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("testuser"))
                .andExpect(jsonPath("$.email").value("test@example.com"))
                .andExpect(jsonPath("$.name").value("Test User"));
    }

    @Test
    void testGetUserProfile_NotFound() throws Exception {
        mockMvc.perform(get("/api/v1/users/{username}", "nonexistent")
                .with(user(userPrincipal)))
                .andExpect(status().isNotFound()); // 404 for not found user
    }
}
