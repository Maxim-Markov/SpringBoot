package com.maxmarkovdev.springboot.model;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User implements UserDetails {
    private static final long serialVersionUID = 8086496705293852501L;
    private static PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @Column(unique = true)
    private String name; // уникальное значение

    @Column
    private byte age;

    @Column
    private String email;
    @Column
    @NonNull
    private String password;

    @Column(name = "persist_date", updatable = false)
    @Type(type = "org.hibernate.type.LocalDateTimeType")
    @CreationTimestamp
    private LocalDateTime persistDateTime;

    @Column(name = "is_enabled")
    @Type(type = "org.hibernate.type.BooleanType")
    private Boolean isEnabled = true;

    @Column(name = "is_deleted")
    @Type(type = "org.hibernate.type.BooleanType")
    private Boolean isDeleted = false;

    @Column
    private String city;

    @Column(name = "link_site")
    private String linkSite;

    @Column(name = "link_github")
    private String linkGitHub;

    @Column(name = "link_vk")
    private String linkVk;

    @Column
    @Type(type = "org.hibernate.type.TextType")
    private String about;

    @Column(name = "image_link")
    private String imageLink;

    @Column(name = "last_redaction_date", nullable = false)
    @Type(type = "org.hibernate.type.LocalDateTimeType")
    @UpdateTimestamp
    private LocalDateTime lastUpdateDateTime;

    @Column
    private String nickname;

    @ManyToMany(cascade = {CascadeType.REFRESH,CascadeType.MERGE},fetch = FetchType.LAZY,targetEntity = Role.class)
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    public User(@NonNull String name, @NonNull String password, String email,
                byte age, String city, String linkSite, String linkGitHub, String linkVk,
                String about, String imageLink, String nickname) {
        this.name = name;
        setPassword(password);
        this.email = email;
        this.age = age;
        this.city = city;
        this.linkSite = linkSite;
        this.linkGitHub = linkGitHub;
        this.linkVk = linkVk;
        this.about = about;
        this.imageLink = imageLink;
        this.nickname = nickname;
    }

    public User(@NonNull String name, String password, String email, byte age) {
        this.name = name;
        setPassword(password);
        this.email = email;
        this.age = age;
    }

    public void setPassword(String password) {
        this.password = passwordEncoder.encode(password);
    }

    @Override
    public @NonNull String getPassword() {
        return password;
    }

    public void addRole(Role role) {
        roles.add(role);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getUsername() {
        return name;
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
        return isEnabled;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) &&
                Objects.equals(email, user.email) &&
                Objects.equals(password, user.password) &&
                Objects.equals(name, user.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, password, name);
    }
}
