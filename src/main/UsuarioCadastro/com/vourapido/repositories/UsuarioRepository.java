package com.vourapido.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.vourapido.model.entity.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
	
	boolean existsByEmail(String email);

	Optional<Usuario>findByEmail(String email);

	
	@Query("SELECT u FROM usuario u JOIN FETCH u.acesso WHERE u.nome = :username")
	Usuario findByUsernameFetchAcessos(@Param("username") String username);
	
	
}

