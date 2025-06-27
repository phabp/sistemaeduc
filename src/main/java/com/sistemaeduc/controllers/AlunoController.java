package com.sistemaeduc.controllers;

import com.sistemaeduc.entities.Aluno;
import com.sistemaeduc.entities.Professor;
import com.sistemaeduc.repositories.AlunoRepository;
import com.sistemaeduc.repositories.ProfessorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/alunos")
public class AlunoController {

    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private ProfessorRepository professorRepository;

    
    @GetMapping
    public List<Aluno> listarTodos() {
        return alunoRepository.findAll();
    }

    
    @GetMapping("/cpf/{cpf}")
    public ResponseEntity<?> buscarPorCpf(@PathVariable String cpf) {
        Optional<Aluno> aluno = alunoRepository.findByCpf(cpf);
        return aluno.map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.notFound().build());
    }

    
    @GetMapping("/nome/{nome}")
    public ResponseEntity<?> buscarPorNome(@PathVariable String nome) {
        List<Aluno> alunos = alunoRepository.findByNomeContainingIgnoreCase(nome);
        if (alunos.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(alunos);
    }

    
    @GetMapping("/professor/{id}")
    public List<Aluno> buscarPorProfessor(@PathVariable Long id) {
        return alunoRepository.findByProfessorId(id);
    }

    
    @GetMapping("/escola/{id}")
    public List<Aluno> buscarPorEscola(@PathVariable Long id) {
        return alunoRepository.findByProfessorEscolaId(id);
    }

    
    @GetMapping("/{id}")
    public ResponseEntity<Aluno> buscarPorId(@PathVariable Long id) {
        Optional<Aluno> alunoOpt = alunoRepository.findById(id);
        return alunoOpt.map(ResponseEntity::ok)
                       .orElseGet(() -> ResponseEntity.notFound().build());
    }

   
    @PostMapping
    public ResponseEntity<?> criarAluno(@RequestBody Aluno aluno) {
        if (alunoRepository.existsByCpf(aluno.getCpf())) {
            return ResponseEntity.badRequest().body("Erro: CPF já cadastrado.");
        }

        if (aluno.getProfessor() == null || aluno.getProfessor().getId() == null) {
            return ResponseEntity.badRequest().body("Erro: Professor não informado.");
        }

        Optional<Professor> professorOpt = professorRepository.findById(aluno.getProfessor().getId());
        if (professorOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Erro: Professor não encontrado.");
        }

        aluno.setProfessor(professorOpt.get());
        Aluno salvo = alunoRepository.save(aluno);
        return ResponseEntity.ok(salvo);
    }
}
