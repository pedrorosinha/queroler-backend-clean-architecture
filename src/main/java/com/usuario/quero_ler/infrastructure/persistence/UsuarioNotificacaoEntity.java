package com.usuario.quero_ler.infrastructure.persistence;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@Entity
@Table(name = "tb_usuario_notificacao")
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioNotificacaoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private UsuarioEntity usuario;

    @ManyToOne
    @JoinColumn(name = "notificacao_id")
    private NotificacaoEntity notificacao;

    private Boolean visualizada;

    private LocalDateTime dataLeitura;
}