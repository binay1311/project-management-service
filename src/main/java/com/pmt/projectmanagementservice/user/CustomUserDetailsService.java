package com.pmt.projectmanagementservice.user;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @SneakyThrows
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(username);
        if(user.isEmpty()){
            throw new IllegalAccessException("User '" + username + "' not found");
        }
        return user.get();
    }

    @Transactional
    public User loadUserById(Long id) throws IllegalAccessException {
        Optional<User> user = userRepository.findById(id);
        if(user.isEmpty()){
            throw new IllegalAccessException("User with id '" + id + "' not found");
        }
        return user.get();
    }

}
