package com.tecsup.ferreteria.config.service;

import com.tecsup.ferreteria.config.entities.AppRole;
import com.tecsup.ferreteria.config.entities.AppUser;

public interface IAccountService {
    AppUser addNewUser(String username,String password, String email,String confirmPassword);
    AppRole addNewRole(String role);
    void addRoleToUser(String  username, String role);
    void removeRoleFromUser(String username, String role);
    AppUser loadUserByUsername(String username);
}
