package com.example.projectlist.services;

import com.example.projectlist.entites.User;
import com.example.projectlist.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        return user;
    }

    public boolean saveUser(User user){
        User userFromDB = userRepository.findByUsername(user.getUsername());
        if(userFromDB != null) return false;
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return true;
    }

    public String validation(User user) {
        String decision = "";
        if (!Pattern.compile(".{6,}").matcher(user.getPassword()).find())
            decision += "Weak password!\nIt's length should be at least 6 symbols.\n\n";
        if (userRepository.findByUsername(user.getUsername()) != null)
            decision += "User with this name already exists!\n\n";
        return decision;
    }
}
