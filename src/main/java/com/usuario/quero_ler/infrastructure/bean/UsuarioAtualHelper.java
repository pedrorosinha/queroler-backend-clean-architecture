package com.usuario.quero_ler.infrastructure.bean;

import com.usuario.quero_ler.core.entities.User;
import com.usuario.quero_ler.core.entities.Usuario;
import com.usuario.quero_ler.core.exceptions.UsuarioNaoEncontradoException;
import com.usuario.quero_ler.core.gateway.UsuarioGateway;
import com.usuario.quero_ler.infrastructure.security.UserDetailsAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UsuarioAtualHelper {

    private final UsuarioGateway usuarioGateway;

    public User getUsuarioLogado() {
        var principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetailsAdapter adapter) {
            return adapter.getUser();
        }
        return (User) principal;
    }

    public Usuario getUsuarioAtual() {
        User user = getUsuarioLogado();
        return usuarioGateway.findByUserLoginIgnoreCase(user.user())
                .orElseThrow(() -> new UsuarioNaoEncontradoException("Usuário não encontrado para o login: " + user.user()));
    }

    public Long getUsuarioAtualId() {
        return getUsuarioAtual().id();
    }
}
