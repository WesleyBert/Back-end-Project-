package com.vourapido.services;

import java.util.List;
import java.util.Optional;

import com.vourapido.model.entity.Usuario;


public interface UsuarioService {

	Usuario autenticar(String email, String senha);
	
	Usuario salvarUsuario(Usuario usuario);
	
	void validarEmail(String email);
	
	Optional<Usuario>buscarPorId(Long id);
	
	Usuario getUsuarioById(long id);

	List<Usuario> getAllUsuario();


}
