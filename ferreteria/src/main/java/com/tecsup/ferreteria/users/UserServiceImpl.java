package com.tecsup.ferreteria.users;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.tecsup.ferreteria.user_register.UserRegister;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private BCryptPasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository) {
        super();
        this.userRepository = userRepository;
    }

    @Override
    public UserModel save(UserRegister register) {
        UserModel user = new UserModel(register.getNombre(),
        register.getApellido(), register.getUsername(),
        passwordEncoder.encode(register.getPassword()), 
        Arrays.asList(new RoleModel(register.getId(),"ROLE_USER")));
        return userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserModel user = userRepository.findByUsername(username);
        if(user == null) {
            throw new UsernameNotFoundException("Usuario o password inválidos");
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(),user.getPassword(), mapearAutoridadesRoles(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapearAutoridadesRoles(Collection<RoleModel> roles){
		return roles.stream().map(role -> new SimpleGrantedAuthority(role.getNombre())).collect(Collectors.toList());
	}
    
    @Override
    public List<UserModel> listUsers() {
        return userRepository.findAll();
    }
}
