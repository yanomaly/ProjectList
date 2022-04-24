package com.example.projectlist.service;

import com.example.projectlist.entites.User;
import com.example.projectlist.repositories.UserRepository;
import com.example.projectlist.services.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.Assert;

@SpringBootTest
public class UserServiceTests {

    @Autowired
    UserService userService;

    @Mock
    UserRepository userRepository;

    @Mock
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Test
    public void validation() {
        String test1 = userService.validation(new User("user", "pass"));
        String test2 = userService.validation(new User("user", "StrongPa22word"));

        Mockito.verify(userRepository, Mockito.times(2)).findByUsername(Mockito.anyString());
        Assert.isTrue(!test1.equals(""));
        Assert.isTrue(test2.equals(""));
    }

    @Test
    public void saveUser() {
        User user = new User("user", "StrongPa22word");
        userService.saveUser(user);

        Mockito.verify(userRepository, Mockito.times(1)).findByUsername(ArgumentMatchers.anyString());
        Mockito.verify(userRepository, Mockito.times(1)).save(user);
        Mockito.verify(bCryptPasswordEncoder, Mockito.times(1)).encode(ArgumentMatchers.anyString());
        Assert.notNull(user.getRoles());
    }

}
