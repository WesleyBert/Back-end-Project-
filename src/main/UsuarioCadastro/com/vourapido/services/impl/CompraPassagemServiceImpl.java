package com.vourapido.services.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vourapido.exception.RegraNegocioException;
import com.vourapido.model.entity.CompraPassagem;
import com.vourapido.model.enums.StatusCompraPassagem;
import com.vourapido.model.enums.TipoCompraPassagem;
import com.vourapido.repositories.CompraPassagemRepository;
import com.vourapido.services.CompraPassagemService;

@Service
public class CompraPassagemServiceImpl implements CompraPassagemService {

	@Autowired
	private CompraPassagemRepository repository;
	
	@Override
	@Transactional
	public CompraPassagem salvar(CompraPassagem comprapassagem) {
		comprapassagem.setStatus(StatusCompraPassagem.PENDENTE);
		return repository.save(comprapassagem);
	}

	@Override
	@Transactional
	public CompraPassagem atualizar(CompraPassagem comprapassagem) {
		Objects.requireNonNull(comprapassagem.getId());
		return repository.save(comprapassagem);
		
	}

	@Override
	@Transactional
	public void deletar(CompraPassagem comprapassagem) {
		Objects.requireNonNull(comprapassagem.getId());
		repository.delete(comprapassagem);
		
	}

	@Override
	@Transactional(readOnly = true)
	public List<CompraPassagem> buscar(CompraPassagem comprapassagemSearch) {
		Example<CompraPassagem> examplo = Example.of(comprapassagemSearch,
				ExampleMatcher.matching().withIgnoreCase().withStringMatcher(StringMatcher.CONTAINING));
		return repository.findAll(examplo);
	}

	@Override
	public void atualizarStatus(CompraPassagem comprapassagem, StatusCompraPassagem status) {
		comprapassagem.setStatus(status);
		atualizar(comprapassagem);
	}

	@Override
	public void validar(CompraPassagem comprapassagem) {
		
		if(comprapassagem.getDataCompra() == null || comprapassagem.getDataCompra().toString().length() != 4) {
			throw new RegraNegocioException("Informe uma Data Válida!");
		}
		
		if(comprapassagem.getValor() == null || comprapassagem.getValor().compareTo(BigDecimal.ZERO) < 1) {
			throw new RegraNegocioException("Informe um Valor válido");
		}
		
		if(comprapassagem.getTipo() == null ){
			throw new RegraNegocioException("Informe qual será a forma de pagamento, DEBITO ou CREDITO !");
		}
	}

	@Override
	public Optional<CompraPassagem> buscarPorId(Long id) {
		 return repository.findById(id);
	}

	@Override
	@Transactional(readOnly = true )
	public BigDecimal obterSaldoPorUsuario(Long id) {
		BigDecimal hotel = repository.obterSaldoPorTipoCompraPassagemEUsuario(id,
				TipoCompraPassagem.HOTEL);
		
		BigDecimal passagem = 
				repository.obterSaldoPorTipoCompraPassagemEUsuario(id, 
						TipoCompraPassagem.PASSAGEM);
		
		if(hotel == null) {
			hotel = BigDecimal.ZERO;
		}
		
		if(passagem == null) {
			passagem = BigDecimal.ZERO;
		}
		return hotel.subtract(passagem);
	}
	}
		
		

