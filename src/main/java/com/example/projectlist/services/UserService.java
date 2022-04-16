package com.example.projectlist.services;

import com.example.projectlist.entites.Project;
import com.example.projectlist.entites.User;
import com.example.projectlist.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    private HashMap<Long, Integer> user_page = new HashMap<>();
    private HashMap<Long, List<Project>> user_activedata = new HashMap<>();

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

    public void setData(long user_id, List<Project> projects){
        user_activedata.put(user_id, projects);
    }

    public void setPage(long user_id, int page){
        user_page.put(user_id, page);
    }

    public List<Project> getData(long user_id){
//        List<Project> temp = user_activedata.get(user_id);
//        if(temp == null){
//            return new LinkedList<>();
//        }
//        else{
//            Integer page = user_page.get(user_id);
//            if(page == null) {
//                page = 0;
//                user_page.put(user_id, page);
//            }
//            else{
//
//            }
//        }
        return null;
    }
}
