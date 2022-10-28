package com.vourapido.dto;

import java.math.BigDecimal; 

import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class CompraPassagemDTO {

	private Long id;
	private String nome;
	private String Senha;
	private BigDecimal valor;
	private String  dataCompra;
	private Long usuario;
	private String tipo;
	private String Status;
	
}
