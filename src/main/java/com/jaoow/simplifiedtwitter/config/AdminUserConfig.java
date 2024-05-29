package com.jaoow.simplifiedtwitter.config;

import com.jaoow.simplifiedtwitter.entity.User;
import com.jaoow.simplifiedtwitter.repository.RoleRepository;
import com.jaoow.simplifiedtwitter.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@AllArgsConstructor
@Configuration
public class AdminUserConfig implements CommandLineRunner {

    private RoleRepository roleRepository;
    private UserRepository userRepository;
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void run(String... args) {
        var adminRole = roleRepository.findByName("ADMIN")
                .orElseThrow(() -> new IllegalArgumentException("Role not found"));

        userRepository.findByUsername("admin")
                .ifPresentOrElse(
                        user -> {},
                        () -> {
                            var user = User.builder()
                                    .username("admin")
                                    .password(passwordEncoder.encode("admin"))
                                    .roles(Set.of(adminRole))
                                    .build();
                            userRepository.save(user);
                        });
    }
}
