package com.vourapido.repositories;

import java.util.Optional ; 

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.vourapido.model.entity.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
	
	boolean existsByEmail(String email);

	Optional<Usuario>findByEmail(String Email);
	
	Usuario findByNome(@Param("nome") String nome);
	
	@Query("SELECT u FROM Usuario u JOIN FETCH u.acesso WHERE u.nome = :nome")
	Usuario findByUsernameFetchAcesso(@Param("nome") String nome);
	
}

