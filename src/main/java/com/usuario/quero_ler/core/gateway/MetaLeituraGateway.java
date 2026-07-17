package com.usuario.quero_ler.core.gateway;

import com.usuario.quero_ler.core.entities.MetaLeitura;
import com.usuario.quero_ler.core.entities.Usuario;

public interface MetaLeituraGateway {
    MetaLeitura save(MetaLeitura meta);

    void deleteAllByUsuario(Usuario usuario);

    boolean existsByUsuarioAndAno(Usuario usuario, Integer ano);
}
