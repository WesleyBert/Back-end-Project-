package com.vourapido.security;

import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.vourapido.model.entity.Usuario;
import com.vourapido.repositories.UsuarioRepository;

@Service
public class UsuarioDetailsService implements UserDetailsService{

	@Autowired
	UsuarioRepository repo;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Usuario usuario = repo.findByUsernameFetchAcesso(email);
		if(usuario == null) 
			usuario = repo.findByNome(email);
		if(usuario == null) 
				throw new Error("Usuario não existe!");
			return new UsuarioPrincipal(usuario);
		}
	}

