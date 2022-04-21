package com.example.projectlist.services;

import com.example.projectlist.entites.Project;
import com.example.projectlist.entites.Role;
import com.example.projectlist.entites.User;
import com.example.projectlist.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
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
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        for (Role role : user.getRoles())
            grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), grantedAuthorities);
    }
    public String validation(User user) {
        String decision = "";
        if (!Pattern.compile(".{6,}").matcher(user.getPassword()).find())
            decision += "Weak password!\nIt's length should be at least 6 symbols.\n\n";
        if (userRepository.findByUsername(user.getUsername()) != null)
            decision += "User with this name already exists!\n\n";
        return decision;
    }
    public Project getRequest(long user_id){
        return user_request.get(user_id);
    }
    public List<User> getUsers(long user_id){
        List<User> data = new LinkedList<>();
        Pageable page = PageRequest.of(getPage(user_id), 2);
        data = userRepository.findAllByRole(1L,  page).getContent();
        setMaxPage(user_id, page.getPageSize());
        return data;
    }
    public boolean saveUser(User user){
        User userFromDB = userRepository.findByUsername(user.getUsername());
        if(userFromDB != null) return false;
        user.setRoles(Collections.singleton(new Role(1L, "ROLE_USER")));
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return true;
    }
    public void setPage(long user_id, int page){
        user_page.put(user_id, page);
    }
    public void setRequest(long user_id, Project project){
        user_request.put(user_id, project);
    }
    public void setMaxPage(long user_id, int maxPage){
        user_maxpage.put(user_id, maxPage);
    }
    public void setOrder(long user_id, int order){
        user_order.put(user_id, order);
    }
    public int getOrder(long user_id){
        return user_order.get(user_id);
    }
    public int getMaxPage(long user_id){
        return user_maxpage.get(user_id);
    }
    public int getPage(long user_id){
        if(user_page.containsKey(user_id))
            return user_page.get(user_id);
        else return 0;
    }
}
