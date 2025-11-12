package br.com.api.biblioteca.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "TB_LIVRO")
@Getter
@Setter
public class LivroModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String synopsis;
    private String genre;
    private Integer year;

    @ManyToOne
    @JoinColumn(name = "autor_id", nullable = false)
    @JsonIgnoreProperties("livros")
    private AutorModel autor;

    @Version
    private Long version; // campo de controle de versão
}
