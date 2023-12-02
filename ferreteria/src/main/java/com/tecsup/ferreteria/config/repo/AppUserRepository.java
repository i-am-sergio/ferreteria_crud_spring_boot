package com.tecsup.ferreteria.config.repo;

import com.tecsup.ferreteria.config.entities.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppUserRepository extends JpaRepository<AppUser,String> {
    AppUser findByUsername(String username);
}
