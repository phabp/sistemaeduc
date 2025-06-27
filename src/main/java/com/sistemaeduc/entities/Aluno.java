package com.sistemaeduc.entities;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class Aluno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @Column(unique = true)
    private String cpf;

    private LocalDate datadNascimento;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "professor_id") 
    private Professor professor;

    public Aluno() {
    }

    public Aluno(String nome, String cpf, LocalDate datadNascimento, Professor professor) {
        this.nome = nome;
        this.cpf = cpf;
        this.datadNascimento = datadNascimento;
        this.professor = professor;
    }

    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public LocalDate getDatadNascimento() {
        return datadNascimento;
    }

    public void setDatadNascimento(LocalDate datadNascimento) {
        this.datadNascimento = datadNascimento;
    }

    public Professor getProfessor() {
        return professor;
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
    }
}
