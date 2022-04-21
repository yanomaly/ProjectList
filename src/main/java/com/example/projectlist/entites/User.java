package com.example.projectlist.entites;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userID;

    @Column(name = "username")
    private String username;
    @Column(name = "password")
    private String password;
    @Column(name = "is_delete")
    private boolean isDelete;
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> role;

    public Long getUserID() {
        return userID;
    }
    public void setUserID(Long user_id) {
        this.userID = user_id;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String name) {
        this.username = name;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String hash_password) {
        this.password = hash_password;
    }

    public boolean getIsDelete() {
        return isDelete;
    }
    public void setIsDelete(boolean is_delete) {
        this.isDelete = is_delete;
    }

    public Set<Role> getRoles() {
        return role;
    }
    public void setRoles(Set<Role> role) {
        this.role = role;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }
}
