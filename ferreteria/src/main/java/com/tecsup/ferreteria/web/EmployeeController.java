package com.tecsup.ferreteria.web;

import com.tecsup.ferreteria.dto.EmployeeDTO;
import com.tecsup.ferreteria.service.EmployeeServiceImpl;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class EmployeeController {

    private EmployeeServiceImpl employeeService;
    private static final String INDEX_PAGE = "index";

    @RequestMapping("/v1")
    public String homePage2(Model model) {
        model.addAttribute("list_employees", employeeService.getAllEmployees());
        return INDEX_PAGE;
    }

    @RequestMapping("/")
    public String homePage(Model model) {
        return findPaginated2(1, "firstName", "asc", model);
    }

    @RequestMapping("/showNewEmployeeForm")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String showNewEmployeeForm(Model model) {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        model.addAttribute("employeeDTO", employeeDTO);
        return "new_employee";
    }

    @PostMapping("/saveNewEmployee")
    @PreAuthorize("hasAuthority('ADMIN')") // @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String saveNewEmployee(@ModelAttribute("employeeDTO") EmployeeDTO employeeDTO) {
        // Save employee to database
        employeeService.saveEmployee(employeeDTO);
        return "redirect:/";
    }

    @RequestMapping("/showFormForUpdate/{id}")
    @PreAuthorize("hasAuthority('ADMIN')") // @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String showFormForUpdate(@PathVariable(value = "id") long id, Model model) {
        // get Employee from the service
        EmployeeDTO employeeDTO = employeeService.getEmployeeById(id);

        // Set Employee as a model attribute to pre-populate the form
        model.addAttribute("employeeDTO", employeeDTO);
        return "update_employee";
    }

    @RequestMapping("/deleteEmployee/{id}")
    @PreAuthorize("hasAuthority('ADMIN')") // @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String deleteEmployee(@PathVariable(value = "id") long id) {

        // call a delete Employee method from the service
        employeeService.deleteEmployeeById(id);

        return "redirect:/?id=" + id;
    }

    @RequestMapping("/page/v1/{pageNo}")
    public String findPaginated(@PathVariable(value = "pageNo") int pageNo, Model model) {
        int pageSize = 5;
        Page<EmployeeDTO> page = employeeService.findPaginated(pageNo, pageSize);
        List<EmployeeDTO> listEmployees = page.getContent();

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("listEmployees", listEmployees);
        return INDEX_PAGE;
    }

    // /page/1?sortField=name&sortDir=asc
    @RequestMapping("/page/{pageNo}")
    public String findPaginated2(@PathVariable(value = "pageNo") int pageNo,
            @RequestParam("sortField") String sortField,
            @RequestParam("sortDir") String sortDir,
            Model model) {
        int pageSize = 5;
        Page<EmployeeDTO> page = employeeService.findPaginated2(pageNo, pageSize, sortField, sortDir);
        List<EmployeeDTO> listEmployees = page.getContent();

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reserveSortDir", (sortDir.equals("asc") ? "desc" : "asc"));

        model.addAttribute("listEmployees", listEmployees);
        return INDEX_PAGE;
    }

}
