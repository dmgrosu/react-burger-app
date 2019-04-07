package com.example.reactburgerapp.service;

import com.example.reactburgerapp.dao.UserRepository;
import com.example.reactburgerapp.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class MongoUserService implements UserDetailsService {

    private final UserRepository dao;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public MongoUserService(UserRepository dao, PasswordEncoder passwordEncoder) {
        this.dao = dao;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = getByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("user not found");
        }
        List<SimpleGrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("user"));
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);
    }

    public User getById(String userId) {
        return dao.findById(userId).get();
    }

    public User getByEmail(String email) {
        return dao.findByEmail(email);
    }

    public User saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return dao.save(user);
    }
}
