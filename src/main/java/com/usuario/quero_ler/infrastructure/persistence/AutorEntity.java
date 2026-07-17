package com.usuario.quero_ler.infrastructure.persistence;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@Entity
@Table(name = "tb_autores")
@NoArgsConstructor
@AllArgsConstructor
public class AutorEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 80)
    @Column(name = "nome", nullable = false, length = 80)
    private String nome;

		@Builder.Default
    @ManyToMany(mappedBy = "autores")
    List<LivroEntity> livros = new ArrayList<>();
}
