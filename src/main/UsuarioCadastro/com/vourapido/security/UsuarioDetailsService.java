package com.vourapido.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.vourapido.model.entity.Usuario;
import com.vourapido.repositories.UsuarioRepository;

@Service
public class UsuarioDetailsService implements UserDetailsService{

	UsuarioRepository repo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Usuario usuario = repo.findByUsernameFetchAcessos(username);
		if(usuario == null) {
			usuario = repo.findByUsernameFetchAcessos(username);
		}
		if(usuario == null) {
			throw new Error("Usuario não existe!");
		}
		return new UsuarioPrincipal(usuario);
	}

}
