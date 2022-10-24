package com.vourapido.controller;


import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vourapido.dto.UsuarioDTO;
import com.vourapido.exception.ErroAutenticacao;
import com.vourapido.exception.RegraNegocioException;
import com.vourapido.model.entity.Usuario;
import com.vourapido.services.CompraPassagemService;
import com.vourapido.services.UsuarioService;

@CrossOrigin(origins = "http://127.0.0.1:5173", maxAge = 3600)
@RestController
@RequestMapping("/usuario")
public class UsuarioController {
	
	@Autowired
	private UsuarioService service;
	
	@Autowired
	private CompraPassagemService passagemservice;
	
	@GetMapping("/user")
	public String helloWord() {
		return "Oi";
	}
	
	@PostMapping("/login")
	public ResponseEntity autenticar(@RequestBody UsuarioDTO dto) { 
		try { 
			Usuario usuarioAutenticado = service.autenticar(dto.getEmail(), dto.getSenha());
			return ResponseEntity.ok(usuarioAutenticado);
		} catch (ErroAutenticacao e){ 
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	
	@PostMapping("/create")
	public ResponseEntity salvar(@RequestBody UsuarioDTO dto) {
		Usuario usuario = Usuario.builder()
				.nome(dto.getNome())
				.email(dto.getEmail())
				.senha(dto.getSenha())
				.build();
		try {
			Usuario usuarioSalvo = service.salvarUsuario(usuario);
			return new ResponseEntity(usuarioSalvo,HttpStatus.CREATED);
		} catch (RegraNegocioException e){
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	
	@GetMapping("{id}/saldo")
	public ResponseEntity valorPassagem(@PathVariable Long id) {
		Optional<Usuario> usuario = service.buscarPorId(id);
	
		if(!usuario.isPresent()) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		
		BigDecimal valor = passagemservice.obterSaldoPorUsuario(id);
		return ResponseEntity.ok(valor);
	}
}
