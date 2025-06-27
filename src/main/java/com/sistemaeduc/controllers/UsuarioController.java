package com.sistemaeduc.controllers;

import com.sistemaeduc.entities.Usuario;
import com.sistemaeduc.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

   
    @PostMapping("/cadastro")
    public ResponseEntity<?> cadastrar(@RequestBody Usuario usuario) {
        
        if (usuarioRepository.findByCpf(usuario.getCpf()).isPresent()) {
            return ResponseEntity.badRequest().body("Erro: CPF já cadastrado.");
        }

       
        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        Usuario salvo = usuarioRepository.save(usuario);
        return ResponseEntity.ok(salvo);
    }

   
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Usuario usuario) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findByCpf(usuario.getCpf());

        if (usuarioOpt.isEmpty()) {
            return ResponseEntity.status(401).body("CPF não encontrado.");
        }

        Usuario existente = usuarioOpt.get();

      
        if (!passwordEncoder.matches(usuario.getSenha(), existente.getSenha())) {
            return ResponseEntity.status(401).body("Senha incorreta.");
        }

        
        return ResponseEntity.ok("Login realizado com sucesso!");
    }
}
