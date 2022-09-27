package com.vourapido.services;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import com.vourapido.model.entity.CompraPassagem;
import com.vourapido.model.enums.StatusCompraPassagem;

public interface CompraPassagemService {

	CompraPassagem salvar(CompraPassagem comprapassagem);
	
	CompraPassagem atualizar(CompraPassagem comprapassagem);
	
	void deletar(CompraPassagem comprapassagem);
	
	List<CompraPassagem> buscar(CompraPassagem comprapassagemSearch);
	
	void atualizarStatus(CompraPassagem comprapassagem , StatusCompraPassagem status);
	
	void validar(CompraPassagem comprapassagem);
	
	Optional<CompraPassagem>buscarPorId(Long id);
	
	BigDecimal obterSaldoPorUsuario(Long id);
}
