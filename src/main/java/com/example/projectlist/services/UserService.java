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
import java.util.regex.Pattern;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    private HashMap<Long, Integer> user_page = new HashMap<>();
    private HashMap<Long, Project> user_request = new HashMap<>();
    private HashMap<Long, Integer> user_order = new HashMap<>();
    private HashMap<Long, Integer> user_maxpage = new HashMap<>();

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
    public void setPage(long user_id, int page){
        user_page.put(user_id, page);
    }
    public int getPage(long user_id){
        if(user_page.containsKey(user_id))
        return user_page.get(user_id);
        else return 0;
    }
    public void setRequest(long user_id, Project project){
        user_request.put(user_id, project);
    }
    public Project getRequest(long user_id){
        return user_request.get(user_id);
    }
    public void setOrder(long user_id, int order){
        user_order.put(user_id, order);
    }
    public int getOrder(long user_id){
        return user_order.get(user_id);
    }
    public void setMaxPage(long user_id, int maxPage){
        user_maxpage.put(user_id, maxPage);
    }
    public int getMaxPage(long user_id){
        return user_maxpage.get(user_id);
    }
}
