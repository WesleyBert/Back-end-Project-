package com.vourapido.controller;


import java.math.BigDecimal;  
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
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
@Controller
@RequestMapping("/usuario")

public class UsuarioController {
	
	@Autowired
	private UsuarioService service;
	
	@Autowired
	private CompraPassagemService passagemservice;

	
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
	
//	
//	@GetMapping("{id}")
//		public ResponseEntity<Usuario> getUsuarioById(@PathVariable("id") long UsuarioId) { 
//			return new ResponseEntity<Usuario>(service.getUsuarioById(UsuarioId), HttpStatus.OK);
//		}
	
	@GetMapping("/listar")
	public ResponseEntity<List<Usuario>> findAll() { 
		List<Usuario> funcionarios = service.getAllUsuario();
		return ResponseEntity.ok().body(funcionarios);
	}
}
