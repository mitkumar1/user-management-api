package com.kumar.wipro.api.integration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kumar.wipro.api.dto.LoginRequest;
import com.kumar.wipro.api.dto.SignUpRequest;
import com.kumar.wipro.api.model.Role;
import com.kumar.wipro.api.model.RoleName;
import com.kumar.wipro.api.repository.RoleRepository;
import com.kumar.wipro.api.repository.UserRepository;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
public class AuthenticationIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        // Clean up and set up test data
        userRepository.deleteAll();
        roleRepository.deleteAll();

        // Create roles required for testing
        Role userRole = new Role(RoleName.ROLE_USER);
        Role adminRole = new Role(RoleName.ROLE_ADMIN);
        roleRepository.save(userRole);
        roleRepository.save(adminRole);
    }

    @Test
    void testCompleteAuthenticationFlow() throws Exception {
        // 1. Register a new user
        SignUpRequest signUpRequest = new SignUpRequest();
        signUpRequest.setName("Integration Test User");
        signUpRequest.setUsername("integrationuser");
        signUpRequest.setEmail("integration@example.com");
        signUpRequest.setPassword("password123");

        mockMvc.perform(post("/api/v1/auth/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(signUpRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.success").value(true));

        // 2. Login with the registered user
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsernameOrEmail("integrationuser");
        loginRequest.setPassword("password123");

        MvcResult loginResult = mockMvc.perform(post("/api/v1/auth/signin")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken").exists())
                .andExpect(jsonPath("$.tokenType").value("Bearer"))
                .andReturn();

        // Extract the JWT token
        String responseContent = loginResult.getResponse().getContentAsString();
        JsonNode jsonNode = objectMapper.readTree(responseContent);
        String jwtToken = jsonNode.get("accessToken").asText();

        // 3. Access protected endpoint with JWT token
        mockMvc.perform(get("/api/v1/users/me")
                .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("integrationuser"))
                .andExpect(jsonPath("$.email").value("integration@example.com"))
                .andExpect(jsonPath("$.name").value("Integration Test User"));

        // 4. Try to access protected endpoint without token (should fail)
        mockMvc.perform(get("/api/v1/users/me"))
                .andExpect(status().isUnauthorized());

        // 5. Access public endpoint (get user profile by username)
        mockMvc.perform(get("/api/v1/users/{username}", "integrationuser"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("integrationuser"));
    }

    @Test
    void testAuthenticationWithInvalidCredentials() throws Exception {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsernameOrEmail("nonexistent");
        loginRequest.setPassword("wrongpassword");

        mockMvc.perform(post("/api/v1/auth/signin")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isUnauthorized());
    }
}
