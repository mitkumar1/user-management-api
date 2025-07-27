package com.kumar.wipro.api.security;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.ActiveProfiles;

import com.kumar.wipro.api.model.Role;
import com.kumar.wipro.api.model.RoleName;
import com.kumar.wipro.api.model.User;

import java.util.Collections;

@SpringBootTest
@ActiveProfiles("test")
public class JwtTokenProviderTest {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    private Authentication authentication;
    private User testUser;

    @BeforeEach
    void setUp() {
        // Create test user with role
        Role userRole = new Role(RoleName.ROLE_USER);
        testUser = new User("Test User", "testuser", "test@example.com", "password123");
        testUser.setId(1L);
        testUser.setRoles(Collections.singleton(userRole));

        UserPrincipal userPrincipal = UserPrincipal.create(testUser);
        authentication = new UsernamePasswordAuthenticationToken(userPrincipal, null, userPrincipal.getAuthorities());
    }

    @Test
    void testGenerateToken() {
        String token = jwtTokenProvider.generateToken(authentication);
        
        assertThat(token).isNotNull();
        assertThat(token).isNotEmpty();
    }

    @Test
    void testGetUserIdFromToken() {
        String token = jwtTokenProvider.generateToken(authentication);
        Long userId = jwtTokenProvider.getUserIdFromToken(token);
        
        assertThat(userId).isEqualTo(1L);
    }

    @Test
    void testValidateToken_ValidToken() {
        String token = jwtTokenProvider.generateToken(authentication);
        Boolean isValid = jwtTokenProvider.validateToken(token);
        
        assertThat(isValid).isTrue();
    }

    @Test
    void testValidateToken_InvalidToken() {
        String invalidToken = "invalid.jwt.token";
        Boolean isValid = jwtTokenProvider.validateToken(invalidToken);
        
        assertThat(isValid).isFalse();
    }

    @Test
    void testValidateToken_EmptyToken() {
        Boolean isValid = jwtTokenProvider.validateToken("");
        
        assertThat(isValid).isFalse();
    }

    @Test
    void testValidateToken_NullToken() {
        Boolean isValid = jwtTokenProvider.validateToken(null);
        
        assertThat(isValid).isFalse();
    }
}
