package com.sistemaeduc.controllers;

import com.sistemaeduc.entities.Escola;
import com.sistemaeduc.repositories.EscolaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController // diz que esse é um gerenciador dos endpoints/api
@RequestMapping("/api/escolas")
public class EscolaController {

    @Autowired
    private EscolaRepository escolaRepository;

    // Listar todas as escolas
    @GetMapping
    public List<Escola> listarEscolas() {
        return escolaRepository.findAll();
    }

    // Buscar escola por ID
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        Optional<Escola> escola = escolaRepository.findById(id);
        if (escola.isPresent()) {
            return ResponseEntity.ok(escola.get());
        } else {
            return ResponseEntity.status(404).body("Escola não encontrada.");
        }
    }

    // Buscar escolas por nome (parcial)
    @GetMapping("/nome/{nome}")
    public List<Escola> buscarPorNome(@PathVariable String nome) {
        return escolaRepository.findByNomeContainingIgnoreCase(nome);
    }
    
    

    // Criar nova escola (post)
    @PostMapping
    public ResponseEntity<Escola> criarEscola(@RequestBody Escola escola) {
        Escola salva = escolaRepository.save(escola);
        return ResponseEntity.ok(salva);
    }
}
