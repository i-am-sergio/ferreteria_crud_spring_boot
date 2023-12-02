package com.tecsup.ferreteria;

import com.tecsup.ferreteria.config.service.IAccountService;
import com.tecsup.ferreteria.dao.EmployeeRepository;
import com.tecsup.ferreteria.dto.EmployeeDTO;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import java.util.logging.Logger;

@SpringBootApplication
public class JdbcAuthenticationSpringSecurity6Application {

    private static final Logger LOGGER = Logger.getLogger(JdbcAuthenticationSpringSecurity6Application.class.getName());

    public static void main(String[] args) {
        SpringApplication.run(JdbcAuthenticationSpringSecurity6Application.class, args);
    }

    CommandLineRunner commandLineRunner(EmployeeRepository employeeRepository) {
        return args -> {
            employeeRepository.save(new EmployeeDTO(0, "oko", "Manou", "okoma@ma.com"));
            employeeRepository.findAll().forEach(e -> LOGGER.info("First Name: " + e.getFirstName()));
        };
    }

    // @Bean
    CommandLineRunner start2(JdbcUserDetailsManager jdbcUserDetailsManager) {
        PasswordEncoder passwordEncoder = passwordEncoder();
        return args -> {
            jdbcUserDetailsManager.createUser(
                    User.withUsername("user1").password(passwordEncoder.encode("1234")).roles("USER").build());
            jdbcUserDetailsManager.createUser(
                    User.withUsername("user2").password(passwordEncoder.encode("1234")).roles("USER").build());
            jdbcUserDetailsManager.createUser(
                    User.withUsername("admin").password(passwordEncoder.encode("1234")).roles("USER", "ADMIN").build());
        };
    }

    CommandLineRunner start(JdbcUserDetailsManager jdbcUserDetailsManager) {
        PasswordEncoder passwordEncoder = passwordEncoder();
        return args -> {
            UserDetails u1 = jdbcUserDetailsManager.loadUserByUsername("user11");
            if (u1.equals(null))
                jdbcUserDetailsManager.createUser(
                        User.withUsername("user11").password(passwordEncoder.encode("1234")).roles("USER").build());
            UserDetails u2 = jdbcUserDetailsManager.loadUserByUsername("user22");
            if (u2 == null)
                jdbcUserDetailsManager.createUser(
                        User.withUsername("user22").password(passwordEncoder.encode("1234")).roles("USER").build());
            UserDetails u3 = jdbcUserDetailsManager.loadUserByUsername("admin2");
            if (u3 == null)
                jdbcUserDetailsManager.createUser(
                        User.withUsername("admin3").password(passwordEncoder.encode("1234")).roles("USER", "ADMIN")
                                .build());
        };
    }

    CommandLineRunner commandLineRunnerUserDetails(IAccountService accountService) {
        return args -> {
            accountService.addNewRole("USER");
            accountService.addNewRole("ADMIN");
            accountService.addNewUser("user1", "0123", "user1@user1.com", "0123");
            accountService.addNewUser("user2", "0123", "user2@user2.com", "0123");
            accountService.addNewUser("admin", "0123", "admin@user.com", "0123");
            accountService.addRoleToUser("user1", "USER");
            accountService.addRoleToUser("user2", "USER");
            accountService.addRoleToUser("admin", "ADMIN");
            accountService.addRoleToUser("admin", "USER");
        };
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}