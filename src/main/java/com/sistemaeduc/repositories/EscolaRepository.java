package com.sistemaeduc.repositories;

import com.sistemaeduc.entities.Escola;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EscolaRepository extends JpaRepository<Escola, Long> {

    Optional<Escola> findByNome(String nome); 

    List<Escola> findByNomeContainingIgnoreCase(String nome); // busca parcial

    boolean existsByNome(String nome); 
}

