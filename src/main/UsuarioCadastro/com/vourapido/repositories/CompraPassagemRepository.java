package com.vourapido.repositories;

import java.math.BigDecimal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.vourapido.model.entity.CompraPassagem;
import com.vourapido.model.enums.TipoCompraPassagem;

@Repository
public interface CompraPassagemRepository extends JpaRepository<CompraPassagem, Long> {
	
	@Query(value = "SELECT (c.valor) from CompraPassagem c "
			+ "JOIN c.usuario u WHERE u.id = :idUsuario "
			+ "AND c.tipo = :tipo "
			+ "group by u")
	BigDecimal obterSaldoPorTipoCompraPassagemEUsuario(@Param("idUsuario") Long idUsuario,
	@Param("tipo") TipoCompraPassagem tipo);
}
