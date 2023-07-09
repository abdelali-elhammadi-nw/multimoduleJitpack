package com.nimbleways.springboilerplate.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nimbleways.springboilerplate.entities.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

public class UserDetailsImpl implements UserDetails {
    private static final long serialVersionUID = 1L;

    private final UUID id;
    private final String username;

    @JsonIgnore
    private final String password;

    private final Collection<? extends GrantedAuthority> authorities;

    public UserDetailsImpl(final UUID id, final String username, final String password, final Collection<String> roles) {
        this.id = id;
        this.username = username;
        this.password = password;
        final Collection<GrantedAuthority> authorities=new ArrayList<>();
        roles.forEach(role-> authorities.add(new SimpleGrantedAuthority(role)));
        this.authorities = authorities;
    }

    public static UserDetailsImpl build(final User user) {

        return new UserDetailsImpl(
                user.getId(),
                user.getEmail(),
                user.getPassword(),
                Collections.singleton(user.getRole())
        );
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public UUID getId() {
        return id;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
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
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final UserDetailsImpl user = (UserDetailsImpl) obj;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        int result = id == null ? 0 : id.hashCode();
        result = 31 * result + (username == null ? 0 : username.hashCode());
        result = 31 * result + (password == null ? 0 : password.hashCode());
        result = 31 * result + (authorities == null ? 0 : authorities.hashCode());
        return result;
    }
}

