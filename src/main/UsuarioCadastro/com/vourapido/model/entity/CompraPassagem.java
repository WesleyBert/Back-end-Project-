package com.vourapido.model.entity;

import java.math.BigDecimal ;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


import com.vourapido.model.enums.StatusCompraPassagem;
import com.vourapido.model.enums.TipoCompraPassagem;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
  
@Entity
@Table(name="CompraPassagem")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompraPassagem {

	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column
	private String nome;

	@JoinColumn(name = "Id_usuario")
	@ManyToOne
	private Usuario usuario;
	
	@Column
	private Integer idade;
	
	@Column
	private BigDecimal valor;
	
	@Column
	private String  dataCompra;
	
	@Column
	@Enumerated(value = EnumType.STRING)
	private TipoCompraPassagem tipo;
	
	@Column(name="stats")
	@Enumerated(value = EnumType.STRING)
	private StatusCompraPassagem status;
}
