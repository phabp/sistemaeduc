package com.sistemaeduc.controllers;

import com.sistemaeduc.entities.Professor;
import com.sistemaeduc.entities.Escola;
import com.sistemaeduc.repositories.EscolaRepository;
import com.sistemaeduc.repositories.ProfessorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/professores")
public class ProfessorController {

    @Autowired
    private ProfessorRepository professorRepository;

    @Autowired
    private EscolaRepository escolaRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @GetMapping
    public List<Professor> listarProfessores() {
        return professorRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Professor> buscarPorId(@PathVariable Long id) {
        Optional<Professor> professor = professorRepository.findById(id);
        return professor.map(ResponseEntity::ok)
                        .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> cadastrarProfessor(@RequestBody Professor professor) {

        if (professorRepository.existsByCpf(professor.getCpf())) {
            return ResponseEntity.badRequest().body("Erro: CPF já cadastrado.");
        }

        if (professor.getSenha() == null || professor.getSenha().length() < 6) {
            return ResponseEntity.badRequest().body("Erro: A senha deve ter no mínimo 6 caracteres.");
        }

        if (professor.getEscola() == null || professor.getEscola().getId() == null) {
            return ResponseEntity.badRequest().body("Erro: Escola não informada.");
        }

        Optional<Escola> escolaOptional = escolaRepository.findById(professor.getEscola().getId());
        if (escolaOptional.isEmpty()) {
            return ResponseEntity.badRequest().body("Erro: Escola não encontrada.");
        }

        professor.setEscola(escolaOptional.get());

        
        if (!professor.getSenha().startsWith("$2a$")) {
            professor.setSenha(passwordEncoder.encode(professor.getSenha()));
        }

        Professor salvo = professorRepository.save(professor);
        return ResponseEntity.ok(salvo);
    }

    @GetMapping("/cpf/{cpf}")
    public ResponseEntity<Professor> buscarPorCpf(@PathVariable String cpf) {
        Optional<Professor> professor = professorRepository.findByCpf(cpf);
        return professor.map(ResponseEntity::ok)
                        .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/nome/{nome}")
    public List<Professor> buscarPorNome(@PathVariable String nome) {
        return professorRepository.findByNomeContainingIgnoreCase(nome);
    }

    @GetMapping("/escola/{escolaId}")
    public List<Professor> buscarPorEscola(@PathVariable Long escolaId) {
        return professorRepository.findByEscolaId(escolaId);
    }
}

