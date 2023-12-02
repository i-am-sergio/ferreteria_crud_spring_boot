package com.tecsup.ferreteria.service;

import com.tecsup.ferreteria.dto.EmployeeDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IEmployeeService {
    List<EmployeeDTO> getAllEmployees();

    void saveEmployee(EmployeeDTO employeeDTO);

    EmployeeDTO getEmployeeById(long id);

    void deleteEmployeeById(long id);

    Page<EmployeeDTO> findPaginated(int pageNo, int pageSize);

    Page<EmployeeDTO> findPaginated2(int pageNo, int pageSize, String sortField, String sortDirection);
}
