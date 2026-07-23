package com.usuario.quero_ler.infrastructure.persistence;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@Entity
@Table(name = "tb_diario_leitura")
@NoArgsConstructor
@AllArgsConstructor
public class DiarioDeLeituraEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    @JoinColumn(name = "leitura_id")
    private LeituraEntity leitura;
    private LocalDateTime inicioDaLeitura;
    private LocalDateTime terminoDaLeitura;
    private Integer paginasLidas;

    @OneToMany(mappedBy = "diarioDeLeitura", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<AcompanhamentoDeLeituraEntity> comentarios = new ArrayList<>();

    @Builder.Default
    private Double nota = 0.0;
    private String tituloDaResenha;

    @Builder.Default
    private Boolean spoiler = false;

    @Column(columnDefinition = "TEXT")
    private String resenha;

    public void adicionarComentario(AcompanhamentoDeLeituraEntity comentario) {
        comentarios.add(comentario);
    }
}
