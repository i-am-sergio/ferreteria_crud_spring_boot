package com.tecsup.ferreteria.users;

import java.util.List;
import com.tecsup.ferreteria.user_register.UserRegister;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService{
    public UserModel save(UserRegister register);
    public List<UserModel> listUsers();    
}
