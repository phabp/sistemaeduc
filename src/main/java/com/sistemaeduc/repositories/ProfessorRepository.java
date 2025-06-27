package com.sistemaeduc.repositories;

import com.sistemaeduc.entities.Professor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProfessorRepository extends JpaRepository<Professor, Long> {

    Optional<Professor> findByCpf(String cpf); 

    List<Professor> findByNomeContainingIgnoreCase(String nome); 

    List<Professor> findByEscolaId(Long id); 
    
    boolean existsByCpf(String cpf);
}
