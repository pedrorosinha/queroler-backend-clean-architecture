package com.usuario.quero_ler.infrastructure.persistence;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Builder
@Entity
@Table(name = "tb_usuario")
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome", nullable = false, length = 80)
    private String nome;

    @Column(name = "email", nullable = false, unique = true, length = 256)
    private String email;

    @Column(name = "cpf", nullable = false, unique = true, length = 14)
    private String cpf;

    @Column(name = "data_nascimento")
    private LocalDate dataDeNascimento;

    @Column(name = "aceite_termos", nullable = false)
    private Boolean aceitarTermos;

    @Column(name = "cidade", length = 80)
    private String cidade;

    @Column(name = "estado", length = 100)
    private String estado;

    @Column(name = "pais", length = 100)
    private String pais;

    @Column(name = "foto", columnDefinition = "BYTEA")
    private byte[] foto;

    @OneToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY)
    private List<UsuarioNotificacaoEntity> notificacoes;

    @OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY)
    private List<LeituraEntity> livros;

    @OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY)
    private List<MetaLeituraEntity> metasLeitura;
}
