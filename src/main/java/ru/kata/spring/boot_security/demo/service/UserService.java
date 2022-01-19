package ru.kata.spring.boot_security.demo.service;

import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;


public interface UserService {



    List<User> findAll();

    User findByEmail(String name);

    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

    User findById(Long id);

    void deleteById(Long id);

    User saveUser(User user);
}
