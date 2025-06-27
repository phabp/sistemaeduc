package com.sistemaeduc.repositories;
import com.sistemaeduc.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional; 

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

	
	Optional<Usuario> findByCpf(String cpf);      
}
