package com.tecsup.ferreteria.config.service;

import com.tecsup.ferreteria.config.entities.AppRole;
import com.tecsup.ferreteria.config.entities.AppUser;
import com.tecsup.ferreteria.config.repo.AppRoleRepository;
import com.tecsup.ferreteria.config.repo.AppUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
@AllArgsConstructor
public class AccountServiceImpl implements IAccountService {

    private AppUserRepository appUserRepository;
    private AppRoleRepository appRoleRepository;
    private PasswordEncoder passwordEncoder;

    @Override
    public AppUser addNewUser(String username, String password, String email, String confirmPassword) {
        AppUser appUser = appUserRepository.findByUsername(username);
        if (appUser != null) {
            throw new UserAlreadyExistsException("This User already exists");
        }
        if (!password.equals(confirmPassword)) {
            throw new PasswordMismatchException("Password does not match");
        }
        appUser = AppUser.builder()
                .userId(UUID.randomUUID().toString())
                .username(username)
                .password(passwordEncoder.encode(password))
                .email(email)
                .build();
        return appUserRepository.save(appUser);
    }

    // Custom exception for user already exists
    class UserAlreadyExistsException extends RuntimeException {
        public UserAlreadyExistsException(String message) {
            super(message);
        }
    }

    // Custom exception for password mismatch
    class PasswordMismatchException extends RuntimeException {
        public PasswordMismatchException(String message) {
            super(message);
        }
    }

    @Override
    public AppRole addNewRole(String role) {
        AppRole appRole = appRoleRepository.findById(role).orElse(null);
        if (appRole != null) {
            throw new RoleAlreadyExistsException("This Role already exists");
        }
        appRole = AppRole.builder()
                .role(role)
                .build();
        return appRoleRepository.save(appRole);
    }

    // Custom exception for role already exists
    class RoleAlreadyExistsException extends RuntimeException {
        public RoleAlreadyExistsException(String message) {
            super(message);
        }
    }

    @Override
    public void addRoleToUser(String username, String role) {
        AppUser appUser = appUserRepository.findByUsername(username);
        Optional<AppRole> optionalAppRole = appRoleRepository.findById(role);
        if (optionalAppRole.isPresent()) {
            AppRole appRole = optionalAppRole.get();
            appUser.getRoles().add(appRole);
        } else {
            throw new RoleAlreadyExistsException("Role not found");
        }
    }

    @Override
    public void removeRoleFromUser(String username, String role) throws RoleAlreadyExistsException {
        AppUser appUser = appUserRepository.findByUsername(username);
        Optional<AppRole> optionalAppRole = appRoleRepository.findById(role);
        if (optionalAppRole.isPresent()) {
            AppRole appRole = optionalAppRole.get();
            appUser.getRoles().remove(appRole);
        } else {
            throw new RoleAlreadyExistsException("Role not found");
        }
    }

    @Override
    public AppUser loadUserByUsername(String username) {
        return appUserRepository.findByUsername(username);
    }
}
