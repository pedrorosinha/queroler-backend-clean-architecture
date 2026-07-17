package com.usuario.quero_ler.infrastructure.persistence;

import com.usuario.quero_ler.core.enums.LeituraStatus;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@Entity
@Table(name = "tb_leitura")
@NoArgsConstructor
@AllArgsConstructor
public class LeituraEntity {

		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private LeituraStatus status;

    @ManyToOne
    @JoinColumn(name = "usuario_id",  nullable = false)
    private UsuarioEntity usuario;

    @ManyToOne
    @JoinColumn(name = "livro_id",  nullable = false)
    private LivroEntity livro;

    @OneToOne(mappedBy = "leitura",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private DiarioDeLeituraEntity diarioDeLeitura;
		@Column(name = "lido")
		private Boolean lido;
}
