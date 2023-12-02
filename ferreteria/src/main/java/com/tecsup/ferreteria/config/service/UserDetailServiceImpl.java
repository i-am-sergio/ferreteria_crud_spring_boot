package com.tecsup.ferreteria.config.service;

import com.tecsup.ferreteria.config.entities.AppUser;
import lombok.AllArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserDetailServiceImpl implements IUserDetailService { // UserDetailsService
    private IAccountService accountService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser appUser = accountService.loadUserByUsername(username);
        if (appUser == null)
            throw new UsernameNotFoundException(String.format("User %s not found", username));
        List<SimpleGrantedAuthority> authorities = appUser.getRoles().stream()
                .map(r -> new SimpleGrantedAuthority(r.getRole()))
                .collect(Collectors.toList());
        return User
                .withUsername(username)
                .password(appUser.getPassword())
                .authorities(authorities)
                .build();
    }
}
