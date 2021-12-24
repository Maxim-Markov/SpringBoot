package com.maxmarkovdev.springboot.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@Table(name = "roles")
public class Role implements GrantedAuthority {

    private static final long serialVersionUID = 5731253987889984670L;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column(nullable = false,unique = true)
    private String role;

    @ManyToMany(mappedBy = "roles", fetch = FetchType.EAGER, cascade = {CascadeType.REFRESH,CascadeType.REMOVE})
    private Set<User> users;
    public Role(String role) {
        this.role = role;
        users = new HashSet<>();
    }

    public Role() {
        users = new HashSet<>();
    }

    public void addUser(User user) {
        users.add(user);
        user.getRoles().add(this);
    }

    @Override
    public String getAuthority() {
        return role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Role role1 = (Role) o;
        return Objects.equals(id, role1.id) &&
                Objects.equals(role, role1.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, role);
    }
}
