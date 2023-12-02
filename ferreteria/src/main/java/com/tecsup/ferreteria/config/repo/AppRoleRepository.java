package com.tecsup.ferreteria.config.repo;

import com.tecsup.ferreteria.config.entities.AppRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppRoleRepository extends JpaRepository<AppRole,String> {
}
