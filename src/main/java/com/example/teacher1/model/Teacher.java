package com.example.teacher1.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;



import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

@Table(name="teacher")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity

public class Teacher implements UserDetails {

    @Id
   // @GeneratedValue(strategy = GenerationType.AUTO)
            @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false,unique = true)
    private Long phoneNo;

    private String firstName;
    private String lastName;
    @Column(nullable=false,unique = true)
    private String email;
    private String gender;
    private String password;

     @ManyToOne
     @JoinColumn(name="role_id",nullable = false)
    private Role roles;


//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return roles.stream()
//                .map(role -> new SimpleGrantedAuthority(role.getName()))
//                .collect(Collectors.toSet());
//    }
@Override
public Collection<? extends GrantedAuthority> getAuthorities() {
    // Convert the RoleEnum to a String representation
    return Collections.singleton(new SimpleGrantedAuthority(roles.getName()));
}

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}
