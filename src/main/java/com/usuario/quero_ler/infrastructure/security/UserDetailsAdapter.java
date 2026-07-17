package com.usuario.quero_ler.infrastructure.security;

import com.usuario.quero_ler.core.entities.User;
import com.usuario.quero_ler.core.enums.UsuarioProfile;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class UserDetailsAdapter implements UserDetails {

    private final User user;

    public UserDetailsAdapter(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (user.profile() == null) {
            return List.of();
        }
        return List.of(new SimpleGrantedAuthority("ROLE_" + user.profile().name()));
    }

    @Override
    public String getPassword() {
        return user.senha();
    }

    @Override
    public String getUsername() {
        return user.user();
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
}
