package com.tecsup.ferreteria.user_register;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRegister {
    private Long id;
    private String username;
	private String nombre;
	private String apellido;
	private String password;
}
