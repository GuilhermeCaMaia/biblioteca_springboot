package br.com.api.biblioteca.model;

import java.sql.Date;

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
@Table(name = "TB_ALUGAR")
@Getter
@Setter
public class AlugarModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date dataAluguel;
    private Date dataDevolucao;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnoreProperties("alugueis")
    private UserModel user;

    @ManyToOne
    @JoinColumn(name = "livro_id", nullable = false)
    @JsonIgnoreProperties("alugueis")
    private LivroModel livro;

    @Version
    private Long version; // campo de controle de versão
}
