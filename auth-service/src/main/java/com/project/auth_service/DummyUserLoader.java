package com.project.auth_service;

import com.project.auth_service.model.User;
import com.project.auth_service.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DummyUserLoader {

    private static final Logger log = LoggerFactory.getLogger(DummyUserLoader.class);

    @Autowired
    private UserRepository userRepository;
    // This bean will run after the application context is loaded
    @Bean
    public CommandLineRunner createDummyUser(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            // Check if the dummy user already exists to avoid duplicates on restart
            if (userRepository.findByEmail("test@gmail.com").isEmpty()) {
                String rawPassword = "12345"; // The plain-text password you want to use
                String encodedPassword = passwordEncoder.encode(rawPassword);

                User dummyUser = new User();
                dummyUser.setEmail("test@gmail.com");
                dummyUser.setPassword(encodedPassword);
                dummyUser.setRole("ROLE_USER"); // Assign a role

                userRepository.save(dummyUser);
                log.info("Dummy user 'devuser' created with encoded password.");
                log.info("Plain password for 'devuser': {}", rawPassword); // ONLY FOR DEVELOPMENT
            } else {
                log.info("Dummy user 'devuser' already exists.");
            }
        };
    }
}

