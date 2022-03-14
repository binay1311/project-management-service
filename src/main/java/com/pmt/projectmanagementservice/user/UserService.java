package com.pmt.projectmanagementservice.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public User saveUser(User newUser) throws IllegalAccessException {
        //username has to be unique
        Optional<User> alreadyExists = userRepository.findByUsername(newUser.getUsername());
        if(alreadyExists.isPresent()){
            throw new IllegalAccessException("Username '" + newUser.getUsername() + "' already exists");
        }

        //make sure that password and confirmPassword match
        if(newUser.getPassword().length() < 6){
            throw new IllegalStateException("Password must be of atleast 6 characters");
        }
        if(!newUser.getPassword().equals(newUser.getConfirmPassword())){
            throw new IllegalStateException("'Password' and 'ConfirmPassword' must match");
        }

        //we dont persist or show the confirm password

        newUser.setPassword(bCryptPasswordEncoder.encode(newUser.getPassword()));
        newUser.setConfirmPassword("");
        return userRepository.save(newUser);
    }

}
