package com.example.mobimarket.entity;

import com.example.mobimarket.entity.base.BaseEntity;
import com.example.mobimarket.enums.Role;
import com.example.mobimarket.enums.Status;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "users")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Transactional
public class User extends BaseEntity implements UserDetails {

    @Column(nullable = false, unique = true)
    String email;

    @Column(nullable = false, unique = true)
    String username;

    @Column(nullable = false)
    String password;

    @Enumerated(EnumType.STRING)
    Role role;

    String firstname;

    String lastname;

    String phoneNumber;

    @JsonFormat(pattern="dd.MM.yyyy")
    LocalDate birthday;

    Integer token;

    LocalDateTime tokenExpiration;

    @Enumerated(EnumType.STRING)
    Status status;

    @JsonIgnore
    @ManyToMany (fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    List<Product> likedProducts;

    @JsonIgnore
    @OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE})
    List<Product> myProducts;


    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    @JsonIgnore
    public String getUsername() {
        return username;
    }


    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return true;
    }
}
