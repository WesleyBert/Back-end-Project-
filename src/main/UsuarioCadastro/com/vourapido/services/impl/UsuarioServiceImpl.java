package com.vourapido.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.vourapido.exception.ErroAutenticacao;
import com.vourapido.exception.RegraNegocioException;
import com.vourapido.model.entity.Usuario;
import com.vourapido.repositories.UsuarioRepository;
import com.vourapido.services.UsuarioService;

@Service
public class UsuarioServiceImpl implements UsuarioService {

	@Autowired
	private UsuarioRepository repository;
	
	private BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Override
	public Usuario autenticar(String email, String senha) {
		Optional<Usuario>usuario = repository.findByEmail(email);
		
		if(!usuario.isPresent()) {
			throw new ErroAutenticacao("Usuario nao existe!");
		
		}
		if(!usuario.get().getSenha().equals(senha)) {
			throw new ErroAutenticacao("Senha incorreta, Tente novamente!!");
		}
		
		return usuario.get();
	}

	@Override
	public Usuario salvarUsuario(Usuario usuario) {
		validarEmail(usuario.getEmail());		
		usuario.setSenha(passwordEncoder().encode(usuario.getSenha()));
		
		return repository.save(usuario);
	}

	@Override
	public void validarEmail(String email) {
		boolean existe = repository.existsByEmail(email);
		if(existe) {
		 throw new RegraNegocioException("Email já Ultilizado!");
		}
		
	}

	@Override
	public Optional<Usuario> buscarPorId(Long id) {
		
		return repository.findById(id);
	}

	@Override
	public Usuario getUsuarioById(long id) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public List<Usuario> getAllUsuario() { 
		return repository.findAll();
	}

}
