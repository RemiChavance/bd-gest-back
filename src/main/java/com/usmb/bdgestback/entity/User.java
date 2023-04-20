package com.usmb.bdgestback.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data // lombok, getter and setter
@Builder // lombok design partern builder
@AllArgsConstructor // constructor with all args
@NoArgsConstructor // constructor with no args
@Entity
@Table(name = "_user")
public class User implements UserDetails {

    @Id
    @GeneratedValue
    private Integer id;
    private String username;
    private String email;
    @OneToMany
    private List<Bd> collection;
    @OneToMany
    private List<Serie> followedSeries;
    @OneToMany
    private List<Author> followedAuthors;
    @JsonIgnore
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;

    @Override @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // return la liste des roles
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getUsername() {
        // return ce avec quoi l'utilisateur va s'authentifier
        return username;
    }

    @Override @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override @JsonIgnore
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override @JsonIgnore
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String getPassword() {
        return password;
    }


    // test purpose
    public User(int id) {
        this.id = id;
    }
}
