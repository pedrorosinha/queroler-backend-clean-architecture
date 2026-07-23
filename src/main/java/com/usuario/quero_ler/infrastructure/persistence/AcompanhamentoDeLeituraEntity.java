package com.usuario.quero_ler.infrastructure.persistence;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Table(name = "tb_acompanhamento_leitura")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AcompanhamentoDeLeituraEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int paginaInicial;
    private int paginaFinal;
    private String comentario;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "diario_leitura_id")
    private DiarioDeLeituraEntity diarioDeLeitura;
}
