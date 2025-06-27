package com.sistemaeduc.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
public class Professor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @Column(unique = true)
    private String cpf;

    private String senha;

    private LocalDate datadNascimento;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "escola_id")
    @JsonIgnoreProperties("professores") 
    private Escola escola;

    @OneToMany(mappedBy = "professor")
    @JsonIgnore  
    private List<Aluno> alunos;

    public Professor() {}

    public Professor(String nome, String cpf, String senha, LocalDate datadNascimento, Escola escola) {
        this.nome = nome;
        this.cpf = cpf;
        this.senha = senha;
        this.datadNascimento = datadNascimento;
        this.escola = escola;
    }

    public Professor(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public LocalDate getDataNascimento() {
        return datadNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.datadNascimento = dataNascimento;
    }

    public Escola getEscola() {
        return escola;
    }

    public void setEscola(Escola escola) {
        this.escola = escola;
    }

    public List<Aluno> getAlunos() {
        return alunos;
    }

    public void setAlunos(List<Aluno> alunos) {
        this.alunos = alunos;
    }
}
