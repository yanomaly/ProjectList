package com.example.projectlist.entites;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "roles")
public class Role implements GrantedAuthority {
    @Id
    @Column(name = "role_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roleID;
    @Column(name = "name")
    private String name;
    @Transient
    @ManyToMany(mappedBy = "role")
    private Set<User> users;

    public Role() {
    }
    public Role(Long roleID) {
        this.roleID = roleID;
    }
    public Role(Long roleID, String name) {
        this.roleID = roleID;
        this.name = name;
    }
    public Long getId() {
        return roleID;
    }
    public void setId(Long roleID) {
        this.roleID = roleID;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Set<User> getUsers() {
        return users;
    }
    public void setUsers(Set<User> users) {
        this.users = users;
    }

    @Override
    public String getAuthority() {
        return getName();
    }
}
