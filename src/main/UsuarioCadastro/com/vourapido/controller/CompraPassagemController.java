package com.vourapido.controller;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vourapido.dto.AtualizarStatusDTO;
import com.vourapido.dto.CompraPassagemDTO;
import com.vourapido.exception.RegraNegocioException;
import com.vourapido.model.entity.CompraPassagem;
import com.vourapido.model.entity.Usuario;
import com.vourapido.model.enums.StatusCompraPassagem;
import com.vourapido.model.enums.TipoCompraPassagem;
import com.vourapido.services.CompraPassagemService;
import com.vourapido.services.UsuarioService;

import java.util.List;

@CrossOrigin(origins = "http://127.0.0.1:5173", maxAge = 3600)
@RestController	
@RequestMapping("/passagem")
public class CompraPassagemController {

	@Autowired
	private CompraPassagemService service;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@PostMapping("/comprar")
	public ResponseEntity<Object> salvar(@RequestBody CompraPassagemDTO dto) {
		try {
			CompraPassagem entidade = converter(dto);
			entidade = service.salvar(entidade);
			return new ResponseEntity(entidade, HttpStatus.CREATED);
			
		}catch (RegraNegocioException e){
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@PutMapping("{id}")
	public ResponseEntity atualizar(@PathVariable Long id, @RequestBody CompraPassagemDTO dto) {
		return service.buscarPorId(id).map(entity -> {
				try {
					CompraPassagem comprapassagem = converter(dto);
					comprapassagem.setId(entity.getId());
					service.atualizar(comprapassagem);
					return ResponseEntity.ok(comprapassagem);
				} catch (RegraNegocioException e) {
					return ResponseEntity.badRequest().body(e.getMessage());
				}
		}).orElseGet(() -> new ResponseEntity<String>("Passagem não encontrada no banco de dados", HttpStatus.BAD_REQUEST));
	}
	
	@PutMapping("{id}/atualizar-status")
	public ResponseEntity atualizatStatus(@PathVariable("id") Long id, @RequestBody AtualizarStatusDTO dto) {
		return service.buscarPorId(id).map(entity -> {
			StatusCompraPassagem statusSelecionado = StatusCompraPassagem.valueOf(dto.getStatus());
			
			if(statusSelecionado == null) {
				return ResponseEntity.badRequest().body("Não foi possivel atualizar o status de pagamento");
			}
			try {
				entity.setStatus(statusSelecionado);
				return ResponseEntity.ok(entity);
			}catch(RegraNegocioException e) {
				return ResponseEntity.badRequest().body(e.getMessage());
			}
		}).orElseGet(() -> new ResponseEntity("Lançamento não encontrado", HttpStatus.BAD_REQUEST));
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity deletar(@PathVariable("id") Long id) {
		return service.buscarPorId(id).map(entity -> {
			service.deletar(entity);
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}).orElseGet(() -> new ResponseEntity<String>("Lançamento não encontrado", HttpStatus.BAD_REQUEST));
	}
	
	@GetMapping
	public ResponseEntity buscar(
			@RequestParam(value = "nome", required = false) String  nome,
			@RequestParam(value = "idade", required = false) Integer idade,
			@RequestParam(value = "valor", required = false) BigDecimal valor,
			@RequestParam(value = "dataCompra", required = false) String dataCompra,
			@RequestParam(value = "usuario") Long idUsuario
			) {
		CompraPassagem passagemSearch = new CompraPassagem();
		passagemSearch.setDataCompra(dataCompra);
		passagemSearch.setIdade(idade);
		passagemSearch.setNome(nome);
		passagemSearch.setValor(valor);
		
		Optional<Usuario> usuario = usuarioService.buscarPorId(idUsuario);
		if(!usuario.isPresent()) {
			return ResponseEntity.badRequest().body(" Usuario não encontrado.");
		}else {
			passagemSearch.setUsuario(usuario.get());
		}
		
		List<CompraPassagem> comprapassagem = service.buscar(passagemSearch);
		return ResponseEntity.ok(comprapassagem);
		
	}
	
	
	
	private CompraPassagem converter(CompraPassagemDTO dto){
		CompraPassagem comprapassagem = new CompraPassagem();
			comprapassagem.setId(dto.getId());
			comprapassagem.setDataCompra(dto.getDataCompra());
			comprapassagem.setIdade(dto.getIdade());
			comprapassagem.setNome(dto.getNome());
			comprapassagem.setValor(dto.getValor());
			
			Usuario usuario = usuarioService.buscarPorId(dto.getUsuario())
					.orElseThrow(() -> new RegraNegocioException("Usuario com id " + dto.getUsuario() +
							" Não encontrado"));
			
			comprapassagem.setUsuario(usuario);
			
			if(dto.getTipo() != null) {
				comprapassagem.setTipo(TipoCompraPassagem.valueOf(dto.getTipo()));
			}
			
			if(dto.getStatus() != null) {
				comprapassagem.setStatus(StatusCompraPassagem.valueOf(dto.getStatus()));
			}
			
			return comprapassagem;
	}
	}
	
	