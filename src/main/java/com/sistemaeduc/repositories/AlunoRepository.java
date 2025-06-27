package com.sistemaeduc.repositories;

import com.sistemaeduc.entities.Aluno;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AlunoRepository extends JpaRepository<Aluno, Long> {

 
    Optional<Aluno> findByCpf(String cpf);

    
    @EntityGraph(attributePaths = {"professor", "professor.escola"})
    List<Aluno> findByNomeContainingIgnoreCase(String nome);

    
    List<Aluno> findByProfessorId(Long professorId);
    
    
    List<Aluno> findByProfessorEscolaId(Long escolaId);
    
    boolean existsByCpf(String cpf);
}

