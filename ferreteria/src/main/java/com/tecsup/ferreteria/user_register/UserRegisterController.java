package com.tecsup.ferreteria.user_register;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tecsup.ferreteria.users.UserService;

@Controller
@RequestMapping("/registro")
public class UserRegisterController {
    private UserService userService;

    public UserRegisterController(UserService userService) {
        super();
        this.userService = userService;
    }

    @ModelAttribute("usuario")
    public UserRegister retornarNuevoUsuarioRegistroDTO() {
        return new UserRegister();
    }

    @GetMapping
    public String mostrarFormularioDeRegistro() {
        return "registro";
    }

    @PostMapping
    public String registrarCuentaDeUsuario(@ModelAttribute("usuario") UserRegister registroDTO) {
        userService.save(registroDTO);
        return "redirect:/registro?exito";
    }
}
