package com.tecsup.ferreteria.dao;

import com.tecsup.ferreteria.dto.EmployeeDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<EmployeeDTO,Long> {

}
