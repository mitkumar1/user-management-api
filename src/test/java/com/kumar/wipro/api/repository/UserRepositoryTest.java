package com.kumar.wipro.api.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import com.kumar.wipro.api.model.Role;
import com.kumar.wipro.api.model.RoleName;
import com.kumar.wipro.api.model.User;

import java.util.Collections;
import java.util.Optional;

@DataJpaTest
@ActiveProfiles("test")
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    private User testUser;
    private Role userRole;

    @BeforeEach
    void setUp() {
        // Create role
        userRole = new Role(RoleName.ROLE_USER);
        userRole = roleRepository.save(userRole);

        // Create test user
        testUser = new User("Test User", "testuser", "test@example.com", "password123");
        testUser.setRoles(Collections.singleton(userRole));
        testUser = userRepository.save(testUser);
    }

    @Test
    void testFindByEmail() {
        Optional<User> found = userRepository.findByEmail("test@example.com");
        
        assertThat(found).isPresent();
        assertThat(found.get().getUsername()).isEqualTo("testuser");
        assertThat(found.get().getName()).isEqualTo("Test User");
    }

    @Test
    void testFindByUsername() {
        Optional<User> found = userRepository.findByUsername("testuser");
        
        assertThat(found).isPresent();
        assertThat(found.get().getEmail()).isEqualTo("test@example.com");
        assertThat(found.get().getName()).isEqualTo("Test User");
    }

    @Test
    void testFindByUsernameOrEmail_WithUsername() {
        Optional<User> found = userRepository.findByUsernameOrEmail("testuser", "wrong@email.com");
        
        assertThat(found).isPresent();
        assertThat(found.get().getEmail()).isEqualTo("test@example.com");
    }

    @Test
    void testFindByUsernameOrEmail_WithEmail() {
        Optional<User> found = userRepository.findByUsernameOrEmail("wrongusername", "test@example.com");
        
        assertThat(found).isPresent();
        assertThat(found.get().getUsername()).isEqualTo("testuser");
    }

    @Test
    void testExistsByUsername() {
        Boolean exists = userRepository.existsByUsername("testuser");
        assertThat(exists).isTrue();

        Boolean notExists = userRepository.existsByUsername("nonexistent");
        assertThat(notExists).isFalse();
    }

    @Test
    void testExistsByEmail() {
        Boolean exists = userRepository.existsByEmail("test@example.com");
        assertThat(exists).isTrue();

        Boolean notExists = userRepository.existsByEmail("nonexistent@example.com");
        assertThat(notExists).isFalse();
    }
}
