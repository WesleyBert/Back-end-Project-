package com.vourapido.security;

import java.util.ArrayList; 
import java.util.Collection;
import java.util.stream.Collectors;

import javax.persistence.Entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.vourapido.model.entity.Usuario;

@Entity
public class UsuarioPrincipal implements UserDetails{

	private static final long serialVersionUID = 1L;
	
	private String nome;
	private String password;
	private String email;
	private Collection<? extends GrantedAuthority>Authorities;

	public UsuarioPrincipal(Usuario usuario) {
		this.nome = usuario.getNome();
		this.password = usuario.getSenha();
		this.email = usuario.getEmail();
		try {
			this.Authorities = usuario.getAcesso()
					.stream().map(acesso -> {
				return new
						SimpleGrantedAuthority("Acesso" + acesso.getId());
			}).collect(Collectors.toList());
		}catch(Exception e) {
			this.Authorities = new ArrayList<>();
		}
	}
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	public String getNome() {
		return nome;
	}
	
	public String getEmail() {
		return email;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return null;
	}

}
